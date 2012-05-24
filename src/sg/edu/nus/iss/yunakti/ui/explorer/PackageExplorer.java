package sg.edu.nus.iss.yunakti.ui.explorer;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.SingleMemberAnnotation;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.jdt.core.dom.rewrite.ITrackedNodePosition;
import org.eclipse.jdt.core.dom.rewrite.ListRewrite;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.text.edits.MalformedTreeException;
import org.eclipse.text.edits.TextEdit;
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
					
					SelectionParser.listHelperClassForUnit((ICompilationUnit)firstElement, allUnitList);
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
