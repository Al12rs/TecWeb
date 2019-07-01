package it.unibo.tw.dao.mysql;

import java.util.List;

import it.unibo.tw.dao.PiattoDTO;
import it.unibo.tw.dao.RistoranteDTO;
import it.unibo.tw.dao.RistorantePiattoMappingDAO;

public class MySqlRistoranteDTOProxy extends RistoranteDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public MySqlRistoranteDTOProxy() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	@Override
	public List<PiattoDTO> getPiatti()
	{
		if(isAlreadyLoaded())
			return super.getPiatti();
		else
		{
			RistorantePiattoMappingDAO rpm = new MySqlRistorantePiattoMappingDAO();
			isAlreadyLoaded(true);
			return rpm.getPiattiFromResturant(this.getId());
		}
	}

}
