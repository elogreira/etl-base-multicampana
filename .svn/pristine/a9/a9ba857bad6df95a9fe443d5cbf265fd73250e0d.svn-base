package com.millenium.etl.base.multicampana.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.millenium.etl.base.multicampana.model.MulticampanaTemportal;
import com.millenium.etl.common.component.EtlProcess;
import com.millenium.etl.common.dto.CommandLinePredictiveCampaignArgumentsDTO;
import com.millenium.etl.common.service.CommandPredictiveCampaignArgumentsLineServiceImpl;
import com.millenium.etl.common.service.EtlPropertiesFactoryService;

@Configuration
@EntityScan(basePackages = "com.millenium.etl.base.multicampana.model")
public class SpringConfig {
	
	@Bean
	EtlProcess<MulticampanaTemportal, CommandLinePredictiveCampaignArgumentsDTO> etlMulticampana(){
		return new EtlProcess<>( MulticampanaTemportal.class );
	}
	
	@Bean
	EtlPropertiesFactoryService<CommandLinePredictiveCampaignArgumentsDTO> commandLineService(){
		return new CommandPredictiveCampaignArgumentsLineServiceImpl();
	}
	
}