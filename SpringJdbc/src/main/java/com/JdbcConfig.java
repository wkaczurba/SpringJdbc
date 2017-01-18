package com;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

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
	@Bean
	public DataSource dataSource() throws Exception {
		if (!useEmbeddedDB) {
			DriverManagerDataSource ds = new DriverManagerDataSource();
			ds.setDriverClassName("com.mysql.jdbc.Driver"); // From: mysql:mysql-connector-java:5.1.6
			ds.setUrl("jdbc:mysql://localhost:3306/world");
			ds.setUsername("root");
			ds.setPassword("root");
			return ds;
		} else {
		  return new EmbeddedDatabaseBuilder()
		    .setType(EmbeddedDatabaseType.HSQL) // Was .H2
		    .addScripts("classpath:com/world_country.sql" /*, "classpath:spittr/db/jdbc/test-data.sql"*/)
		    .build();
		}
	}	
}
