package korenski.controller.institutions;

import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

import korenski.controller.institutions.pomocni.ZatvaranjeFilter;
import korenski.intercepting.CustomAnnotation;
import korenski.model.autorizacija.User;
import korenski.model.geografija.NaseljenoMesto;
import korenski.model.infrastruktura.Bank;
import korenski.model.infrastruktura.Racun;
import korenski.model.infrastruktura.ZatvaranjeRacuna;
import korenski.repository.institutions.BankRepository;
import korenski.repository.institutions.RacunRepository;
import korenski.repository.institutions.ZatvaranjeRacunaRepository;

@Controller
public class ZatvaranjeRacunaController {

	@Autowired
	ZatvaranjeRacunaRepository repository;
	
	@Autowired 
	BankRepository bankRepository;
	@Autowired
	RacunRepository racunRepository;
	
	@CustomAnnotation(value = "FIND_ALL_DEACTIVATION")
	@RequestMapping(
			value = "/svaZatvaranja",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<ZatvaranjeRacuna>> svaZatvaranja(@Context HttpServletRequest request) throws Exception {
		User u = (User)request.getSession().getAttribute("user");
		Bank bank = bankRepository.findOne(u.getBank().getId());
		
		Collection<ZatvaranjeRacuna> zatvaranja = repository.findBySearch(bank.getId());
		
		return new ResponseEntity<Collection<ZatvaranjeRacuna>>( zatvaranja, HttpStatus.OK);
	}
	
	@CustomAnnotation(value = "FILTER_DEACTIVATION_BY_ACCOUNT")
	@RequestMapping(
			value = "/filtrirajZatvaranjaPoRacunu/{racun}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<ZatvaranjeRacuna>> filtrirajRacune(@PathVariable("racun") Long id, @Context HttpServletRequest request) throws Exception {
		
		Racun racun = racunRepository.findOne(id);
		Collection<ZatvaranjeRacuna> zatvaranje = repository.findByRacun(racun);
		
		return new ResponseEntity<Collection<ZatvaranjeRacuna>>(zatvaranje, HttpStatus.OK);
	}
	
	@CustomAnnotation(value = "FILTER_DEACTIVATION")
	@RequestMapping(
			value = "/filtrirajZatvaranja",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<ZatvaranjeRacuna>> filtriraj(@RequestBody ZatvaranjeFilter zatvaranjeFilter, @Context HttpServletRequest request) throws Exception {
		
		if(zatvaranjeFilter.getRacunZatvaranja() == null){
			zatvaranjeFilter.setRacunZatvaranja("");
		}
		
		if(zatvaranjeFilter.getRacunPrenosa() == null ){
			zatvaranjeFilter.setRacunPrenosa("");
		}
		
		java.sql.Date pocetak = null;
		java.sql.Date kraj = null;
		
		if(zatvaranjeFilter.getPocetak() == null && zatvaranjeFilter.getKraj() == null){
			Date d = new Date(0L);
			pocetak = new java.sql.Date(d.getTime());
			System.out.println("Pocetak "+pocetak.toString());
			Date current = new Date();
			kraj = new java.sql.Date(current.getTime());
			System.out.println("Kraj "+kraj.toString());
		}
		
		if(zatvaranjeFilter.getPocetak() == null && zatvaranjeFilter.getKraj() != null){
			Date d = new Date(0L);
			pocetak = new java.sql.Date(d.getTime());
			kraj = new java.sql.Date(zatvaranjeFilter.getKraj().getTime());
		}
		
		if(zatvaranjeFilter.getPocetak() != null && zatvaranjeFilter.getKraj() == null){
			pocetak = new java.sql.Date(zatvaranjeFilter.getPocetak().getTime());
			Date current = new Date();
			kraj = new java.sql.Date(current.getTime());
		}
		
		if(zatvaranjeFilter.getPocetak() != null && zatvaranjeFilter.getKraj() != null){
			pocetak = new java.sql.Date(zatvaranjeFilter.getPocetak().getTime());
			
			kraj = new java.sql.Date(zatvaranjeFilter.getKraj().getTime());
		}
		
		User userFromSession = (User) request.getSession().getAttribute("user");
		Long id = userFromSession.getBank().getId();
		
		System.out.println("Pocetak "+pocetak.toString());
		System.out.println("Kraj "+kraj.toString());
		return new ResponseEntity<Collection<ZatvaranjeRacuna>>( repository.filter(id,zatvaranjeFilter.getRacunZatvaranja(), zatvaranjeFilter.getRacunPrenosa(), pocetak, kraj), HttpStatus.OK);
		//return new ResponseEntity<Collection<ZatvaranjeRacuna>>( repository.filter(id,zatvaranjeFilter.getRacunZatvaranja()), HttpStatus.OK);
		//return new ResponseEntity<Collection<ZatvaranjeRacuna>>( repository.filter(id,zatvaranjeFilter.getRacunZatvaranja(), zatvaranjeFilter.getRacunPrenosa()), HttpStatus.OK);
	}
	
}
