package korenski.repository.institutions;

import java.util.Set;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import korenski.model.infrastruktura.Bank;

@Repository
public interface BankRepository extends CrudRepository<Bank, Long>{
	public Bank save(Bank bank);
	public Bank findOne(Long id);
	public void delete(Long id);
	public Set<Bank> findAll();
	
}
