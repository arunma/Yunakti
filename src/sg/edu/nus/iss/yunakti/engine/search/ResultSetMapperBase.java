package sg.edu.nus.iss.yunakti.engine.search;

import org.eclipse.jdt.core.search.SearchRequestor;

import sg.edu.nus.iss.yunakti.model.YModel;

public abstract class ResultSetMapperBase extends SearchRequestor {
	
	protected YModel model=null;
	
	public YModel getModel() {
		return model;
	}

	@Override
	public void beginReporting() {
		System.out.println("Begin reporting");
		super.beginReporting();
		
	}
	
	@Override
	public void endReporting() {
		System.out.println("End reporting");
		super.endReporting();
	}
	
}
