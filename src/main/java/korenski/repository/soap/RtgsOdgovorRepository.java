package korenski.repository.soap;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import korenski.soap.rtgs.Poruka;

@Repository
public interface RtgsOdgovorRepository extends CrudRepository<Poruka, Long> {
	public Poruka save(Poruka poruka);
}
