package korenski.repository.soap;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import korenski.soap.izvestaji_model.Presek;

@Repository
public interface PresekRepository extends CrudRepository<Presek, Long>{
	public Presek save(Presek presek);
}
