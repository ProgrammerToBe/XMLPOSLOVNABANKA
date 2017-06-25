package korenski.repository.soap;

import org.springframework.data.repository.CrudRepository;

import korenski.soap.odobrenjertgs.Poruka;

public interface OdobrenjeRtgsOdgovorRepository extends CrudRepository<Poruka, Long>{
	public Poruka save(Poruka poruka);
}
