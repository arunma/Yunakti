package sg.edu.nus.iss.yunakti.command;

import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

import sg.edu.nus.iss.yunakti.engine.EngineCore;
import sg.edu.nus.iss.yunakti.model.YClass;

public class ListClassesCommand extends AbstractHandler{

	
	EngineCore engine=new EngineCore();

	public ListClassesCommand() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
	
		System.out.println("Execution event fired :::::"+event);
		List<YClass> models=engine.getAllClassesInWorkspace();
		
		System.out.println("All models : "+models);
		return null;
	}
}
