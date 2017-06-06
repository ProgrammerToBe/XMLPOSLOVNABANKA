package korenski.repository.sifrarnici;

import java.util.Set;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import korenski.model.sifrarnici.Activity;
import korenski.model.sifrarnici.Valuta;

@Repository
public interface ActivityRepository extends CrudRepository<Activity,Long> {
	public Activity save(Activity newActivity);
	public Activity findOne(Long id);
	public void delete(Long id);
	public Activity  findActivityByCode(String code);
	public Activity  findActivityByName(String name);
	public Set<Activity> findAll();
	public Set<Activity> findByCodeContainingIgnoreCaseOrNameContainingIgnoreCase(String code, String name);

	

}
