package com.example.wetrain;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class WetrainApplication {

	public static void main(String[] args) {
		SpringApplication.run(WetrainApplication.class, args);
	}

	@Bean
	public ServletWebServerFactory servletContainer() { //ServletWebServerFactory = used to create a WebServer in this case returns a tomcat server
		// Enable SSL Trafic
		TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {//creates tomcat webserver
			@Override
			protected void postProcessContext(Context context) { // context = container that repr a servlet context = individual webapp in Catalina servlet engine
				SecurityConstraint securityConstraint = new SecurityConstraint();
				securityConstraint.setUserConstraint("CONFIDENTIAL");
				SecurityCollection collection = new SecurityCollection();
				collection.addPattern("/*");// add a url pattern to be part of this collection
				securityConstraint.addCollection(collection);
				context.addConstraint(securityConstraint); // adds the constraint
			}
		};

		// Add HTTP to HTTPS redirect to tomcat
		tomcat.addAdditionalTomcatConnectors(httpToHttpsRedirectConnector());

		return tomcat;
	}

	/*
  We need to redirect from HTTP to HTTPS. Without SSL, this application used
  port 8080. With SSL it will use port 8443. So, any request for 8080 needs to be
  redirected to HTTPS on 8443.
   */
	private Connector httpToHttpsRedirectConnector() {
		Connector connector = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
		connector.setScheme("http");
		connector.setPort(8080);
		connector.setSecure(false);
		connector.setRedirectPort(8443);
		return connector;
	}
}
