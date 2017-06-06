package korenski.repository.geografija;

import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


import korenski.model.geografija.Drzava;

@Repository
public interface DrzavaRepository extends CrudRepository<Drzava, Long>{
	public Drzava save(Drzava drzava);
	public Drzava findOne(Long id);
	public void delete(Long id);
	public Set<Drzava> findAll();
	public Drzava findByOznaka(String oznaka);
	public Drzava findByNaziv(String naziv);
	public Set<Drzava> findByOznakaContainingIgnoreCaseOrNazivContainingIgnoreCase(String oznaka, String naziv);
	
	@Query("select d from Drzava d where d in (select d1 from Drzava d1 where d1.naziv like %?2% ) "
			+"and d in (select d2 from Drzava d2 where d2.oznaka like %?1% )")
	public Set<Drzava> filter(String oznaka, String naziv);
}
