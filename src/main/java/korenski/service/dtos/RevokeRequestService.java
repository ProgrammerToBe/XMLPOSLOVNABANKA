package korenski.service.dtos;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import korenski.model.dto.RevokeRequest;
import korenski.model.infrastruktura.Bank;
import korenski.repository.dtos.RevokeRequestRepository;

@Service
public class RevokeRequestService {

	@Autowired
	RevokeRequestRepository revokeRequestRepository;
	
	public RevokeRequest save(RevokeRequest revokeRequest){
		return revokeRequestRepository.save(revokeRequest);
	}
	
	public RevokeRequest update(RevokeRequest revokeRequest){
		return revokeRequestRepository.save(revokeRequest);
	}
	public  Set<RevokeRequest> findRevokedRequests(){
		return revokeRequestRepository.findAll();
	}
	
	public RevokeRequest findRevokeRequest(Long id){
		return revokeRequestRepository.findOne(id);
	}
	
	public RevokeRequest deleteRevokeRequest(Long id){
		RevokeRequest revokeRequest=revokeRequestRepository.findOne(id);
		if(revokeRequest!=null){
			revokeRequestRepository.delete(id);
			
		}
		return revokeRequest;
	}
	
	public Set<RevokeRequest> findRevokeRequestByBank(Bank bank){
		return revokeRequestRepository.findByBank(bank);
	}
	
	public RevokeRequest findRevokeRequestByBankAndAlias(Bank bank,String alias){
		return revokeRequestRepository.findByBankAndAlias(bank, alias);
	}
}
