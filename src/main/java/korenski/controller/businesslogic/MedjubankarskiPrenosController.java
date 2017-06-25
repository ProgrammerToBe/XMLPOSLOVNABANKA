package korenski.controller.businesslogic;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import korenski.DTOs.MedjubankarskiPrenosDTO;
import korenski.intercepting.CustomAnnotation;
import korenski.model.autorizacija.User;
import korenski.model.infrastruktura.MedjubankarskiPrenos;
import korenski.repository.institutions.MedjubankarskiPrenosRepository;

@Controller
public class MedjubankarskiPrenosController {

	@Autowired 
	MedjubankarskiPrenosRepository medjubankarskiPrenosRepository;
	
	
	@CustomAnnotation( value = "FIND_ALL_INTERBANK_TRANSFER" )
	@RequestMapping(
			value="/medjubankarskiPrenos",
			method=RequestMethod.GET,
			produces=MediaType.APPLICATION_JSON_VALUE
			)
	public ResponseEntity<Set<MedjubankarskiPrenos>> findAllMedjubankarskePrenose(@Context HttpServletRequest request){
		System.out.println("**********************************************");
		System.out.println("Filtriranje medjubankarskog prenosa");
		User user=(User) request.getSession().getAttribute("user");
		Set<MedjubankarskiPrenos> medjubankarskiPrenosi=medjubankarskiPrenosRepository.findByBankaPrva(user.getBank());
		if(medjubankarskiPrenosi!=null){
			System.out.println("Pronadjen su zapisi kojima odgovara uslov "+medjubankarskiPrenosi.size());
			System.out.println("**********************************************");
			return new ResponseEntity<Set<MedjubankarskiPrenos>>(medjubankarskiPrenosi, HttpStatus.OK);
		}else{
			System.out.println("Nije pronadjen nijedan medjubankarski prenos koji odgovara datom upitu");
			System.out.println("**********************************************");
			return new ResponseEntity<Set<MedjubankarskiPrenos>>(HttpStatus.NO_CONTENT);
		}
	
		
	}
	
	@CustomAnnotation( value = "FILTER_INTERBANK_TRANSFER")
	@RequestMapping(
			value="/medjubankarskiPrenosi",
			method=RequestMethod.POST,
			consumes=MediaType.APPLICATION_JSON_VALUE,
			produces=MediaType.APPLICATION_JSON_VALUE
			)
	public ResponseEntity<Set<MedjubankarskiPrenos>> findMedjubankarskiPrenosi(@RequestBody MedjubankarskiPrenosDTO mBPDTO, @Context HttpServletRequest request){
		Set<MedjubankarskiPrenos> medjubankarskiPrenosi;
		try{
			if(mBPDTO.getDatum1()==null){
				mBPDTO.setDatum1(new GregorianCalendar(1970, Calendar.JANUARY, 1).getTime());
			}
			if(mBPDTO.getDatum2()==null){
				mBPDTO.setDatum2(new GregorianCalendar(2200, Calendar.JANUARY,1).getTime());
			}
			if(mBPDTO.getIznos2()==0){
				mBPDTO.setIznos2(999999999);
			}
			if(mBPDTO.getBanka()==null && mBPDTO.getPoruka()==null){
				medjubankarskiPrenosi=medjubankarskiPrenosRepository.findBySearch3(mBPDTO.getDatum1(), mBPDTO.getDatum2(),mBPDTO.getIznos1(), mBPDTO.getIznos2());
			}else if(mBPDTO.getBanka()==null){
				medjubankarskiPrenosi=medjubankarskiPrenosRepository.findBySearch2(mBPDTO.getDatum1(), mBPDTO.getDatum2(), mBPDTO.getIznos1(), mBPDTO.getIznos2(), mBPDTO.getPoruka());
			}else if(mBPDTO.getPoruka()==null){
				medjubankarskiPrenosi=medjubankarskiPrenosRepository.findBySearch1(mBPDTO.getDatum1(), mBPDTO.getDatum2(), mBPDTO.getBanka(), mBPDTO.getIznos1(), mBPDTO.getIznos2());
			}else{

			//MedjubankarskiPrenos mbPRenos=medjubankarskiPrenosRepository.findOne(new Long(1));
			
			medjubankarskiPrenosi=medjubankarskiPrenosRepository.findBySearch(mBPDTO.getDatum1(), mBPDTO.getDatum2(), mBPDTO.getBanka(),mBPDTO.getIznos1(), mBPDTO.getIznos2(), mBPDTO.getPoruka());
			}
		}catch(Exception e){
			return new ResponseEntity<Set<MedjubankarskiPrenos>>(HttpStatus.BAD_REQUEST);
		}
		if(medjubankarskiPrenosi!=null){
			return new ResponseEntity<Set<MedjubankarskiPrenos>>(medjubankarskiPrenosi,HttpStatus.OK);
		}else{
			return new ResponseEntity<Set<MedjubankarskiPrenos>>(HttpStatus.NO_CONTENT);
		}
	
	}
}
