package br.com.wstravelpoint.config;

import java.io.IOException;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionTemplate;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableTransactionManagement
public class DatabaseConfig {
	
	@Bean(name = "database")
	@Qualifier(value = "database")
	public ResourceBundle database() {
		return ResourceBundle.getBundle("project");
	}
	
	@Bean(name  = "postgresDataSource")
	@Qualifier("postgresDataSource")
	public DataSource postgresDataSource( @Qualifier(value = "database") ResourceBundle databaseBundle ) {
		HikariConfig dataSourceConfig = new HikariConfig();
		dataSourceConfig.setPoolName("POSTGRES_POOL");
		dataSourceConfig.setDriverClassName( "org.postgresql.Driver" );
		dataSourceConfig.setJdbcUrl(databaseBundle.getString("postgres.url"));
		dataSourceConfig.setUsername(databaseBundle.getString("postgres.username"));
		dataSourceConfig.setPassword(databaseBundle.getString("postgres.password"));
		dataSourceConfig.setMinimumIdle(1);
		dataSourceConfig.setMaximumPoolSize(10);
		dataSourceConfig.setValidationTimeout( 30 * 1000); // 30 segundos
		dataSourceConfig.setConnectionTimeout( 30 * 1000); // 30 segundos
		dataSourceConfig.setMaxLifetime( 30 * 60 * 1000); // 30 minutos
		dataSourceConfig.setIdleTimeout(5 * 60 * 1000); // 5 minutos
		dataSourceConfig.setConnectionTestQuery( "SELECT 1");
		return new HikariDataSource(dataSourceConfig);
	}
	
	@Lazy(true)
	@Bean(name = "entityManagerFactoryPostgres" )
	@DependsOn(value = "postgresDataSource")
	public LocalContainerEntityManagerFactoryBean entityManagerFactoryPostgres(@Qualifier("postgresDataSource") DataSource dataSource , @Qualifier(value = "database") ResourceBundle databaseBundle ) {
		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactoryBean.setDataSource(dataSource);
		entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		entityManagerFactoryBean.setPersistenceUnitName("postgresPU");
		
		entityManagerFactoryBean.setPackagesToScan(new String[]{ "br.com.wstravelpoint.models" });
		Properties jpaProperties = new Properties();

		jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
		jpaProperties.put("hibernate.temp.use_jdbc_metadata_defaults", "false");
		
		jpaProperties.put("hibernate.hbm2ddl.auto", "create");//CRIAR AUTOMATICO AS TABLEAS DE ACORDO COM OS MODELS
		jpaProperties.put("hibernate.show_sql",  databaseBundle.getString("jpa.show_sql") );
		jpaProperties.put("hibernate.format_sql", databaseBundle.getString("jpa.format_sql") );
		jpaProperties.put("hibernate.generate_statistics", false );

		entityManagerFactoryBean.setJpaProperties(jpaProperties);
		return entityManagerFactoryBean;
	}
	
	@Lazy(true)
	@Bean(name = "postgresSqlFactory" )
	@Qualifier(value = "postgresSqlFactory" )
	public SqlSessionFactoryBean postgresSqlFactory( @Qualifier("postgresDataSource") DataSource dataSource  , ResourceLoader resourceLoader) throws IOException{
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource( dataSource );
		sqlSessionFactoryBean.setConfigLocation( new ClassPathResource("mybatis/mybatis-postgres-config.xml"  ) );
		Resource[] resources = ResourcePatternUtils.getResourcePatternResolver(resourceLoader).getResources("classpath:mybatis/postgres/*.xml");
		sqlSessionFactoryBean.setMapperLocations( resources ) ;
		return sqlSessionFactoryBean;
	}
	
	@Bean(name = "postgresTransactionManager")
	@Qualifier( "postgresTransactionManager")
	public JpaTransactionManager transactionManagerPostgres(@Qualifier("entityManagerFactoryPostgres") EntityManagerFactory entityManagerFactory) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory);
		return transactionManager;
	}
	
	@Lazy(true)
	@Bean(name = "postgresTransactionTemplate")
	@Qualifier("postgresTransactionTemplate")
	public TransactionTemplate postgresTransactionTemplate( @Qualifier("postgresTransactionManager") JpaTransactionManager transactionManager){
		return new TransactionTemplate( transactionManager );
	}

	@Lazy(true)
	@Bean(name = "postgresMapperScannerConfigurer" )
	@Qualifier(value = "postgresMapperScannerConfigurer" )
	public MapperScannerConfigurer configPostgresMapperScanner( @Qualifier("postgresSqlFactory") SqlSessionFactoryBean sessionFactoryBean ) throws Exception{
		MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
		mapperScannerConfigurer.setSqlSessionFactoryBeanName("postgresSqlFactory");	
		mapperScannerConfigurer.setBasePackage("br.com.wstravelpoint.mappers" );
		return mapperScannerConfigurer;
	}
	
}
