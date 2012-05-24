package sg.edu.nus.iss.yunakti.ui.explorer;

import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

public class PackageExplorer extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		IStructuredSelection selection = (IStructuredSelection) HandlerUtil
				.getActiveMenuSelection(event);

		List<IJavaElement> elmAllPackList = null;
		Object firstElement = selection.getFirstElement();
		
		elmAllPackList = SelectionParser.listAllPackage(selection);

		if(elmAllPackList == null){
			MessageDialog.openInformation(HandlerUtil.getActiveShell(event),"Information", "Please select a Java source file");
		}
		
		List<ICompilationUnit> allUnitList = SelectionParser.getAllCompilationUnits(elmAllPackList);

		if (firstElement instanceof IPackageFragment) {
			IPackageFragment pack = (IPackageFragment) firstElement;
			List<IJavaElement> subPackageList = null;
			subPackageList = SelectionParser.getSubPackList(elmAllPackList, pack);
			List<ICompilationUnit> unitList = SelectionParser.getAllCompilationUnits(subPackageList);
			
			if(unitList != null && unitList.size() > 0){
				
				for (ICompilationUnit tmpUnit : unitList){
					
					SelectionParser.listHelperClassForUnit(tmpUnit, allUnitList);
				}
			}
			

		}else if(firstElement instanceof ICompilationUnit){
			
			//System.out.println("You have chosen a JAVA file");
			SelectionParser.listHelperClassForUnit((ICompilationUnit)firstElement, allUnitList);
			
		}else {
			System.out.println("You have NOT selected a valid element");
		}
		return null;
	}

}
