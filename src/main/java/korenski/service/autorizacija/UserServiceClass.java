package korenski.service.autorizacija;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import korenski.repository.autorizacija.UserRepository;

@Service
public class UserServiceClass {
	
	@Autowired
	UserRepository repository;
	
	public UserRepository getRepository(){
		return repository;
	}

}
