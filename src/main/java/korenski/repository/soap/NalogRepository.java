package korenski.repository.soap;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import korenski.soap.nalozi_model.NalogZaPrenos;

@Repository
public interface NalogRepository extends CrudRepository<NalogZaPrenos, Long> {
	public NalogZaPrenos save(NalogZaPrenos nalog);
}
