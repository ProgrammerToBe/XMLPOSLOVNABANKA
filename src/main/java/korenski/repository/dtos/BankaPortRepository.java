package korenski.repository.dtos;

import org.springframework.data.repository.CrudRepository;

import korenski.model.dto.BankaPort;

public interface BankaPortRepository extends CrudRepository<BankaPort, Long>{
	public BankaPort findBySwiftCode(String swiftCode);
}
