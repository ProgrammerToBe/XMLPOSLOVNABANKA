package korenski.repository.dtos;

import java.util.Set;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import korenski.model.dto.RevokeRequest;
import korenski.model.infrastruktura.Bank;

@Repository
public interface RevokeRequestRepository extends CrudRepository<RevokeRequest,Long>{
	public RevokeRequest save(RevokeRequest revokeRequest);
	public RevokeRequest findOne(Long id);
	public void delete(Long id);
	public Set<RevokeRequest> findByBank(Bank bank);
	public Set<RevokeRequest> findAll();
	public RevokeRequest findByBankAndAlias(Bank bank,String alias);

}
