package br.com.wstravelpoint.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
public class SpringDataConfig {
	@Configuration
	@EnableJpaRepositories(basePackages = {"br.com.wstravelpoint.repositories" },
	 transactionManagerRef="postgresTransactionManager" , entityManagerFactoryRef = "entityManagerFactoryPostgres")
	public static class DummyToConfigSpringDataPostgres{
		
	}
}