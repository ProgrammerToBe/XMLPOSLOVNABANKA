package korenski.repository.klijenti;

import java.util.Set;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import korenski.model.infrastruktura.Bank;
import korenski.model.klijenti.Employee;
@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long>{
	public Employee save(Employee employee);
	public Employee findOne(Long id);
	public void delete(Long id);
	public Set<Employee> findAll();
	public Set<Employee> findByBank(Bank bank);

}
