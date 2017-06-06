package korenski.repository.dtos;

import java.math.BigInteger;
import java.util.Date;
import java.util.Set;

import org.springframework.data.repository.CrudRepository;

import korenski.model.dto.CertificateInfo;
import korenski.model.dto.CertificateInfo.CertStatus;

import korenski.model.infrastruktura.Bank;

public interface CertificateInfoRepository  extends CrudRepository<CertificateInfo,Long>{
	public CertificateInfo save(CertificateInfo certificateID);
	public void delete(Long id);
	public CertificateInfo findOne(Long id);
	public CertificateInfo findBySerialNumberAndCa(BigInteger serialNumber,CertificateInfo ca);
	public Set<CertificateInfo> findByCa(CertificateInfo ca);
	public Set<CertificateInfo> findBySerialNumber(BigInteger serialNumber);
	public Set<CertificateInfo> findByStatus(CertStatus status);
	public Set<CertificateInfo> findByDateOfRevocation(Date dateOfRevocation);
	public Set<CertificateInfo> findAll();
	public CertificateInfo findByAlias(String alias);
	public CertificateInfo findBySerialNumberAndBank(BigInteger serialNumber, Bank bank);
}
