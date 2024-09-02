package com.millenium.etl.base.multicampana.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URL;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import com.millenium.etl.base.multicampana.MulticampanaEtl;
import com.millenium.etl.base.multicampana.model.MulticampanaTemportal;
import com.millenium.etl.base.multicampana.service.MulticampanaToLoadService;
import com.millenium.etl.common.model.MarcadorLista;
import com.millenium.etl.common.repository.MarcadorListaRepository;
import com.millenium.etl.common.service.DataModelService;

@SpringBootTest
public class MulticampanaServiceTest {
	
	@Autowired
	MarcadorListaRepository marcadorListaRepository;
	
	@Autowired
	DataModelService dataModelService;
	
	@Autowired
	MulticampanaEtl multicampanaEtl;
	
	@Autowired
	MulticampanaToLoadService multicampanaToLoadService;
	
	@Value("${etl.default.target.table:salida}")
    private String targetTableByProperties;
	

	@Test
	public void testRunningEtlWithSomeData() {
		
		// Crea una lista
		MarcadorLista lista = new MarcadorLista();
		lista.setNombre( "ListaTest" );
		lista.setActiva(true);
		lista.setMarcadorconfiguracionId( 1l );
		marcadorListaRepository.save( lista );
		Long listId = lista.getId();
		
		// Ruta de la base
		URL resource = getClass().getResource("/base_multicampana.xlsx");
		List<MulticampanaTemportal> list = dataModelService.getDataModelBuilderFromXlsxFile(resource.getPath() , MulticampanaTemportal.class).data().get();
		int countToSave = list.size(); // para determinar si se insertó esta cantidad
		
		// inicia el proceso de ETL
		String[] args = {"--context_param"
				,"usuario=1",
				"--context_param ",
				String.format( "archivo=%s", resource.getPath()),
				"--context_param",
				"lista="+listId};
		multicampanaEtl.run( args );
		
		long countGuionWithMarcadorListaId = multicampanaToLoadService.countGuionWithMarcadorListaId( targetTableByProperties , listId);
		
		assertEquals( countGuionWithMarcadorListaId , countToSave);
		
	}
	
}