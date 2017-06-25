package korenski.repository.soap;

import org.springframework.data.repository.CrudRepository;

import korenski.soap.odobrenjeclearing.Poruka;

public interface OdobrenjeClearingOdgovorRepository extends CrudRepository<Poruka, Long> {
	public Poruka save(Poruka poruka);
}
