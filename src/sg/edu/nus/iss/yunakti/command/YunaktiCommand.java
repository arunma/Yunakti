package sg.edu.nus.iss.yunakti.command;

import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

import sg.edu.nus.iss.yunakti.engine.EngineCore;
import sg.edu.nus.iss.yunakti.engine.util.YModelListParser;
import sg.edu.nus.iss.yunakti.model.YModel;
import sg.edu.nus.iss.yunakti.ui.view.YunaktiGridView;
import sg.edu.nus.iss.yunakti.view.YunaktiTextView;

public class YunaktiCommand extends AbstractHandler {

	EngineCore engine = new EngineCore();

	public YunaktiCommand() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		System.out.println("Execution event fired :::::" + event);
		IStructuredSelection selection = (IStructuredSelection) HandlerUtil
				.getActiveMenuSelection(event);

		try {
			if (YModelListParser.findwhetherToPopulateModel(selection)) {
				List<YModel> models = engine.populateModel(selection);
				YunaktiTextView vw = (YunaktiTextView) HandlerUtil
						.getActiveWorkbenchWindow(event).getActivePage()
						.showView(YunaktiTextView.ID);
				vw.setListModel(models);
				YunaktiGridView vw1 = (YunaktiGridView) HandlerUtil
						.getActiveWorkbenchWindow(event).getActivePage()
						.showView(YunaktiGridView.ID);
			}
		} catch (Exception e) {
		}

		return null;
	}

}
