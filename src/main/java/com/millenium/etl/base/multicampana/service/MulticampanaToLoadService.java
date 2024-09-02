package com.millenium.etl.base.multicampana.service;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.millenium.etl.base.multicampana.dao.MulticampanaNativeRepository;
import com.millenium.etl.base.multicampana.model.MulticampanaTemportal;
import com.millenium.etl.common.dto.CommandLinePredictiveCampaignArgumentsDTO;
import com.millenium.etl.common.dto.DataModelBuilder;
import com.millenium.etl.common.exception.EtlRuntimeException;
import com.millenium.etl.common.repository.GenericEtlRepository;
import com.millenium.etl.common.service.EntityToLoadService;

@Service
public class MulticampanaToLoadService implements EntityToLoadService<MulticampanaTemportal, CommandLinePredictiveCampaignArgumentsDTO> {
	
	private static final Logger LOG = Logger.getLogger( MulticampanaToLoadService.class.getName() );

    @Autowired
    private MulticampanaNativeRepository multicampanaNativeRepository;
    
    @Autowired
    private MulticampanaToLoadService transaccionalMulticampanaToLoadService;

    @Autowired
    GenericEtlRepository genericEtlRepository;
    
    @Override
    public void validateProperties(CommandLinePredictiveCampaignArgumentsDTO properties) {
		LOG.info( "Validando que la lista de marcación esté vacía" );
		long listId = properties.getListId();
		String targetTable = properties.getTargetTable();
		long countByList = countGuionWithMarcadorListaId(targetTable, listId);
		if( countByList>0 ) {
			String errMessage = String.format( "La lista %d ya tiene registros para el guion %s." , listId, targetTable);
			LOG.info( errMessage );
			throw new EtlRuntimeException( errMessage );
		}
    }
    
    @Override
    public void preLoadData(CommandLinePredictiveCampaignArgumentsDTO properties) {
    	LOG.info( "Borrando tabla temporal" );
    	transaccionalMulticampanaToLoadService.cleanTable();
    }
    
	@Override
	public List<MulticampanaTemportal> validateData(CommandLinePredictiveCampaignArgumentsDTO properties, DataModelBuilder<MulticampanaTemportal> dataList) {
		LOG.info( "Se filtran registros que no tengan documento" );
		final long[] temporalId= {0}; // id temporal para mejorar el tiempo de inserción
		return dataList.data().get()
				.stream()
				.filter( data -> data.getDocumento()!=null && !data.getDocumento().trim().isEmpty() ) // filtra aquellos que NO tenga documento
				.map( t->{t.setId( temporalId[0]++ ); return t;} ) // se agrega el ID temporal
				// debe excluir aquellos que documento se repita
				.collect( Collectors.toList() )
				;
	}

	@Transactional
	@Override
	public List<MulticampanaTemportal> loadData(CommandLinePredictiveCampaignArgumentsDTO properties, List<MulticampanaTemportal> data) {
		return genericEtlRepository.saveAll( MulticampanaTemportal.class,  data );
	}
	
	@Override
    public void postLoadData(CommandLinePredictiveCampaignArgumentsDTO properties, List<MulticampanaTemportal> list) {
		long listId = properties.getListId();
		String targetTable = properties.getTargetTable();
		long userId = properties.getUserId();
		int countPredictiveDataCreated = transaccionalMulticampanaToLoadService.createGuionAndMarcadorOutbound(targetTable, MulticampanaTemportal.TABLE_NAME, listId, userId);
		LOG.log( java.util.logging.Level.INFO ,  "Registros de marcación predictiva creados: {0}", countPredictiveDataCreated);
	}


    @Transactional
    public void cleanTable() {
    	genericEtlRepository.deleteEntityTable( MulticampanaTemportal.class );
	}
    
	public long countGuionWithMarcadorListaId(String targetTable, long listId) {
		return genericEtlRepository.countGuionByMarcadorLista(targetTable, listId);
	}

	@Transactional
	public int createGuionAndMarcadorOutbound(String targetGuionTable, String tmpTable, long listId, long userId ) {
		multicampanaNativeRepository.createGuionFromTemporalTable(targetGuionTable, tmpTable, listId, userId);
		return genericEtlRepository.createMarcadorOutboundFromGuionAndLista(targetGuionTable, listId, userId);
	}


}