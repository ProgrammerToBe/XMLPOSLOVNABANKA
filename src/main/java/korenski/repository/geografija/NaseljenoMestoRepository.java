package korenski.repository.geografija;

import java.util.Collection;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import korenski.model.geografija.Drzava;
import korenski.model.geografija.NaseljenoMesto;

@Repository
public interface NaseljenoMestoRepository extends CrudRepository<NaseljenoMesto, Long>{
	public NaseljenoMesto save(NaseljenoMesto naseljenoMesto);
	public NaseljenoMesto findOne(Long id);
	public void delete(Long id);
	public Set<NaseljenoMesto> findAll();
	public NaseljenoMesto findByOznaka(String oznaka);
	public NaseljenoMesto findByNaziv(String naziv);
	public NaseljenoMesto findByPostanskiBroj(String postanskiBroj);
	public Set<NaseljenoMesto> findByDrzava(Drzava drzava);
	
	@Query("select nm from NaseljenoMesto nm where nm in (select nm1 from NaseljenoMesto nm1 where nm1.naziv like %?2% ) "
			+"and nm in (select nm2 from NaseljenoMesto nm2 where nm2.oznaka like %?1% )"
			+"and nm in (select nm3 from NaseljenoMesto nm3 where nm3.postanskiBroj like %?3% )" 
			+ "and nm in (select nm4 from NaseljenoMesto nm4 where nm4.drzava.id = ?4 )")
	public Set<NaseljenoMesto> filter(String oznaka, String naziv, String postanskiBroj, Long id);
	
	@Query("select nm from NaseljenoMesto nm where nm in (select nm1 from NaseljenoMesto nm1 where nm1.naziv like %?2% ) "
			+"and nm in (select nm2 from NaseljenoMesto nm2 where nm2.oznaka like %?1% )"
			+"and nm in (select nm3 from NaseljenoMesto nm3 where nm3.postanskiBroj like %?3% )")
	public Set<NaseljenoMesto> filterBezDrzave(String oznaka, String naziv, String postanskiBroj);
	
	public Set<NaseljenoMesto> findByOznakaContainingIgnoreCaseOrNazivContainingIgnoreCaseOrPostanskiBrojContainingIgnoreCase(String oznaka,
			String naziv, String postanskiBroj);
	public Collection<NaseljenoMesto> findByOznakaContainingIgnoreCaseOrNazivContainingIgnoreCaseOrPostanskiBrojContainingIgnoreCaseAndDrzava(
			String oznaka, String naziv, String postanskiBroj, Drzava drzava);
	public Collection<NaseljenoMesto> findByOznakaContainingIgnoreCaseOrNazivContainingIgnoreCaseOrPostanskiBrojContainingIgnoreCaseOrDrzava(
			String oznaka, String naziv, String postanskiBroj, Drzava drzava);
}
