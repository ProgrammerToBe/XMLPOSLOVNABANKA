package korenski.soap;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ws.client.core.WebServiceTemplate;

@Configuration
public class PoslovnaKlijentConfig {
//	@Bean
//	  Jaxb2Marshaller jaxb2Marshaller() {
//
//	    Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
//	    jaxb2Marshaller.setContextPath("korenski.soap.nalozi_model");
//	    return jaxb2Marshaller;
//	  }

	  @Bean(name = "Prvi")
	  public WebServiceTemplate webServiceTemplate() {

	    WebServiceTemplate webServiceTemplate = new WebServiceTemplate();
//	    webServiceTemplate.setMarshaller(jaxb2Marshaller());
//	    webServiceTemplate.setUnmarshaller(jaxb2Marshaller());
	    //webServiceTemplate.setDefaultUri("http://localhost:8080/ws_poslovne/nalog");

	    return webServiceTemplate;
	  }
}
