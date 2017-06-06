/*
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matcher.*;
import static org.springframework.test.web.servlet.result.ContentResultMatchers.*;
import static org.hamcrest.core.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.hamcrest.Matchers.containsString;
*/

package mtb.rest;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hamcrest.core.IsNot;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import korenski.MtbApplication;
import mtb.MtbApplicationTests;

@SuppressWarnings("deprecation")
@RunWith(SpringRunner.class)
@SpringBootTest 
@SpringApplicationConfiguration(classes = MtbApplication.class)
public class AutentificationControllerRest  extends MtbApplicationTests {

	@Autowired
	private WebApplicationContext context; // ovo omogucava da se ovo posmatra kao web aplikacija

	private MockMvc mvc;
	@Before
	public void setUp() {
		this.mvc = MockMvcBuilders.webAppContextSetup(this.context).build();
	}

	@Test
	public void testAutentification() throws Exception {

		// prvi kupac - koji ce se registrovati
		JSONObject musterija = new JSONObject();
		musterija.put("username", "Maksim");
		musterija.put("password", "Lalic");
		musterija.put("mail","maksim.kac.94@gmail.com");
	    byte[] sendData = musterija.toString().getBytes("utf-8");
	    
	    // drugi kupac - koji se nece registrovati
		JSONObject musterija2 = new JSONObject();
		musterija2.put("username", "Maksim2");
		musterija2.put("password", "Lalic2");
		musterija2.put("mail","maksim.kac.94@gmail.com");
	    byte[] sendData2 = musterija2.toString().getBytes("utf-8");
		
	    // kupimo inicijalno rpaznu bazu
		this.mvc.perform(get("/autentification/testGet")).andExpect(status().isOk())
			.andExpect(jsonPath("$", hasSize(0) ) );
		
		// punimo bazu
		this.mvc.perform(post("/autentification/testAdd")).andExpect(status().isAccepted());
		MvcResult nizRezultata = this.mvc.perform(get("/autentification/testGet")).andExpect(status().isOk())
			.andExpect(jsonPath("$", hasSize(10) ) )
			.andReturn();
		
		// registrujemo prvog kupca
		MvcResult result=this.mvc.perform(post("/autentification/register")
				.content(sendData)
				.contentType(MediaType.APPLICATION_JSON_VALUE))
					.andExpect(status().isOk())
					.andExpect(content().string("OK"))
					.andReturn();
		String content = result.getResponse().getContentAsString();
		assertTrue(content.equals("OK"));
		//.andExpect(content().string(org.hamcrest.Matchers.containsString("OK")));
		
		// pokusavamo opet registrovati prvog kupca - neuspesno
		result=this.mvc.perform(post("/autentification/register")
				.content(sendData)
				.contentType(MediaType.APPLICATION_JSON_VALUE))
					.andExpect(status().isOk())
					.andReturn();
		content = result.getResponse().getContentAsString();
		assertTrue(content.contains("username"));
		
		// logujemo se kao prvi kupac - uspesno
		this.mvc.perform(post("/autentification/login")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(sendData))
		.andExpect(status().isOk())
//		.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE) )  // uvek mu sam doda ";UTF=8"
		.andExpect(jsonPath("$.username", is("Maksim")))
		.andExpect(jsonPath("$.password", is("Lalic")))
		.andExpect(jsonPath("$.mail", is("maksim.kac.94@gmail.com")))
		.andExpect(jsonPath("$.verification", IsNot.not(is("OK"))));
		
		
		// logujemo se kao drugi kupac, neuspesno
		this.mvc.perform(post("/autentification/login")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(sendData2))
		.andExpect(status().isBadRequest())
//		.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE) )  // uvek mu sam doda ";UTF=8"
		.andExpect(jsonPath("$.username").doesNotExist())
		.andExpect(jsonPath("$.password").doesNotExist())
		.andExpect(content().string(""))
		.andExpect(jsonPath("$").doesNotExist());
		

		// hocemo da menjamo password, kao neregistrovani
		this.mvc.perform(post("/autentification/firstChangeProducer")
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.content(sendData2))
		.andExpect(status().isNotFound())
		.andExpect(jsonPath("$.username").doesNotExist())
		.andExpect(jsonPath("$.password").doesNotExist());
		
		musterija.put("password","LALIC");
		sendData = musterija.toString().getBytes("utf-8");

		// hocemo da menjamo password, jesmo registrovani, ali nismo radnik nego musterija
		this.mvc.perform(post("/autentification/firstChangeProducer")
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.content(sendData))
		.andExpect(status().isNotFound())
		.andExpect(jsonPath("$.username").doesNotExist())
		.andExpect(jsonPath("$.password").doesNotExist());
		
		
		// kreiramo radnika, ali ima prazan password, null - ne moze da menja password
		JSONObject radnik2 = new JSONObject();
		radnik2.put("username", "Rade");
		radnik2.put("mail","maksim.kac.94@gmail.com");
	    byte[] sendData3 = radnik2.toString().getBytes("utf-8");
		
	    result = this.mvc.perform(post("/autentification/firstChangeProducer")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(sendData3))
			.andExpect(status().isNotFound())
			.andExpect(jsonPath("$.username", is("Rade")))
			.andExpect(jsonPath("$.password").doesNotExist() )
			.andReturn();
	    
	    /// info
	    //Uloga korisnik = (Uloga)result.getAsyncResult(1000L); nemoguce pre nego sto se setuje mockup sta da vraca
	    assertNull(result.getModelAndView()); // jer mi radimo rest, nemamo M&V
	    /// info
	    
		radnik2.put("password", "");
		sendData3 = radnik2.toString().getBytes("utf-8");
		// imamo radnika, ali ima "" password, ne moze da postavlja prazan password
		this.mvc.perform(post("/autentification/firstChangeProducer")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(sendData3))
			.andExpect(status().isNotFound())
			.andExpect(jsonPath("$.username", is("Rade")))
			.andExpect(jsonPath("$.password", is("")));
		
		
		radnik2.put("password", "Radic");
		sendData3 = radnik2.toString().getBytes("utf-8");
		// imamo validnog radnika, promenice password
		this.mvc.perform(post("/autentification/firstChangeProducer")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(sendData3))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.username", is("Rade")))
			.andExpect(jsonPath("$.password", is("Radic")));
		
		
		// radnik hoce opet sebi da promeni password, ne moze na istoj metodi
		result = this.mvc.perform(post("/autentification/firstChangeProducer")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(sendData3))
		.andExpect(status().isNotFound())
		.andExpect(jsonPath("$.username").doesNotExist() )
		.andExpect(jsonPath("$.password").doesNotExist() )
		.andReturn();
		
		
	}
}
