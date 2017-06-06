package korenski.repository.klijenti;

import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import korenski.model.geografija.NaseljenoMesto;
import korenski.model.infrastruktura.Bank;
import korenski.model.klijenti.Klijent;
import korenski.model.klijenti.PravnoLice;
import korenski.model.sifrarnici.Activity;

@Repository
public interface PravnoLiceRepository extends CrudRepository<PravnoLice, Long>{
	public PravnoLice save(PravnoLice pravnoLice);
	public PravnoLice findOne(Long id);
	public void delete(Long id);
	public Set<PravnoLice> findAll();
	public Set<PravnoLice> findByBank(Bank bank);
	public PravnoLice findByJmbg(String jmbg);
	public PravnoLice findByIme(String ime);
	public PravnoLice findByPrezime(String prezime);
	public PravnoLice findByAdresa(String adresa);
	public PravnoLice findByTelefon(String telefon);
	public PravnoLice findByEmail(String email);
	public PravnoLice findByPib(String pib);
	public PravnoLice findByFax(String fax);
	public PravnoLice findByOdobrio(String odobrio);
	public Set<PravnoLice> findByNaseljenoMesto(NaseljenoMesto naseljenoMesto);
	public Set<PravnoLice> findByNaseljenoMestoAndBank(NaseljenoMesto naseljenoMesto, Bank bank);
	
	@Query("select k from Klijent k where k in (select k1 from Klijent k1 where k1.jmbg like %?1% ) "
			+"and k in (select k2 from Klijent k2 where k2.ime like %?2% )"
			+"and k in (select k3 from Klijent k3 where k3.prezime like %?3% )"
			+"and k in (select k4 from Klijent k4 where k4.adresa like %?4% )"
			+"and k in (select k5 from Klijent k5 where k5.telefon like %?5% )"
			+"and k in (select k6 from Klijent k6 where k6.email like %?6% )"
			+"and k in (select k7 from Klijent k7 where k7.pib like %?7% )"
			+"and k in (select k8 from Klijent k8 where k8.fax like %?8% )"
			+"and k in (select k9 from Klijent k9 where k9.odobrio like %?9% )")
	public Set<PravnoLice> filter(String jmbg, String ime, String prezime, String adresa, String telefon, String email, String pib, String fax, String odobrio);
	
	@Query("select k from Klijent k where k in (select k1 from Klijent k1 where k1.jmbg like %?1% ) "
			+"and k in (select k2 from Klijent k2 where k2.ime like %?2% )"
			+"and k in (select k3 from Klijent k3 where k3.prezime like %?3% )"
			+"and k in (select k4 from Klijent k4 where k4.adresa like %?4% )"
			+"and k in (select k5 from Klijent k5 where k5.telefon like %?5% )"
			+"and k in (select k6 from Klijent k6 where k6.email like %?6% )"
			+"and k in (select k7 from Klijent k7 where k7.pib like %?7% )"
			+"and k in (select k8 from Klijent k8 where k8.fax like %?8% )"
			+"and k in (select k9 from Klijent k9 where k9.odobrio like %?9% )"
			+"and k in (select k10 from Klijent k10 where k10.naseljenoMesto.id = ?10 )")
	public Set<PravnoLice> filterNaseljenoMesto(String jmbg, String ime, String prezime, String adresa, String telefon, String email, String pib, String fax, String odobrio, Long id);
	
	@Query("select k from Klijent k where k in (select k1 from Klijent k1 where k1.jmbg like %?1% ) "
			+"and k in (select k2 from Klijent k2 where k2.ime like %?2% )"
			+"and k in (select k3 from Klijent k3 where k3.prezime like %?3% )"
			+"and k in (select k4 from Klijent k4 where k4.adresa like %?4% )"
			+"and k in (select k5 from Klijent k5 where k5.telefon like %?5% )"
			+"and k in (select k6 from Klijent k6 where k6.email like %?6% )"
			+"and k in (select k7 from Klijent k7 where k7.pib like %?7% )"
			+"and k in (select k8 from Klijent k8 where k8.fax like %?8% )"
			+"and k in (select k9 from Klijent k9 where k9.odobrio like %?9% )"
			+"and k in (select k10 from Klijent k10 where k10.activity.id = ?10 )")
	public Set<PravnoLice> filterDelatnost(String jmbg, String ime, String prezime, String adresa, String telefon, String email, String pib, String fax, String odobrio, Long id);
	
	@Query("select k from Klijent k where k in (select k1 from Klijent k1 where k1.jmbg like %?1% ) "
			+"and k in (select k2 from Klijent k2 where k2.ime like %?2% )"
			+"and k in (select k3 from Klijent k3 where k3.prezime like %?3% )"
			+"and k in (select k4 from Klijent k4 where k4.adresa like %?4% )"
			+"and k in (select k5 from Klijent k5 where k5.telefon like %?5% )"
			+"and k in (select k6 from Klijent k6 where k6.email like %?6% )"
			+"and k in (select k7 from Klijent k7 where k7.pib like %?7% )"
			+"and k in (select k8 from Klijent k8 where k8.fax like %?8% )"
			+"and k in (select k9 from Klijent k9 where k9.odobrio like %?9% )"
			+"and k in (select k10 from Klijent k10 where k10.activity.id = ?10 )"
			+"and k in (select k11 from Klijent k11 where k11.naseljenoMesto.id = ?11 )")
	public Set<PravnoLice> filterSve(String jmbg, String ime, String prezime, String adresa, String telefon, String email, String pib, String fax, String odobrio, Long id1, Long id2);
	
	public Set<PravnoLice> findByJmbgContainingIgnoreCaseOrImeContainingIgnoreCaseOrPrezimeContainingIgnoreCaseOrAdresaContainingIgnoreCaseOrTelefonContainingIgnoreCaseOrEmailContainingIgnoreCaseOrPibContainingIgnoreCaseOrFaxContainingIgnoreCaseOrOdobrioContainingIgnoreCase(String jmbg,
			String ime, String prezime, String adresa, String telefon, String email, String pib, String fax, String odobrio);
	public Set<PravnoLice> findByJmbgContainingIgnoreCaseOrImeContainingIgnoreCaseOrPrezimeContainingIgnoreCaseOrAdresaContainingIgnoreCaseOrTelefonContainingIgnoreCaseOrEmailContainingIgnoreCaseOrPibContainingIgnoreCaseOrFaxContainingIgnoreCaseOrOdobrioContainingIgnoreCaseOrNaseljenoMestoOrActivityAndBank(String jmbg,
			String ime, String prezime, String adresa, String telefon, String email, String pib, String fax, String odobrio, NaseljenoMesto naseljenoMesto, Activity activity, Bank bank);
}
