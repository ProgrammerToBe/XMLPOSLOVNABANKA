package korenski.controller.institutions;

import java.util.Collection;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import javax.ws.rs.core.Context;

import org.hibernate.validator.constraints.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import korenski.model.infrastruktura.Bank;
import korenski.repository.institutions.BankRepository;

@Controller
public class BankController {

	@Autowired
	BankRepository repository;
	@Autowired
    private Validator validator;

	
	@RequestMapping(
			value = "/allBanks",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Bank>> allBanks() throws Exception {

		
		return new ResponseEntity<Collection<Bank>>( repository.findAll(), HttpStatus.OK);
	}
	
	@RequestMapping(
			value = "/newBank",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Bank> newPermission( @RequestBody Bank bank , @Context HttpServletRequest request) throws Exception {

		
//		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
//		
//		javax.validation.Validator validator = validatorFactory.getValidator();
//		
//		 
//		
//		Set<ConstraintViolation<Bank>> violations = validator.validate(bank);
//		
//		for (ConstraintViolation<Bank> violation : violations) {
//		
//		   String propertyPath = violation.getPropertyPath().toString();
//		
//		    String message = violation.getMessage();
//		
//		    System.out.println("invalid value for: '" + propertyPath + "': " + message);
//		}
		
		Bank banka = null;
		try {
			banka = repository.save(bank);
		} catch (Exception e) {
			e.printStackTrace();
			banka = new Bank();
		}
	
		return new ResponseEntity<Bank>(banka, HttpStatus.OK);
	}
	
}
