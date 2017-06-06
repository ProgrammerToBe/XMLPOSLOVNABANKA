package korenski.service.sifrarnici;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import korenski.model.sifrarnici.Message;
import korenski.repository.sifrarnici.MessageRepository;

@Service
public class MessageService {
	@Autowired
	MessageRepository messageRepository;
	
	public Message create(Message message){
		return messageRepository.save(message);
	}
	
	public Message update(Message message){
		return messageRepository.save(message);
	}
	
	public void delete(Long id){
		messageRepository.delete(id);
	}
	
	public Message findMessage(Long id){
		return messageRepository.findOne(id);
	}
	
	public Set<Message> finaAll(){
		return messageRepository.findAll();
	}
	
	public Message findByCode(String code){
		return messageRepository.findByCode(code);
	}

}
