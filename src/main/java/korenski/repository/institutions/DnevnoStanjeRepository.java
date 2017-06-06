package korenski.repository.institutions;

import java.util.Collection;
import java.util.Date;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import korenski.model.infrastruktura.Bank;
import korenski.model.infrastruktura.DnevnoStanjeRacuna;
import korenski.model.infrastruktura.Racun;

@Repository
public interface DnevnoStanjeRepository  extends CrudRepository<DnevnoStanjeRacuna, Long >{

	public DnevnoStanjeRacuna save(DnevnoStanjeRacuna dnevnoStanje);
	public DnevnoStanjeRacuna findByDatumAndRacun(Date datum, Racun racun);
	
	@Query("select s from DnevnoStanjeRacuna s where s.racun.bank.id = ?1 ")
//			+"and z in (select z1 from ZatvaranjeRacuna z1 where z1.racun.brojRacuna like %?2% )"
//			+"and z in (select z2 from ZatvaranjeRacuna z2 where z2.racunPrebacenihSredstava like %?3% )"
//			+"and z in (select z3 from ZatvaranjeRacuna z3 where z3.datumDeaktivacije between ?4 and ?5)")
	public Set<DnevnoStanjeRacuna> searchByBank(Long id);
	
	@Query("select s from DnevnoStanjeRacuna s where s.racun.bank.id = ?1 "
			+"and s in (select s1 from DnevnoStanjeRacuna s1 where s1.racun.brojRacuna like %?2% )"
			+"and s in (select s2 from DnevnoStanjeRacuna s2 where s2.datum between ?3 and ?4)")
	public Collection<DnevnoStanjeRacuna> filter(Long id, String racun, Date pocetak, Date kraj);
	
	
	@Query("select s from DnevnoStanjeRacuna s where s.racun.bank.id = ?1 "
			+"and s in (select s1 from DnevnoStanjeRacuna s1 where s1.racun.id = ?2 )"
			)
	
	public Collection<DnevnoStanjeRacuna> filterByRacunAndBank(Long id, Long racun);
}
