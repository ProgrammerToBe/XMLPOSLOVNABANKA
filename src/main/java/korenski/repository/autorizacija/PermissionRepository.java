package korenski.repository.autorizacija;

import java.util.Set;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import korenski.model.autorizacija.Permission;

@Repository
public interface PermissionRepository extends CrudRepository<Permission, Long>{
	public Permission save(Permission Permission);
	public Permission findOne(Long id);
	public void delete(Long id);
	public Set<Permission> findAll();
	public Permission findByName(String name);

}