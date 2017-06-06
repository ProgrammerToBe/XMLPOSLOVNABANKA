package korenski.controller.institutions;

import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
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

import korenski.controller.institutions.pomocni.StanjaFilter;
import korenski.intercepting.CustomAnnotation;
import korenski.model.autorizacija.User;
import korenski.model.infrastruktura.Bank;
import korenski.model.infrastruktura.DnevnoStanjeRacuna;
import korenski.model.infrastruktura.Racun;
import korenski.model.infrastruktura.ZatvaranjeRacuna;
import korenski.repository.institutions.BankRepository;
import korenski.repository.institutions.DnevnoStanjeRepository;
import korenski.repository.institutions.RacunRepository;

@Controller
public class DnevnaStanjaController {
	
	@Autowired
	DnevnoStanjeRepository repository;
	@Autowired
	BankRepository bankRepository;
	@Autowired
	RacunRepository racunRepository;

	@CustomAnnotation(value = "FIND_ALL_DAILY_STATE")
	@RequestMapping(
			value = "/svaDnevnaStanja",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<DnevnoStanjeRacuna>> svaDnevnaStanja(@Context HttpServletRequest request) throws Exception {
		User u = (User)request.getSession().getAttribute("user");
		Bank bank = bankRepository.findOne(u.getBank().getId());
		
		return new ResponseEntity<Collection<DnevnoStanjeRacuna>>( repository.searchByBank(bank.getId()), HttpStatus.OK);
	}
	
	@CustomAnnotation(value = "FILTER_DAILY_STATE")
	@RequestMapping(
			value = "/filtrirajStanja",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<DnevnoStanjeRacuna>> filtrirajStanja(@RequestBody StanjaFilter filter, @Context HttpServletRequest request) throws Exception {
		
		User u = (User)request.getSession().getAttribute("user");
		Bank bank = bankRepository.findOne(u.getBank().getId());
		
		if(filter.getRacun() == null){
			filter.setRacun("");
		}
		
		if(filter.getRacun().equals("")){
			filter.setRacun("");
		}
		

		java.sql.Date pocetak = null;
		java.sql.Date kraj = null;
		
		if(filter.getPocetak() == null && filter.getKraj() == null){
			Date d = new Date(0L);
			pocetak = new java.sql.Date(d.getTime());
			System.out.println("Pocetak "+pocetak.toString());
			Date current = new Date();
			kraj = new java.sql.Date(current.getTime());
			System.out.println("Kraj "+kraj.toString());
		}
		
		if(filter.getPocetak() == null && filter.getKraj() != null){
			Date d = new Date(0L);
			pocetak = new java.sql.Date(d.getTime());
			kraj = new java.sql.Date(filter.getKraj().getTime());
		}
		
		if(filter.getPocetak() != null && filter.getKraj() == null){
			pocetak = new java.sql.Date(filter.getPocetak().getTime());
			Date current = new Date();
			kraj = new java.sql.Date(current.getTime());
		}
		
		if(filter.getPocetak() != null && filter.getKraj() != null){
			pocetak = new java.sql.Date(filter.getPocetak().getTime());
			
			kraj = new java.sql.Date(filter.getKraj().getTime());
		}
		
		return new ResponseEntity<Collection<DnevnoStanjeRacuna>>(repository.filter(bank.getId(), filter.getRacun(), pocetak, kraj), HttpStatus.OK);
	}
	
	@CustomAnnotation(value = "FIND_DAILY_STATE_BY_ACCOUNT")
	@RequestMapping(
			value = "/filtrirajStanjaPoRacunu/{racun}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<DnevnoStanjeRacuna>> filtrirajRacune(@PathVariable("racun") Long id, @Context HttpServletRequest request) throws Exception {
		
		User user = (User) request.getSession().getAttribute("user");
		Bank bank = bankRepository.findOne(user.getBank().getId());
		
		Racun racun = racunRepository.findOne(id);
		Collection<DnevnoStanjeRacuna> stanja = repository.filterByRacunAndBank(bank.getId(), racun.getId());
		
		return new ResponseEntity<Collection<DnevnoStanjeRacuna>>(stanja, HttpStatus.OK);
	}
}
