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
		return new SimpleXsdSchema(new ClassPathResource("Nalozi_za_placanje.xsd"));
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
		return new SimpleXsdSchema(new ClassPathResource("Izvestaji.xsd"));
	}
	
	//ovo moras obrisati pre nego sto vide
	@Bean(name = "rtgs")
	public DefaultWsdl11Definition defaultWsdl11DefinitionRtgs(XsdSchema rtgsSchema) {
		DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
		wsdl11Definition.setPortTypeName("PoslovnaPort");
		wsdl11Definition.setLocationUri("/ws_poslovne");
		wsdl11Definition.setTargetNamespace("http://korenski/soap/rtgs");
		wsdl11Definition.setSchema(rtgsSchema);
		return wsdl11Definition;
	}

	@Bean
	public XsdSchema rtgsSchema() {
		return new SimpleXsdSchema(new ClassPathResource("RTGS.xsd"));
	}
	
	@Bean(name = "clearing")
	public DefaultWsdl11Definition defaultWsdl11DefinitionClearing(XsdSchema clearingSchema) {
		DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
		wsdl11Definition.setPortTypeName("PoslovnaPort");
		wsdl11Definition.setLocationUri("/ws_poslovne");
		wsdl11Definition.setTargetNamespace("http://korenski/soap/clearing");
		wsdl11Definition.setSchema(clearingSchema);
		return wsdl11Definition;
	}

	@Bean
	public XsdSchema clearingSchema() {
		return new SimpleXsdSchema(new ClassPathResource("Clearing.xsd"));
	}
	
	@Bean(name = "odobrenje")
	public DefaultWsdl11Definition defaultWsdl11DefinitionOdobrenje(XsdSchema odobrenjeSchema) {
		DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
		wsdl11Definition.setPortTypeName("PoslovnaPort");
		wsdl11Definition.setLocationUri("/ws_poslovne");
		wsdl11Definition.setTargetNamespace("http://korenski/soap/odobrenje");
		wsdl11Definition.setSchema(odobrenjeSchema);
		return wsdl11Definition;
	}

	@Bean
	public XsdSchema odobrenjeSchema() {
		return new SimpleXsdSchema(new ClassPathResource("Odobrenje.xsd"));
	}
	
	@Bean(name = "zaduzenje")
	public DefaultWsdl11Definition defaultWsdl11DefinitionZaduzenje(XsdSchema zaduzenjeSchema) {
		DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
		wsdl11Definition.setPortTypeName("PoslovnaPort");
		wsdl11Definition.setLocationUri("/ws_poslovne");
		wsdl11Definition.setTargetNamespace("http://korenski/soap/zaduzenje");
		wsdl11Definition.setSchema(zaduzenjeSchema);
		return wsdl11Definition;
	}

	@Bean
	public XsdSchema zaduzenjeSchema() {
		return new SimpleXsdSchema(new ClassPathResource("Zaduzenje.xsd"));
	}
}
