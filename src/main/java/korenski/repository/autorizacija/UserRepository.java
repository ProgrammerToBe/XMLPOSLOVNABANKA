package korenski.repository.autorizacija;

import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import korenski.model.autorizacija.Role;
import korenski.model.autorizacija.User;
import korenski.model.infrastruktura.Bank;

@Repository
public interface UserRepository extends CrudRepository<User, Long>{
	public User save(User user);
	public User findOne(Long id);
	public void delete(Long id);
	public Set<User> findAll();
	public User findByUsername(String username);
	
	@Query("select u from User u where u in (select u1 from User u1 where u1.bank.id = ?1 ) "
			+"and u not in (select u2 from User u2 where u2.role.id = ?2 )")
			
	public Set<User> findAllButLegal(Long bank, Long role);
	
	public Set<User> findByBankAndRole(Bank bank, Role role);
}
