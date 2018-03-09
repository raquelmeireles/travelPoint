package br.com.wstravelpoint.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
@ComponentScan(basePackages = {"br.com.wstravelpoint.service"})
public class SpringConfig {
	 @Bean
	 public ResourceBundleMessageSource messageSource() {
		 ResourceBundleMessageSource source = new ResourceBundleMessageSource();
		 source.setBasename("Messages");
		 source.setDefaultEncoding("UTF-8");
		 return source;
	 }
}
