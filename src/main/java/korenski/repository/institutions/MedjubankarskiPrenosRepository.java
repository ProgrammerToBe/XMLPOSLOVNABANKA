package korenski.repository.institutions;

import java.util.Date;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import korenski.model.infrastruktura.Bank;
import korenski.model.infrastruktura.MedjubankarskiPrenos;
import korenski.model.sifrarnici.Message;

@Repository
public interface MedjubankarskiPrenosRepository extends CrudRepository<MedjubankarskiPrenos, Long>{
	public MedjubankarskiPrenos save(MedjubankarskiPrenos medjubankarskiPrenos);
	public void delete(Long id);
	public Set<MedjubankarskiPrenos> findByBankaPrva(Bank banka);
	public Set<MedjubankarskiPrenos> findAll();
	@Query("select max(m.datum) from MedjubankarskiPrenos m where m.poruka!='2' and m.bankaPrva=?1 and m.bankaDruga=?2 ")
	public Date findMaxDate(Bank banka1, Bank banka2);
	@Query("select m from MedjubankarskiPrenos m where m.datum=?1")
	public MedjubankarskiPrenos findLatestMedjubankarskiPrenos(Date maxDate);
	
	@Query("select m from MedjubankarskiPrenos m where  m.datum between ?1 and ?2 and m.bankaDruga=?3 and m.iznos between ?4 and ?5 and m.poruka=?6")
	public Set<MedjubankarskiPrenos> findBySearch(Date date1, Date date2, Bank bank, double iznos1, double iznos2, Message poruka );
	@Query("select m from MedjubankarskiPrenos m where m.datum between ?1 and ?2 and m.bankaDruga=?3 and m.iznos between ?4 and ?5")
	public Set<MedjubankarskiPrenos> findBySearch1(Date date1, Date date2, Bank bank, double iznos1, double iznos2);
	@Query("select m from MedjubankarskiPrenos m where m.datum between ?1 and ?2 and m.iznos between ?3 and ?4 and m.poruka=?5")
	public Set<MedjubankarskiPrenos> findBySearch2(Date date1, Date date2, double iznos1, double iznos2, Message poruka);
	
	@Query("select m from MedjubankarskiPrenos m where m.datum between ?1 and ?2 and m.iznos between ?3 and ?4")
	public Set<MedjubankarskiPrenos> findBySearch3(Date date1, Date date2, double iznos1, double iznos2);

}
