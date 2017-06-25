package korenski.repository.soap;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import korenski.soap.clearing.Poruka;

@Repository
public interface ClearingOdgovorRepository extends CrudRepository<Poruka, Long>{
	Poruka save(Poruka poruka);
}
