package korenski.repository.klijenti;

import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import korenski.model.klijenti.Klijent;
import korenski.model.geografija.NaseljenoMesto;
import korenski.model.infrastruktura.Bank;

@Repository
public interface KlijentRepository extends CrudRepository<Klijent, Long>{
	public Klijent save(Klijent klijent);
	public Klijent findOne(Long id);
	public void delete(Long id);
	public Set<Klijent> findAll();
	public Set<Klijent> findByFizickoLice(boolean fizickoLice);
	public Set<Klijent> findByFizickoLiceAndBank(boolean fizickoLice, Bank bank);
	public Klijent findByJmbg(String jmbg);
	public Klijent findByIme(String ime);
	public Klijent findByPrezime(String prezime);
	public Klijent findByAdresa(String adresa);
	public Klijent findByTelefon(String telefon);
	public Klijent findByEmail(String email);
	public Set<Klijent> findByNaseljenoMesto(NaseljenoMesto naseljenoMesto);
	public Set<Klijent> findByNaseljenoMestoAndFizickoLice(NaseljenoMesto naseljenoMest, boolean fizickoLice);
	public Set<Klijent>	findByNaseljenoMestoAndFizickoLiceAndBank(NaseljenoMesto naseljenoMest, boolean fizickoLice, Bank bank);
	
	@Query("select k from Klijent k where k in (select k1 from Klijent k1 where k1.jmbg like %?1% ) "
			+"and k in (select k2 from Klijent k2 where k2.ime like %?2% )"
			+"and k in (select k3 from Klijent k3 where k3.prezime like %?3% )"
			+"and k in (select k4 from Klijent k4 where k4.adresa like %?4% )"
			+"and k in (select k5 from Klijent k5 where k5.telefon like %?5% )"
			+"and k in (select k6 from Klijent k6 where k6.email like %?6% )")
	public Set<Klijent> filter(String jmbg, String ime, String prezime, String adresa, String telefon, String email);
	
	@Query("select k from Klijent k where k in (select k1 from Klijent k1 where k1.jmbg like %?1% ) "
			+"and k in (select k2 from Klijent k2 where k2.ime like %?2% )"
			+"and k in (select k3 from Klijent k3 where k3.prezime like %?3% )"
			+"and k in (select k4 from Klijent k4 where k4.adresa like %?4% )"
			+"and k in (select k5 from Klijent k5 where k5.telefon like %?5% )"
			+"and k in (select k6 from Klijent k6 where k6.email like %?6% )"
			+"and k in (select k7 from Klijent k7 where k7.naseljenoMesto.id = ?7 )")
	public Set<Klijent> filterNaseljenoMesto(String jmbg, String ime, String prezime, String adresa, String telefon, String email, Long id);
	
	
	public Set<Klijent> findByJmbgContainingIgnoreCaseOrImeContainingIgnoreCaseOrPrezimeContainingIgnoreCaseOrAdresaContainingIgnoreCaseOrTelefonContainingIgnoreCaseOrEmailContainingIgnoreCase(String jmbg,
			String ime, String prezime, String adresa, String telefon, String email);
	public Set<Klijent> findByJmbgContainingIgnoreCaseOrImeContainingIgnoreCaseOrPrezimeContainingIgnoreCaseOrAdresaContainingIgnoreCaseOrTelefonContainingIgnoreCaseOrEmailContainingIgnoreCaseOrNaseljenoMesto(String jmbg,
			String ime, String prezime, String adresa, String telefon, String email, NaseljenoMesto naseljenoMesto);
	public Set<Klijent> findByJmbgContainingIgnoreCaseOrImeContainingIgnoreCaseOrPrezimeContainingIgnoreCaseOrAdresaContainingIgnoreCaseOrTelefonContainingIgnoreCaseOrEmailContainingIgnoreCaseOrNaseljenoMestoAndFizickoLiceAndBank(String jmbg,
			String ime, String prezime, String adresa, String telefon, String email, NaseljenoMesto naseljenoMesto, boolean fizickoLice, Bank bank);

}
