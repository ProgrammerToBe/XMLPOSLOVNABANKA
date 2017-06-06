package korenski.controller.geografija;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import korenski.intercepting.CustomAnnotation;
import korenski.model.geografija.Drzava;
import korenski.model.geografija.pomocni.DrzavaFilter;
import korenski.repository.geografija.DrzavaRepository;
import korenski.singletons.ValidatorSingleton;

@Controller
public class DrzavaController {

	@Autowired
	DrzavaRepository repository;
	
	@CustomAnnotation(value = "INSERT_STATE")
	@RequestMapping(
			value = "/novaDrzava",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Drzava> novaDrzava(@RequestBody Drzava drzava , @Context HttpServletRequest request) throws Exception {

		
		
		
		Drzava validity = validityCheck(drzava);
		if(validity != null){
			return new ResponseEntity<Drzava>(validity, HttpStatus.OK);
		}
		
		Drzava drz;
		try {
			drz = repository.save(drzava);
		} catch (Exception e) {
			drz = new Drzava(new Long(-1), null, "Greska pri cuvanju u bazu!", null);
		}
	
		return new ResponseEntity<Drzava>(drz, HttpStatus.OK);
	}
	
	@CustomAnnotation(value = "DELETE_STATE")
	@RequestMapping(
			value = "/obrisiDrzavu/{id}",
			method = RequestMethod.DELETE,
			produces = MediaType.APPLICATION_JSON_VALUE) //String id_string
	public ResponseEntity<Drzava> obrisiDrzavu(@PathVariable("id") Long id , @Context HttpServletRequest request) throws Exception {

		Drzava drzava = repository.findOne(id);
		
		try {
			repository.delete(drzava);
		} catch (Exception e) {
			return new ResponseEntity<Drzava>(new Drzava(new Long(-1), null, null, null), HttpStatus.OK);
		}
	
		
		return new ResponseEntity<Drzava>(new Drzava(), HttpStatus.OK);
	}

	
	@CustomAnnotation(value = "UPDATE_STATE")
	@RequestMapping(
			value = "/azurirajDrzavu",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Drzava> azurirajDrzavu(@RequestBody Drzava drzava , @Context HttpServletRequest request) throws Exception {
		
		Drzava validity = validityCheck(drzava);
		if(validity != null){
			return new ResponseEntity<Drzava>(validity, HttpStatus.OK);
		}
		
		Drzava drzavaToModify = null;
		
		try {
			drzavaToModify = repository.findOne(drzava.getId());
		} catch (Exception e) {
			return new ResponseEntity<Drzava>(new Drzava(new Long(-1), null, "Takva drzava ne postoji u bazi.", null), HttpStatus.OK);
		}
		
		
		drzavaToModify.setOznaka(drzava.getOznaka());
		drzavaToModify.setNaziv(drzava.getNaziv());
		
		try {
			drzavaToModify = repository.save(drzavaToModify);
		} catch (Exception e) {
			return new ResponseEntity<Drzava>(new Drzava(new Long(-1), null, "Greska pri cuvanju u bazi!", null), HttpStatus.OK);
		}

		return new ResponseEntity<Drzava>(drzavaToModify, HttpStatus.OK);
	}
	
	@CustomAnnotation(value = "FIND_ALL_STATE")
	@RequestMapping(
			value = "/sveDrzave",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Drzava>> sveDrzave() throws Exception {

		
//		Method m = DrzavaController.class.getMethod("sveDrzave");
//		
//		String mime = m.getAnnotation(CustomAnnotation.class).value();
//		
//		System.out.println("Mime "+mime);
		
		
		return new ResponseEntity<Collection<Drzava>>( repository.findAll(), HttpStatus.OK);
	}
	
	@CustomAnnotation(value = "FILTER_STATE")
	@RequestMapping(
			value = "/filtrirajDrzave",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Drzava>> filtrirajDrzave(@RequestBody DrzavaFilter drzavaFilter) throws Exception {

		if(drzavaFilter.getOznaka() == null){
			drzavaFilter.setOznaka("");
		}
		
		if(drzavaFilter.getNaziv() == null){
			drzavaFilter.setNaziv("");
		}
		
		
		return new ResponseEntity<Collection<Drzava>>( repository.filter(drzavaFilter.getOznaka(), drzavaFilter.getNaziv()), HttpStatus.OK);
	}
	

	public Drzava validityCheck(Drzava drzava){
		Set<ConstraintViolation<Drzava>> violations = ValidatorSingleton.getInstance().getValidator().validate(drzava);
		
		if(!violations.isEmpty()){
			Iterator iter = violations.iterator();

			ConstraintViolation<Drzava> first = (ConstraintViolation<Drzava>) iter.next();
			Drzava d = new Drzava(new Long(-1), null, first.getMessage(), null);
			return d;
		}else{
			return null;
		}
	}
	
}
