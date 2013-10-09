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

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
/**
 *
 * @author José Luis Villaverde jlvbalsa@gmail.com
 */


//The new builds atop Servlet 3.0's WebApplicationInitializer
//ServletContainerInitializer support to provide a programmatic alternative to the traditional web.xml.
//See org.springframework.web.WebApplicationInitializer Javadoc
//An abstract base class implementation of the WebApplicationInitializer interface is
//provided to simplify code-based registration of a DispatcherServlet and filters mapped to it.
//WebApplicationInitializer is an interface provided by Spring MVC that ensures your code-based
//configuration is detected and automatically used to initialize any Servlet 3 container
// If using Servlet 3, Java based configuration, e.g. via WebApplicationInitializer, you'll also need
//to set the "asyncSupported" flag as well as the ASYNC dispatcher type just like with web.xml.
public class Initializer implements WebApplicationInitializer {

	private static final String DISPATCHER_SERVLET_NAME = "dispatcher";

	public void onStartup(ServletContext servletContext)
			throws ServletException {
            
//                A WebApplicationContext variant of AnnotationConfigApplicationContext is available
//with AnnotationConfigWebApplicationContext. This implementation may be used
//when configuring the Spring ContextLoaderListener servlet listener, Spring MVC
//DispatcherServlet, etc.
		AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
		ctx.register(WebAppConfig.class);
		servletContext.addListener(new ContextLoaderListener(ctx));
		ctx.setServletContext(servletContext);
                
//                Spring's web MVC framework is, like many other web MVC frameworks, request-driven, designed
//around a central Servlet that dispatches requests to controllers and offers other functionality that
//facilitates the development of web applications. Spring's DispatcherServlet however, does more
//than just that. It is completely integrated with the Spring IoC container and as such allows you to use
//every other feature that Spring has.
//                 In a Servlet 3.0+ environment, you also have the
//option of configuring the Servlet container programmatically.
		Dynamic servlet = servletContext.addServlet(DISPATCHER_SERVLET_NAME,
				new DispatcherServlet(ctx));
		servlet.addMapping("/");
		servlet.setLoadOnStartup(1);
	}

}
