package sg.edu.nus.iss.yunakti.command;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

import sg.edu.nus.iss.yunakti.engine.EngineCore;
import sg.edu.nus.iss.yunakti.model.YModel;

public class YunaktiCommand extends AbstractHandler{
	
	EngineCore engine=new EngineCore();

	public YunaktiCommand() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
	
		System.out.println("Execution event fired :::::"+event);
		IStructuredSelection selection=(IStructuredSelection) HandlerUtil.getActiveMenuSelection(event);
		
		YModel model=engine.populateModel(selection);
		
		return null;
	}

	

}
