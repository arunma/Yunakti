package sg.edu.nus.iss.yunakti.engine.search;

import java.util.logging.Logger;

import org.eclipse.jdt.core.search.SearchRequestor;

import sg.edu.nus.iss.yunakti.model.YModel;

public abstract class ResultSetMapperBase extends SearchRequestor {
	
	private static Logger logger=Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	@Override
	public void beginReporting() {
		logger.fine("Begin reporting");
		super.beginReporting();
		
	}
	
	@Override
	public void endReporting() {
		logger.fine("End reporting");
		super.endReporting();
	}
	
}
