package it.unibo.tw.dao.db2;

import java.util.List;

import it.unibo.tw.dao.PiattoDTO;
import it.unibo.tw.dao.RistoranteDTO;
import it.unibo.tw.dao.RistorantePiattoMappingDAO;

public class Db2RistoranteDTOProxy extends RistoranteDTO {
	
	private boolean caricato = false;

	public Db2RistoranteDTOProxy() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<PiattoDTO> getPiatti() {
		if (this.caricato) {
			return super.getPiatti();
		} else {
			RistorantePiattoMappingDAO rpm = new Db2RistorantePiattoMappingDAO();
			this.caricato = true;
			super.setPiatti(rpm.getPiattiFromRestaurant(this.getId()));
			return super.getPiatti();
		}
	}
	
	

}
