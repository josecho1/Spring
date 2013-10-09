/*
DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.

Copyright 2013 José Luis Villaverde Balsa.

This file is part of SpringDataMeu.

SpringDataMeu is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

SpringDataMeu is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with Foobar. If not, see <http://www.gnu.org/licenses/>.
*/
package com.sprdata.init;

import java.util.Properties;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.hibernate.ejb.HibernatePersistence;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

/**
 *
 * @author José Luis Villaverde jlvbalsa@gmail.com
 */

//Or    you    can    bootstrap  a  @Configuration class using AnnotationConfigApplicationContex(Initializer.java)
@Configuration
@EnableWebMvc
@EnableTransactionManagement
@ComponentScan("com.sprdata")
@PropertySource("classpath:application.properties")
@EnableJpaRepositories("com.sprdata.repository")
public class WebAppConfig {

	private static final String PROPERTY_NAME_DATABASE_DRIVER = "db.driver";
	private static final String PROPERTY_NAME_DATABASE_PASSWORD = "db.password";
	private static final String PROPERTY_NAME_DATABASE_URL = "db.url";
	private static final String PROPERTY_NAME_DATABASE_USERNAME = "db.username";
	private static final String PROPERTY_NAME_HIBERNATE_DIALECT = "hibernate.dialect";
	private static final String PROPERTY_NAME_HIBERNATE_SHOW_SQL = "hibernate.show_sql";
        // src/main/resources/application.properties
        // entitymanager.packages.to.scan=com.sprdata.model
	private static final String PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN = "entitymanager.packages.to.scan";

	@Resource
	private Environment env;

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
                //src/main/resources/application.properties
		dataSource.setDriverClassName(env.getRequiredProperty(PROPERTY_NAME_DATABASE_DRIVER));
		dataSource.setUrl(env.getRequiredProperty(PROPERTY_NAME_DATABASE_URL));
		dataSource.setUsername(env.getRequiredProperty(PROPERTY_NAME_DATABASE_USERNAME));
		dataSource.setPassword(env.getRequiredProperty(PROPERTY_NAME_DATABASE_PASSWORD));
		return dataSource;
	}
        
//        In standard JPA, persistence units get defined through META-INF/persistence.xml files
//in specific jar files which will in turn get searched for @Entity classes. In many cases,
//persistence.xml does not contain more than a unit name and relies on defaults and/or external
//setup for all other concerns (such as the DataSource to use, etc). For that reason, Spring
//Framework 3.1 provides an alternative: LocalContainerEntityManagerFactoryBean accepts a
//'packagesToScan' property, specifying base packages to scan for @Entity classes. This is analogous
//to AnnotationSessionFactoryBean's property of the same name for native Hibernate setup, and
//also to Spring's component-scan feature for regular Spring beans. Effectively, this allows for XML-free
//JPA setup at the mere expense of specifying a base package for entity scanning: a particularly fine
//match for Spring applications which rely on component scanning for Spring beans as well, possibly even
//bootstrapped using a code-based Servlet 3.0 initializer.

// src/main/resources/application.properties
// entitymanager.packages.to.scan=com.sprdata.model
        
// The gives full control over LocalContainerEntityManagerFactoryBean
//EntityManagerFactory configuration and is appropriate for environments where fine-
//grained customization is required. The LocalContainerEntityManagerFactoryBean creates
//a PersistenceUnitInfo instance based on the persistence.xml file, the supplied
//dataSourceLookup strategy, and the specified loadTimeWeaver. It is thus possible to work with
//custom data sources outside of JNDI and to control the weaving process.
        
// Using the LocalContainerEntityManagerFactoryBean is the most powerful JPA setup option,
//allowing for flexible local configuration within the application. It supports links to an existing JDBC
//DataSource, supports both local and global transactions, and so on. However, it also imposes
//requirements on the runtime environment, such as the availability of a weaving-capable class loader if
//the persistence provider demands byte-code transformation.
//This option may conflict with the built-in JPA capabilities of a Java EE 5 server. In a full Java EE
//5 environment, consider obtaining your EntityManagerFactory from JNDI. Alternatively, specify
//a custom persistenceXmlLocation on your LocalContainerEntityManagerFactoryBean
//definition, for example, META-INF/my-persistence.xml, and only include a descriptor with that name
//in your application jar files. Because the Java EE 5 server only looks for default META-INF/
//persistence.xml files, it ignores such custom persistence units and hence avoid conflicts with a
//Spring-driven JPA setup upfront. (This applies to Resin 3.1, for example.)
       
      
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactoryBean.setDataSource(dataSource());
		entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistence.class);
		entityManagerFactoryBean.setPackagesToScan(env.getRequiredProperty(PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN));
		
		entityManagerFactoryBean.setJpaProperties(hibProperties());
		
		return entityManagerFactoryBean;
	}

	private Properties hibProperties() {
		Properties properties = new Properties();
		properties.put(PROPERTY_NAME_HIBERNATE_DIALECT,	env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_DIALECT));
		properties.put(PROPERTY_NAME_HIBERNATE_SHOW_SQL, env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_SHOW_SQL));
		return properties;
	}

	@Bean
	public JpaTransactionManager transactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
		return transactionManager;
	}
//Simple implementation of the ViewResolver interface that effects UrlBasedViewResolver
//the direct resolution of logical view names to URLs, without an explicit mapping definition. This is appropriate if your logical names
//match the names of your view resources in a straightforward manner, without the need for arbitrary mappings.
	@Bean
	public UrlBasedViewResolver setupViewResolver() {
		UrlBasedViewResolver resolver = new UrlBasedViewResolver();
		resolver.setPrefix("/WEB-INF/pages/");
		resolver.setSuffix(".jsp");
		resolver.setViewClass(JstlView.class);
		return resolver;
	}
	
	@Bean
	public ResourceBundleMessageSource messageSource() {
		ResourceBundleMessageSource source = new ResourceBundleMessageSource();
		source.setBasename(env.getRequiredProperty("message.source.basename"));
		source.setUseCodeAsDefaultMessage(true);
		return source;
	}
        
       

}
