package com.publicis.sapient.credit.card.api.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author poonamgupta
 *
 */
@Configuration
public class ServerMapperConfiguration {

	@Bean
	public ModelMapper createModelMapperBean() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setFieldMatchingEnabled(true);

		return modelMapper;
	}
}
