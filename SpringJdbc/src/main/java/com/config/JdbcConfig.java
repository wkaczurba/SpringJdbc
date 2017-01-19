package com.config;

import java.sql.SQLException;
import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import com.db.CountryRepository;
import com.db.jdbc.JdbcCountryRepository;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class JdbcConfig {

	// Different beans -> should be conditional
	/*@Bean
	public JndiObjectFactoryBean dataSource() {
		JndiObjectFactoryBean jndiObjectFB = new JndiObjectFactoryBean();
		jndiObjectFB.setJndiName("jdbc/CustomerDS");
		jndiObjectFB.setResourceRef(true);
		jndiObjectFB.setProxyInterface(javax.sql.DataSource.class);
		return jndiObjectFB;
	}*/
	
	// TODO: DBCP-pooled connection
	//@Bean
	// BasicDataSource from Apache DBCP:
	//public BasicDataSource dataSource() {
	//}
	
	/*@Bean
	public DataSource dataSource() {
		DriverManagerDataSource ds = new DriverManagerDataSource();
		ds.setDriverClassName("org.h2.Driver");
		ds.setUrl("jdbc:h2:tcp://localhost/~/spitter");
		ds.setUsername("sa");
		ds.setPassword("");
		return ds;
	}*/

	boolean useEmbeddedDB = false;
	
	// This connects to my local MySQL thing:
	/*@Bean
	public DataSource dataSource() throws Exception {
		if (!useEmbeddedDB) {
			DriverManagerDataSource ds = new DriverManagerDataSource();
			ds.setDriverClassName("com.mysql.jdbc.Driver"); // From: mysql:mysql-connector-java:5.1.6
			ds.setUrl("jdbc:mysql://localhost:3306/world?useSSL=true&requiresSSL=true");
			ds.setUsername("root");
			ds.setPassword("root");
			return ds;
		} else {
		  return new EmbeddedDatabaseBuilder()
		    .setType(EmbeddedDatabaseType.HSQL) // Was .H2
		    .addScripts("classpath:com/world_country.sql")
		    .build();
		}
	}*/	
	
	
	// Commons DBCP2:
	@Bean
	public BasicDataSource dbcpDataSource() {
		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName("com.mysql.jdbc.Driver");
		ds.setUrl("jdbc:mysql://localhost:3306/world?useSSL=true&requiresSSL=true");
		//ds.setUrl("jdbc:mysql://localhost:3306/world"); (USE THIS ONE TO SEE CONNECTIONS CREATED (SSL-WARNING ON EVERY CONNECTION CREATED))
		ds.setUsername("root");
		ds.setPassword("root");
		ds.setInitialSize(5);
		ds.setMaxTotal(10);
		return ds;
	}
	
	@Bean
	public JdbcTemplate jdbcTemplate(DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}
	
	// The repo bean could be skipped if marked with @Repospitory (for autoscan)
	@Bean
	public CountryRepository countryRepository(JdbcTemplate jdbcTemplate) {
		return new JdbcCountryRepository(jdbcTemplate);
	}
	
	// Read about this one:
	@Bean
	public PlatformTransactionManager platformTransactionManager(DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}
}
