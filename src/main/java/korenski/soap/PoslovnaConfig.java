package korenski.soap;


import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@EnableWs
@Configuration

public class PoslovnaConfig extends WsConfigurerAdapter {
	@Bean
	public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext) {
		MessageDispatcherServlet servlet = new MessageDispatcherServlet();
		servlet.setApplicationContext(applicationContext);
		servlet.setTransformWsdlLocations(true);
		return new ServletRegistrationBean(servlet, "/ws_poslovne/*");
	}

	@Bean(name = "nalog")
	public DefaultWsdl11Definition defaultWsdl11DefinitionNalog(XsdSchema nalogSchema) {
		DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
		wsdl11Definition.setPortTypeName("PoslovnaPort");
		wsdl11Definition.setLocationUri("/ws_poslovne");
		wsdl11Definition.setTargetNamespace("http://korenski/soap/nalozi_model");
		wsdl11Definition.setSchema(nalogSchema);
		return wsdl11Definition;
	}

	@Bean
	public XsdSchema nalogSchema() {
		return new SimpleXsdSchema(new ClassPathResource("Nalog_final.xsd"));
	}
	
	
	@Bean(name = "izvestaj")
	public DefaultWsdl11Definition defaultWsdl11DefinitionIzvestaj(XsdSchema izvestajSchema) {
		DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
		wsdl11Definition.setPortTypeName("PoslovnaPort");
		wsdl11Definition.setLocationUri("/ws_poslovne");
		wsdl11Definition.setTargetNamespace("http://korenski/soap/izvestaji_model");
		wsdl11Definition.setSchema(izvestajSchema);
		return wsdl11Definition;
	}

	@Bean
	public XsdSchema izvestajSchema() {
		return new SimpleXsdSchema(new ClassPathResource("Izvestaj_final.xsd"));
	}
}
