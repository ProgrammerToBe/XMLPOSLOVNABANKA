package korenski.repository.institutions;

import java.util.Collection;
import java.util.Date;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import korenski.model.infrastruktura.AnalitikaIzvoda;
import korenski.model.infrastruktura.DnevnoStanjeRacuna;

@Repository
public interface AnalitikaIzvodaRepository extends CrudRepository<AnalitikaIzvoda,Long>{
	
	public AnalitikaIzvoda save(AnalitikaIzvoda analitikaIzvoda);
	public void delete(Long id);
	public Set<AnalitikaIzvoda> findAll();
	
	@Query("select a from AnalitikaIzvoda a where a.dnevnoStanjeRacuna.racun.bank.id = ?1 ")
	public Set<AnalitikaIzvoda> searchByBank(Long id);
	
	//	@Query("select r from Racun r where r.status = ?1 or r.datumOtvaranja between ?2 and ?3 or r.klijent.ime like ?4 or r.klijent.prezime like ?5 and r.bank.id = ?6")
	//public Set<Racun> findBySearch(boolean status, Date datumOtvaranjaOd, Date datumOtvaranjaDo, String ime, String prezime, Long id);
	@Query("select a from AnalitikaIzvoda a where a.datumAnalitike between ?1 and ?2")
	public Set<AnalitikaIzvoda> findBySearch(Date firstDate, Date secondDate);
	@Query("select a from AnalitikaIzvoda a where a.smer=?1 and a.racunPrvi=?2")
	public Set<AnalitikaIzvoda> findBySearch(String direction, String firstAccount);
	
	public Set<AnalitikaIzvoda> findByDnevnoStanjeRacuna(DnevnoStanjeRacuna dnevnoStanjeRacuna);
	/*
	 * Moguce je da ce doci do izmjene u slucaju da vrijednosti koje predstavljaju datum treba da se krecu 
	 * u odredjenom opsegu kao i za iznose ili da vrijednosti tipa string se vrse na osnovu %like%
	 */
	@Query("select a from AnalitikaIzvoda a where a.dnevnoStanjeRacuna.racun.bank.id = ?1"
			+" and a in (select a1 from AnalitikaIzvoda a1 where a1.racunPrvi like %?2% ) "
			+"and a in (select a2 from AnalitikaIzvoda a2 where a2.modelPrvi like %?3% ) "
			+"and a in (select a3 from AnalitikaIzvoda a3 where a3.pozivNaBrojPrvi like %?4% ) "
			+"and a in (select a4 from AnalitikaIzvoda a4 where a4.racunDrugi like %?5% ) "
			+"and a in (select a5 from AnalitikaIzvoda a5 where a5.modelDrugi like %?6% ) "
			+"and a in (select a6 from AnalitikaIzvoda a6 where a6.pozivNaBrojDrugi like %?7% ) "
			+"and a in (select a7 from AnalitikaIzvoda a7 where a7.hitno = ?8 ) "
			+"and a in (select a8 from AnalitikaIzvoda a8 where a8.datumAnalitike between ?9 and ?10 )" 
			+"and a in (select a9 from AnalitikaIzvoda a9 where a9.datumNaloga between ?11 and ?12 ) "
			+"and a in (select a10 from AnalitikaIzvoda a10 where a10.datumValute between ?13 and ?14 ) "
			)
	public Set<AnalitikaIzvoda> filter(Long id, String racunDuznika, String modelDuznika, 
			String pozivNaBrojDuznika,
			String racunPoverioca, String modelPoverioca, String pozivNaBrojPoverioca
			, boolean hitno
			,Date datumAnalitikePocetak,Date datumAnalitikeKraj
			,Date datumNalogaPocetak, Date datumNalogaKraj
			,Date datumValutePocetak, Date datumValuteKraj
			);
	
	@Query("select a from AnalitikaIzvoda a where a.dnevnoStanjeRacuna.racun.bank.id = ?1"
			+" and a in (select a1 from AnalitikaIzvoda a1 where a1.dnevnoStanjeRacuna.id = ?2 ) ")
	
	public Collection<AnalitikaIzvoda> filterByDnevnoStanjeAndBanka(Long id, Long id2);
}
