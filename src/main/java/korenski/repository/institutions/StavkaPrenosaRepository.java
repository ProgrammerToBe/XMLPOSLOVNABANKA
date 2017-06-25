package korenski.repository.institutions;

import java.util.Date;
import java.util.Set;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import korenski.model.infrastruktura.MedjubankarskiPrenos;
import korenski.model.infrastruktura.StavkaPrenosa;

@Repository
public interface StavkaPrenosaRepository extends CrudRepository<StavkaPrenosa,Long>{
	
	public StavkaPrenosa save(StavkaPrenosa stavkaPrenosa);
	public void delete(Long id);
	public Set<StavkaPrenosa> findAll();
	public Set<StavkaPrenosa> findStavkaPrenosaByMedjubankarskiPrenos(MedjubankarskiPrenos medjubankarskiPrenos);
	
	

}
