package korenski.repository.soap;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import korenski.soap.izvestaji_model.ZahtevZaIzvod;

@Repository
public interface IzvodRepository  extends CrudRepository<ZahtevZaIzvod, Long> {
	public ZahtevZaIzvod save(ZahtevZaIzvod izvod);
}
