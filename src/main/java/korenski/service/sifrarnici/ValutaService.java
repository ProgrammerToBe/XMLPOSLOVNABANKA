package korenski.service.sifrarnici;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import korenski.model.sifrarnici.Valuta;
import korenski.repository.sifrarnici.ValutaRepository;

@Service
public class ValutaService {
	@Autowired
	ValutaRepository valutaRepository;
	
	public Valuta createValuta(Valuta valuta){
		return valutaRepository.save(valuta);
	}
	
	public Valuta updateValuta(Valuta valuta){
		return valutaRepository.save(valuta);
	}
	
	public void deleteValuta(Long id){
		valutaRepository.delete(id);
	}
	
	public Valuta findValuta(Long id){
		return valutaRepository.findOne(id);
	}
	
	public Set<Valuta> findAllValuta(){
		return valutaRepository.findAll();
	}
	
	public Valuta findValutaByCode(String code){
		return valutaRepository.findByCode(code);
	}
	
	public Valuta findValutaByName(String name){
		return valutaRepository.findByName(name);
	}
	
	public Set<Valuta> findValutaByCodeAndName(String code,String name){
		return valutaRepository.findByCodeContainingIgnoreCaseOrNameContainingIgnoreCase(code, name);
	}
	
	
}
