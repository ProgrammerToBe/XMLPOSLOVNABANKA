package korenski.controller.geografija;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
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
import korenski.model.geografija.NaseljenoMesto;
import korenski.model.geografija.pomocni.NMFilter;
import korenski.repository.geografija.DrzavaRepository;
import korenski.repository.geografija.NaseljenoMestoRepository;
import korenski.singletons.ValidatorSingleton;

@Controller
public class NaseljenoMestoController {

	@Autowired
	NaseljenoMestoRepository repository;
	@Autowired
	DrzavaRepository repD;
	
	@CustomAnnotation(value = "INSERT_CITY")
	@RequestMapping(
			value = "/novoNaseljenoMesto",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<NaseljenoMesto> novaNaseljenoMesto(@RequestBody NaseljenoMesto naseljenoMesto , @Context HttpServletRequest request) throws Exception {

		NaseljenoMesto validity = validityCheck(naseljenoMesto);
		if(validity != null){
			return new ResponseEntity<NaseljenoMesto>(validity, HttpStatus.OK);
		}
		
		
		NaseljenoMesto nm = null;
		
		try {
			nm = repository.save(naseljenoMesto);
		} catch (Exception e) {
			return new ResponseEntity<NaseljenoMesto>(new NaseljenoMesto(new Long(-1), null, "Greska pri cuvanju u bazu!", null, null), HttpStatus.OK);
		}
		
		return new ResponseEntity<NaseljenoMesto>(nm, HttpStatus.OK);
	}
	
	@CustomAnnotation(value = "DELETE_CITY")
	@RequestMapping(
			value = "/obrisiNaseljenoMesto/{id}",
			method = RequestMethod.DELETE,
			produces = MediaType.APPLICATION_JSON_VALUE) //String id_string
	public ResponseEntity<NaseljenoMesto> obrisiNaseljenoMesto(@PathVariable("id") Long id , @Context HttpServletRequest request) throws Exception {

		NaseljenoMesto naseljenoMesto = repository.findOne(id);
		repository.delete(naseljenoMesto);
		return new ResponseEntity<NaseljenoMesto>(naseljenoMesto, HttpStatus.OK);
	}

	
	@CustomAnnotation(value = "UPDATE_CITY")
	@RequestMapping(
			value = "/azurirajNaseljenoMesto",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<NaseljenoMesto> azurirajNaseljenoMesto(@RequestBody NaseljenoMesto naseljenoMesto , @Context HttpServletRequest request) throws Exception {
		
		
		NaseljenoMesto naseljenoMestoToModify = repository.findOne(naseljenoMesto.getId());
		Drzava drzava = repD.findOne(naseljenoMesto.getDrzava().getId());
		
		naseljenoMestoToModify.setOznaka(naseljenoMesto.getOznaka());
		naseljenoMestoToModify.setNaziv(naseljenoMesto.getNaziv());
		naseljenoMestoToModify.setPostanskiBroj(naseljenoMesto.getPostanskiBroj());
		naseljenoMestoToModify.setDrzava(drzava);

		NaseljenoMesto validity = validityCheck(naseljenoMesto);
		if(validity != null){
			return new ResponseEntity<NaseljenoMesto>(validity, HttpStatus.OK);
		}
		
		try {
			naseljenoMestoToModify = repository.save(naseljenoMestoToModify);
		} catch (Exception e) {
			return new ResponseEntity<NaseljenoMesto>(new NaseljenoMesto(new Long(-1), null, "Greska pri cuvanju u bazu!", null, null), HttpStatus.OK);
		}
		
		return new ResponseEntity<NaseljenoMesto>(naseljenoMestoToModify, HttpStatus.OK);
	}
	
	@CustomAnnotation(value = "FIND_ALL_CITY")
	@RequestMapping(
			value = "/svaNaseljenaMesta",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<NaseljenoMesto>> svaNaseljenaMesta() throws Exception {

		
		return new ResponseEntity<Collection<NaseljenoMesto>>( repository.findAll(), HttpStatus.OK);
	}
	
	@CustomAnnotation(value = "FIND_CITY_BY_STATE")
	@RequestMapping(
			value = "/nadjiNaseljenaMesta/{id}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<NaseljenoMesto>> drzavinaNaseljenaMesta(@PathVariable("id") Long id) throws Exception {

		
		Drzava d = repD.findOne(id);
		
		return new ResponseEntity<Collection<NaseljenoMesto>>( repository.findByDrzava(d), HttpStatus.OK);
	}
	
	@RequestMapping(
			value = "/filtrirajNaseljenaMesta/{oznaka}/{naziv}/{postanski_broj}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<NaseljenoMesto>> filtrirajNaseljenaMesta(@PathVariable("oznaka") String oznaka,
			@PathVariable("naziv") String naziv, @PathVariable("postanski_broj") String postanskiBroj) throws Exception {

		
		return new ResponseEntity<Collection<NaseljenoMesto>>( repository.findByOznakaContainingIgnoreCaseOrNazivContainingIgnoreCaseOrPostanskiBrojContainingIgnoreCase(oznaka, naziv, postanskiBroj), HttpStatus.OK);
	}
	
	@CustomAnnotation(value = "FILTER_CITY")
	@RequestMapping(
			value = "/filtrirajNaseljenaMestaZaDrzavu",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<NaseljenoMesto>> filtrirajNaseljenaMestaDrzave(@RequestBody NMFilter nmFilter) throws Exception {
		
		if(nmFilter.getOznaka() == null){
			nmFilter.setOznaka("");
		}
		
		if(nmFilter.getNaziv() == null){
			nmFilter.setNaziv("");
		}
		
		if(nmFilter.getPostanskiBroj() == null){
			nmFilter.setPostanskiBroj("");
		}
		
		if(nmFilter.getDrzava() == null){
			nmFilter.setDrzava(new Long(0));
		}
		
		if(nmFilter.getDrzava().equals(new Long(0))){
			return new ResponseEntity<Collection<NaseljenoMesto>>( repository.filterBezDrzave(nmFilter.getOznaka(), nmFilter.getNaziv(), nmFilter.getPostanskiBroj()), HttpStatus.OK);
		}
		//repository.findByOznakaContainingIgnoreCaseOrNazivContainingIgnoreCaseOrPostanskiBrojContainingIgnoreCaseOrDrzava(oznaka, naziv, postanskiBroj, drzava)
		return new ResponseEntity<Collection<NaseljenoMesto>>( repository.filter(nmFilter.getOznaka(), nmFilter.getNaziv(), nmFilter.getPostanskiBroj(), nmFilter.getDrzava()), HttpStatus.OK);
	}

	
	public NaseljenoMesto validityCheck(NaseljenoMesto naseljenoMesto){
		Set<ConstraintViolation<NaseljenoMesto>> violations = ValidatorSingleton.getInstance().getValidator().validate(naseljenoMesto);
		
		if(!violations.isEmpty()){
			Iterator iter = violations.iterator();

			ConstraintViolation<NaseljenoMesto> first = (ConstraintViolation<NaseljenoMesto>) iter.next();
			NaseljenoMesto nm = new NaseljenoMesto(new Long(-1), null, first.getMessage(), null, null);
			return nm;
		}else{
			return null;
		}
	}
}
