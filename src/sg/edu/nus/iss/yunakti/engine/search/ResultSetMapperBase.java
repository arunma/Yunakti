package sg.edu.nus.iss.yunakti.engine.search;

import org.eclipse.jdt.core.search.SearchRequestor;

import sg.edu.nus.iss.yunakti.model.YModel;

public abstract class ResultSetMapperBase extends SearchRequestor {
	
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
