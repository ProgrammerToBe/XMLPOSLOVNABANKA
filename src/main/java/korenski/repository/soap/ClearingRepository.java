package korenski.repository.soap;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import korenski.soap.clearing.Clearing;

@Repository
public interface ClearingRepository extends CrudRepository<Clearing, Long> {
	public Clearing save(Clearing clearing);
}
