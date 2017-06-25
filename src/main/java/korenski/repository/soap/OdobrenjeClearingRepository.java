package korenski.repository.soap;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import korenski.soap.nalozi_model.NalogZaPrenos;
import korenski.soap.odobrenjeclearing.OdobrenjeClearing;

@Repository
public interface OdobrenjeClearingRepository extends CrudRepository<OdobrenjeClearing, Long> {
	public OdobrenjeClearing save(OdobrenjeClearing odobrenjeClearing);
}
