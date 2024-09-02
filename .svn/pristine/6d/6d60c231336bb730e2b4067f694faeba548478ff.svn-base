package com.millenium.etl.base.multicampana;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.millenium.etl.base.multicampana.model.MulticampanaTemportal;
import com.millenium.etl.common.component.EtlProcess;
import com.millenium.etl.common.dto.CommandLinePredictiveCampaignArgumentsDTO;

/**
 * 
 * Inicialización de Spring
 * 
 * @author Paulo Cifuentes
 *
 */
@SpringBootApplication
public class MulticampanaEtl{
	
	/**
	 * 
	 * Se requiere la creación de la tabla tmp_guion_multicampana
	 * CREATE TABLE tmp_guion_multicampana ( 	id int8 NOT NULL, 	categoriaformulario varchar(255) NULL, 	departamento varchar(255) NULL, 	direccion varchar(255) NULL, 	documento varchar(255) NULL, 	email varchar(255) NULL, 	fechaevento varchar(255) NULL, 	horaevento varchar(255) NULL, 	informacionadicional1 varchar(255) NULL, 	informacionadicional2 varchar(255) NULL, 	informacionadicional3 varchar(255) NULL, 	informacionadicional4 varchar(255) NULL, 	lugarevento varchar(255) NULL, 	motivo varchar(255) NULL, 	motivodescripcion varchar(255) NULL, 	municipio varchar(255) NULL, 	primerapellido varchar(255) NULL, 	primernombre varchar(255) NULL, 	segundoapellido varchar(255) NULL, 	segundonombre varchar(255) NULL, 	telefono1 varchar(255) NULL, 	telefono2 varchar(255) NULL, 	telefono3 varchar(255) NULL, 	telefono4 varchar(255) NULL, 	tipodocumento varchar(255) NULL, 	CONSTRAINT tmp_guion_multicampana_pkey PRIMARY KEY (id), 	CONSTRAINT uk308a3rtdxn3f99mfyn48tjkot UNIQUE (documento, tipodocumento) );
	 * 
	 * Ejemplo de argumentos:
	 * --context_param usuario=1 --context_param archivo="C:\DESARROLLO\workspace\etl-base-multicampana\src\test\resources\base_lite.xlsx" --context_param lista=2 
	 * @param args
	 */
    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(MulticampanaEtl.class, args);
        MulticampanaEtl bean = applicationContext.getBean( MulticampanaEtl.class );
        bean.run(args);
    }
    
    @Autowired
    EtlProcess<MulticampanaTemportal, CommandLinePredictiveCampaignArgumentsDTO> etlMulticampana;
    
    public void run(String... args) {
    	etlMulticampana.run( args );
    }
    

}