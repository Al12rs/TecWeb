package it.unibo.tw.dao.db2;

import java.util.List;

public class Db2TypeDTOProxy extends #TypeDTO {

	//necessario per lazyloading
	private boolean alreadyLoaded;

	public Db2TypeDTOProxy() {
		super();
		alreadyLoaded=false;
	}
	
	
	@Override
	public List<#TypeDTO> #getOggetti()
	{
		if(isAlreadyLoaded())
			return super.#getOggetti();
		else
		{
			#TypeMappingDAO rpm = new Db2#TypeMappingDAO();
			isAlreadyLoaded(true);
			return rpm.#getOggettiFromField(this.#getField());
		}
	}

	private boolean isAlreadyLoaded(){
		return this.alreadyLoaded;
	}

	private void isAlreadyLoaded(boolean isLoaded){
		this.alreadyLoaded=isLoaded;
	}

}
