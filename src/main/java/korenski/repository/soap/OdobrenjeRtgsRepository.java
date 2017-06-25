package korenski.repository.soap;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import korenski.soap.nalozi_model.NalogZaPrenos;
import korenski.soap.odobrenjertgs.OdobrenjeRtgs;

@Repository
public interface OdobrenjeRtgsRepository extends CrudRepository<OdobrenjeRtgs, Long> {
	public OdobrenjeRtgs save(OdobrenjeRtgs odobrenjeRtgs);
}
