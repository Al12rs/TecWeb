package dao.db2;

import java.util.Set;

import dao.PartnerDTO;
import dao.WorkPackageDTO;
import dao.WorkPackagePartnerMappingDAO;

public class Db2PartnerDTOProxy extends PartnerDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//necessario per lazyloading
	private boolean alreadyLoaded;

	public Db2PartnerDTOProxy() {
		super();
		alreadyLoaded=false;
	}
	
	
	@Override
	public Set<WorkPackageDTO> getPackages()
	{
		if(isAlreadyLoaded())
			return super.getPackages();
		else
		{
			WorkPackagePartnerMappingDAO wpp = new Db2WorkPackagePartnerMappingDAO();
			isAlreadyLoaded(true);
			return wpp.getPackagesForPartner(this.getIdPartner());
		}
	}

	private boolean isAlreadyLoaded(){
		return this.alreadyLoaded;
	}

	private void isAlreadyLoaded(boolean isLoaded){
		this.alreadyLoaded=isLoaded;
	}

}
