package sg.edu.nus.iss.yunakti.engine.util;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;

public class EngineUtil {

	public static void checkCanceled(final IProgressMonitor monitor) {
		if (monitor.isCanceled()) {
			throw new OperationCanceledException();
		}
	}
	
	
	
}
