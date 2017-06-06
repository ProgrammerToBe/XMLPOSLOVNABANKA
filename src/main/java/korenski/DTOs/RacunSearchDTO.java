package korenski.DTOs;

import java.util.Date;

public class RacunSearchDTO {
	public boolean status;
	public Date datumOtvaranjaOd;
	public Date datumOtvaranjaDo;
	public String ime;
	public String prezime;
	
	public RacunSearchDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public RacunSearchDTO(boolean status, Date datumOtvaranjaOd, Date datumOtvaranjaDo) {
		super();
		this.status = status;
		this.datumOtvaranjaOd = datumOtvaranjaOd;
		this.datumOtvaranjaDo = datumOtvaranjaDo;
	}
}
