package mtb.rest;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

@SuppressWarnings("deprecation")
@RunWith(SpringRunner.class)
@SpringBootTest 
@SpringApplicationConfiguration(classes = MtbApplication.class)
public class AdministratorKontrolerTest {

	@Autowired
	private WebApplicationContext context; // ovo omogucava da se ovo posmatra kao web aplikacija

	private MockMvc mvc;
	@Before
	public void setUp() {
		this.mvc = MockMvcBuilders.webAppContextSetup(this.context).build();
	}

	@Test
	public void testAdmin() throws Exception {
		JSONObject administrator = new JSONObject();
		administrator.put("username" , "admin1");
		administrator.put("mail", "admin1@mail.com");
		administrator.put("name", "Pera");
		administrator.put("surname", "Peric");
		administrator.put("role", "administrator");
	    byte[] sendData = administrator.toString().getBytes("utf-8");
	    
	    
	 // registrujemo prvog admina
	 		MvcResult result=this.mvc.perform(post("/registerNewAdministrator")
	 				.content(sendData)
	 				.contentType(MediaType.APPLICATION_JSON_VALUE))
	 					.andExpect(status().isOk())
	 					.andReturn();
	 	
	 // registrujemo menadzera
	 		JSONObject menadzer = new JSONObject();
			menadzer.put("username" , "men1");
			menadzer.put("mail", "men1@mail.com");
			menadzer.put("name", "Pera");
			menadzer.put("surname", "Peric");
			menadzer.put("role", "menadzer");
		    byte[] sendDataM = menadzer.toString().getBytes("utf-8");
		    
		    MvcResult resultM=this.mvc.perform(post("/registerNewManager")
	 				.content(sendDataM)
	 				.contentType(MediaType.APPLICATION_JSON_VALUE))
	 					.andExpect(status().isOk())
	 					.andReturn();
		    
		    this.mvc.perform(get("/allManagers")).andExpect(status().isOk())
			.andExpect(jsonPath("$", hasSize(0) ) );
		
		 // registrujemo dostavljaca
	 		JSONObject dostavljac = new JSONObject();
			dostavljac.put("username" , "men1");
			dostavljac.put("mail", "men1@mail.com");
			dostavljac.put("name", "Pera");
			dostavljac.put("surname", "Peric");
			dostavljac.put("role", "dostavljac");
		    byte[] sendDataD = dostavljac.toString().getBytes("utf-8");
		    
		    MvcResult resultD=this.mvc.perform(post("/registerNewDostavljac")
	 				.content(sendDataD)
	 				.contentType(MediaType.APPLICATION_JSON_VALUE))
	 					.andExpect(status().isOk())
	 					.andReturn();
		    
		    this.mvc.perform(get("/allDostavljaci")).andExpect(status().isOk())
			.andExpect(jsonPath("$", hasSize(0) ) );
		
		 // registrujemo restoran
	 		JSONObject restoran = new JSONObject();
			restoran.put("username" , "men1");
			restoran.put("mail", "men1@mail.com");
			restoran.put("name", "Pera");
			restoran.put("surname", "Peric");
			restoran.put("role", "restoran");
		    byte[] sendDataR = restoran.toString().getBytes("utf-8");
		    
		    MvcResult resultR=this.mvc.perform(post("/registerNewRestaurant")
	 				.content(sendDataD)
	 				.contentType(MediaType.APPLICATION_JSON_VALUE))
	 					.andExpect(status().isOk())
	 					.andReturn();
		    
		    this.mvc.perform(get("/allRestaurants")).andExpect(status().isOk())
			.andExpect(jsonPath("$", hasSize(1) ) );
		
	 	
	}
		
	
}
