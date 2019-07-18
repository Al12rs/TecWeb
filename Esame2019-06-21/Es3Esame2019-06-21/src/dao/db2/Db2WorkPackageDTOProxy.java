package dao.db2;

import java.util.Set;

import dao.PartnerDTO;
import dao.WorkPackageDTO;
import dao.WorkPackagePartnerMappingDAO;

public class Db2WorkPackageDTOProxy extends WorkPackageDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//necessario per lazyloading
	private boolean alreadyLoaded;

	public Db2WorkPackageDTOProxy() {
		super();
		alreadyLoaded=false;
	}
	
	
	@Override
	public Set<PartnerDTO> getPartners()
	{
		if(isAlreadyLoaded())
			return super.getPartners();
		else
		{
			WorkPackagePartnerMappingDAO wpp = new Db2WorkPackagePartnerMappingDAO();
			isAlreadyLoaded(true);
			return wpp.getPartnersForPackage(this.getIdPackage());
		}
	}

	private boolean isAlreadyLoaded(){
		return this.alreadyLoaded;
	}

	private void isAlreadyLoaded(boolean isLoaded){
		this.alreadyLoaded=isLoaded;
	}

}
