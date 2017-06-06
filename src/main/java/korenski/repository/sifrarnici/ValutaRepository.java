package korenski.repository.sifrarnici;

import java.util.Set;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import korenski.model.geografija.Drzava;
import korenski.model.sifrarnici.Valuta;

@Repository
public interface ValutaRepository extends CrudRepository<Valuta,Long> {
	public Valuta save(Valuta valuta);
	public Valuta findOne(Long id);
	public void delete(Long id);
	public Set<Valuta> findAll();
	public Valuta findByCode(String code);
	public Valuta findByName(String name);
	public Set<Valuta> findByCodeContainingIgnoreCaseOrNameContainingIgnoreCase(String code, String name);

}
