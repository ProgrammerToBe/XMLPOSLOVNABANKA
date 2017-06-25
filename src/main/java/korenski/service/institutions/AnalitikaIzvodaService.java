package korenski.service.institutions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import korenski.model.infrastruktura.AnalitikaIzvoda;
import korenski.model.infrastruktura.DnevnoStanjeRacuna;
import korenski.repository.institutions.AnalitikaIzvodaRepository;

@Service
public class AnalitikaIzvodaService {

	@Autowired
	private AnalitikaIzvodaRepository analitikaIzvodaRepository;
	
	public Page<AnalitikaIzvoda> findByPresek(int brojPreseka, DnevnoStanjeRacuna stanjeRacuna){
		
		return analitikaIzvodaRepository.findByDnevnoStanjeRacuna(new PageRequest(brojPreseka, 2), stanjeRacuna);
	}
	
}
