package korenski.repository.autorizacija;

import java.util.Set;



import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import korenski.model.autorizacija.Role;



@Repository
public interface RoleRepository extends CrudRepository<Role, Long>{
		public Role save(Role role);
		public Role findOne(Long id);
		public void delete(Long id);
		public Set<Role> findAll();
		public Role findByName(String name);

}
