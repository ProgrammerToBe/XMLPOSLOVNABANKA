package korenski.controller.testni;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import korenski.model.autorizacija.User;
import korenski.model.geografija.NaseljenoMesto;
import korenski.model.infrastruktura.Bank;
import korenski.model.klijenti.Klijent;
import korenski.repository.autorizacija.UserRepository;
import korenski.repository.klijenti.KlijentRepository;

@Controller
public class CheckIfValidController {

	@Autowired
	UserRepository repository;
	
	
	@RequestMapping(
			value = "/vidiDaLiPuca",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> nadji(@Context HttpServletRequest request) throws Exception {
		
		
		
		return new ResponseEntity<User>( repository.findOne(new Long(8)), HttpStatus.OK);
	}
	
}
