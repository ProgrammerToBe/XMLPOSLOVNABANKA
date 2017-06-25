package korenski.repository.soap;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import korenski.soap.rtgs.RTGS;

@Repository
public interface RtgsRepository extends CrudRepository<RTGS, Long> {
	public RTGS save(RTGS rtgs); 
}
