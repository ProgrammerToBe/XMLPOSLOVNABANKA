package korenski.controller.sifrarnici;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import  org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import korenski.intercepting.CustomAnnotation;
import korenski.model.sifrarnici.Message;
import korenski.service.sifrarnici.MessageService;

@Controller
public class MessageController {
	@Autowired
	MessageService messageService;
	
	@CustomAnnotation(value = "INSERT_MESSAGE")
	@RequestMapping(
			value="/message",
			method=RequestMethod.PUT,
			consumes=MediaType.APPLICATION_JSON_VALUE,
			produces=MediaType.APPLICATION_JSON_VALUE
			)
	public ResponseEntity<Message> createMessage(@RequestBody Message message, @Context HttpServletRequest request){
		Message newMessage=messageService.create(message);
		if(message!=null){
			return new ResponseEntity<Message>(newMessage,HttpStatus.OK);
		}else{
			return new ResponseEntity<Message>(HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@CustomAnnotation(value = "UPDATE_MESSAGE")
	@RequestMapping(
			value="/message",
			method=RequestMethod.POST,
			consumes=MediaType.APPLICATION_JSON_VALUE,
			produces=MediaType.APPLICATION_JSON_VALUE
			)
	public ResponseEntity<Message> updateMessage(@RequestBody Message message, @Context HttpServletRequest request){
		Message newMessage=messageService.create(message);
		if(message!=null){
			return new ResponseEntity<Message>(newMessage,HttpStatus.OK);
		}else{
			return new ResponseEntity<Message>(HttpStatus.BAD_REQUEST);
		}
		
	}
	@CustomAnnotation(value = "FIND_ALL_MESSAGE")
	@RequestMapping(
			value="/messages",
			method=RequestMethod.GET,
			produces=MediaType.APPLICATION_JSON_VALUE
			)
	public ResponseEntity<Set<Message>> findMessages(@Context HttpServletRequest request){
		Set<Message> foundMessages=messageService.finaAll();
		if(foundMessages!=null){
			return new ResponseEntity<Set<Message>>(foundMessages,HttpStatus.OK);
		}
		return new ResponseEntity<Set<Message>>(HttpStatus.NO_CONTENT);
	}
	
	@CustomAnnotation(value = "FIND_MESSAGE_BY_CODE")
	@RequestMapping(
			value="/messageByCode/{code}",
			method=RequestMethod.GET,
			produces=MediaType.APPLICATION_JSON_VALUE
			)
	public ResponseEntity<Message> findMessageByCode(@PathVariable("code") String code, @Context HttpServletRequest request){
		Message foundMessage=messageService.findByCode(code);
		if(foundMessage!=null){
			return new ResponseEntity<Message>(foundMessage,HttpStatus.OK);
		}
		return new ResponseEntity<Message>(HttpStatus.NO_CONTENT);
		
	}
	@CustomAnnotation(value = "FIND_ONE_MESSAGE")
	@RequestMapping(
			value="/message/{id}",
			method=RequestMethod.GET,
			produces=MediaType.APPLICATION_JSON_VALUE
			)
	public ResponseEntity<Message> findMessage(@PathVariable("id") Long id, @Context HttpServletRequest request){
		Message foundMessage=messageService.findMessage(id);
		if(foundMessage!=null){
			return new ResponseEntity<Message>(foundMessage,HttpStatus.OK);
		}
		return new ResponseEntity<Message>(HttpStatus.NO_CONTENT);
		
	}
	
	@CustomAnnotation(value = "DELETE_MESSAGE")
	@RequestMapping(
			value="/message/{id}",
			method=RequestMethod.DELETE,
			produces=MediaType.APPLICATION_JSON_VALUE
			)
	public ResponseEntity<Message> deleteMessage(@PathVariable("id") Long id, @Context HttpServletRequest request){
		Message messageForDelete=messageService.findMessage(id);
		if(messageForDelete!=null){
			messageService.delete(id);
			return new ResponseEntity<Message>(messageForDelete,HttpStatus.OK);
		}
		return new ResponseEntity<Message>(HttpStatus.NO_CONTENT);
	}
}
