package korenski.service.dtos;

import java.math.BigInteger;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import korenski.model.dto.CertificateInfo;
import korenski.model.dto.CertificateInfo.CertStatus;

import korenski.model.infrastruktura.Bank;
import korenski.repository.dtos.CertificateInfoRepository;

@Service
public class CertificateInfoService {
	@Autowired
	CertificateInfoRepository certificateIDRepository;
	
	public CertificateInfo create(CertificateInfo certificateID){
		return certificateIDRepository.save(certificateID);
	}
	
	public CertificateInfo update(CertificateInfo certificateID){
		return certificateIDRepository.save(certificateID);
	}
	
	
	public CertificateInfo findCerificateID(Long id){
		return certificateIDRepository.findOne(id);
	}
	
	public CertificateInfo findBySerialNumberAndCa(BigInteger serialNumber, CertificateInfo ca){
		return certificateIDRepository.findBySerialNumberAndCa(serialNumber, ca);
	}
	
	public Set<CertificateInfo> findBySerialNumber(BigInteger serialNumber){
		return certificateIDRepository.findBySerialNumber(serialNumber);
	}
	
	public Set<CertificateInfo> findByCa(CertificateInfo ca){
		return certificateIDRepository.findByCa(ca);
	}
	
	public Set<CertificateInfo> findByStatus(CertStatus status){
		return certificateIDRepository.findByStatus(status);
	}
	
	public void delete(Long id){
		certificateIDRepository.delete(id);
	}
	
	public CertificateInfo findByAlias(String alias){
		return certificateIDRepository.findByAlias(alias);
	}
	
	public CertificateInfo findCertificateBySerialNumberAndBank(BigInteger serialNumber, Bank bank){
		return certificateIDRepository.findBySerialNumberAndBank(serialNumber, bank);
	}
}
