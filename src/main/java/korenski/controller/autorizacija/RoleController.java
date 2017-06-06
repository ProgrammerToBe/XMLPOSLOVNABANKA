package korenski.controller.autorizacija;

import java.util.Collection;

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

import korenski.intercepting.CustomAnnotation;
import korenski.model.autorizacija.Role;
import korenski.repository.autorizacija.RoleRepository;

@Controller
public class RoleController {

	

	@Autowired
	RoleRepository repository;
	
	@CustomAnnotation(value = "INSERT_ROLE")
	@RequestMapping(
			value = "/newRole",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Role> newRole(@RequestBody Role role , @Context HttpServletRequest request) throws Exception {

		
		Role rle;
		try {
			rle = repository.save(role);
		} catch (Exception e) {
			rle = new Role(new Long(-1), null, null);
		}
	
		return new ResponseEntity<Role>(rle, HttpStatus.OK);
	}
	
	@CustomAnnotation(value = "DELETE_ROLE")
	@RequestMapping(
			value = "/deleteRole/{id}",
			method = RequestMethod.DELETE,
			produces = MediaType.APPLICATION_JSON_VALUE) //String id_string
	public ResponseEntity<Role> deleteRole(@PathVariable("id") Long id , @Context HttpServletRequest request) throws Exception {

		Role Role = repository.findOne(id);
		
		try {
			repository.delete(Role);
		} catch (Exception e) {
			return new ResponseEntity<Role>(new Role(new Long(-1), null, null), HttpStatus.OK);
		}
	
		return new ResponseEntity<Role>(new Role(), HttpStatus.OK);
	}

	
	@CustomAnnotation(value = "UPDATE_ROLE")
	@RequestMapping(
			value = "/updateRole",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Role> updateRole(@RequestBody Role role , @Context HttpServletRequest request) throws Exception {
		
		
		Role roleToModify = null;
		
		try {
			roleToModify = repository.findOne(role.getId());
		} catch (Exception e) {
			return new ResponseEntity<Role>(new Role(new Long(-1), null, null), HttpStatus.OK);
		}
		
		roleToModify.setName(role.getName());
		roleToModify.setPermissions(role.getPermissions());
		
		try {
			roleToModify = repository.save(roleToModify);
		} catch (Exception e) {
			return new ResponseEntity<Role>(new Role(new Long(-1), null, null), HttpStatus.OK);
		}

		return new ResponseEntity<Role>(roleToModify, HttpStatus.OK);
	}
	
	@CustomAnnotation(value = "FIND_ALL_ROLE")
	@RequestMapping(
			value = "/allRoles",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Role>> allRoles() throws Exception {

		
		return new ResponseEntity<Collection<Role>>( repository.findAll(), HttpStatus.OK);
	}
	
	
}
