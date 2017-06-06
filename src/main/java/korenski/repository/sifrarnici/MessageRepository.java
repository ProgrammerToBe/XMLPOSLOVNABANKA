package korenski.repository.sifrarnici;

import java.util.Set;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import korenski.model.sifrarnici.Message;

@Repository
public interface MessageRepository extends CrudRepository<Message, Long> {
	
	public Message findOne(Long id);
	public Set<Message> findAll();
	public Message findByCode(String code);
	public void delete(Long id);
	public Message save(Message message);

}
