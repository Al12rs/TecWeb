package dao.db2;

import java.util.Set;

import dao.ProgettoDTO;
import dao.WorkPackageDAO;
import dao.WorkPackageDTO;

public class Db2ProgettoDTOProxy extends ProgettoDTO {

	private static final long serialVersionUID = 1L;
	//necessario per lazyloading
	private boolean alreadyLoaded;

	public Db2ProgettoDTOProxy() {
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
			WorkPackageDAO wpd = new Db2WorkPackageDAO();
			isAlreadyLoaded(true);
			return wpd.readByIdProgetto(this.getIdProgetto());
		}
	}

	private boolean isAlreadyLoaded(){
		return this.alreadyLoaded;
	}

	private void isAlreadyLoaded(boolean isLoaded){
		this.alreadyLoaded=isLoaded;
	}

}
