package sg.edu.nus.iss.yunakti.ui.explorer;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jface.viewers.IStructuredSelection;

public class SelectionParser {

	//get all packages for project
	/*
	 * In JDT, all the sub package is considered as direct child of the parent package on top(direct child from source folder)
	 */
	public static List<IJavaElement> listAllPackage(IStructuredSelection selection){
			
			List<IJavaElement> allPackList = new ArrayList<IJavaElement>();
			
			IJavaElement[] elmPackList = null;
			Object firstElement = selection.getFirstElement();
	
			IJavaProject currentProject = null;
			if (firstElement instanceof IJavaElement) {
				currentProject = ((IJavaElement) firstElement).getJavaProject();
				IPackageFragmentRoot[] rootList = null;
				try {
					rootList = currentProject.getPackageFragmentRoots();
	
					for (int i = 0; i < rootList.length; i++) {
						if(rootList[i].getKind() == IPackageFragmentRoot.K_SOURCE){
							elmPackList = rootList[i].getChildren();
							for(int j = 0; j < elmPackList.length; j ++){
								allPackList.add(elmPackList[j]);
							}
						}
					}
					
				} catch (JavaModelException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					allPackList = null;
				}
			}else {
				 //MessageDialog.openInformation(HandlerUtil.getActiveShell(event),"Information", "Please select a Java source file");
				allPackList = null;
			}
			
			return allPackList;
		}
	
	//get all the sub packages from selected package
	public static List<IJavaElement> getSubPackList(List<IJavaElement> allPackList, IPackageFragment selectedPackage){
			
			List<IJavaElement> subPackList = new ArrayList<IJavaElement>();
			String packName = selectedPackage.getElementName();
			
			if(allPackList != null && allPackList.size() > 0){
				
				for(IJavaElement packElm : allPackList){
					
					if(!packName.equals(packElm.getElementName()) && packElm.getElementName().indexOf(packName) >= 0){
						
						subPackList.add(packElm);
					}
				}
				subPackList.add((IJavaElement)selectedPackage);
			}
			return subPackList;
	}
	
	//get all compilation units from the selection point(single file, package or project)
	public static List<ICompilationUnit> getAllCompilationUnits(List<IJavaElement> packageList){
		
		List<ICompilationUnit> unitList = new ArrayList<ICompilationUnit>();
		
		for(IJavaElement tmpJavaElement : packageList){
			
			//packageList
			if(tmpJavaElement.getElementType() == IJavaElement.PACKAGE_FRAGMENT){
				IPackageFragment tmpPackage = (IPackageFragment)tmpJavaElement;
				
				try {
					ICompilationUnit[] tmpUnits = tmpPackage.getCompilationUnits();
					for(int i = 0; i < tmpUnits.length; i ++){
						unitList.add(tmpUnits[i]);
					}
				} catch (JavaModelException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					unitList = null;
				}
			}
		}
		
		return unitList;
	}

	//check if the class given by fully qualified name is a helper class
	public static boolean isHelperClass(List<ICompilationUnit> allUnitList, String helperClassName) throws JavaModelException{
		
		if(allUnitList != null && allUnitList.size() > 0){
			
			for(ICompilationUnit tmpUnit : allUnitList){
				
				if(helperClassName.equals(getUnitQualifiedName(tmpUnit))){
					return true;
				}
			}
		}
		return false;
	}
	
	//get fully qualified name for Class
	public static String getUnitQualifiedName(ICompilationUnit tmpUnit) throws JavaModelException{
		
		IType[] tmpTypeList = tmpUnit.getTypes();
		return tmpTypeList[0].getFullyQualifiedName();
	}
	
	//find out helper class for TC
	public static Set<String> listHelperClassForUnit(ICompilationUnit unit, List<ICompilationUnit> allUnitList){
		
		CompilationUnit comUnit = parse(unit);
		CompilationUnitVisitor visitor = new CompilationUnitVisitor();
		comUnit.accept(visitor);
		Set<String> objectList = visitor.getInvokedObjects();
		
		
		try {
			
			String currentClassName = "";
			currentClassName = SelectionParser.getUnitQualifiedName(unit);
			
			
			System.out.println("Current Class Name ======> " + currentClassName);
			if(objectList == null || objectList.size() == 0){
				System.out.println("No helper class founder.");
			}else{
				for(String tmpClassName : objectList){
					if(!tmpClassName.equals(currentClassName) && SelectionParser.isHelperClass(allUnitList,tmpClassName)){
						System.out.println("Helper Class =======> " + tmpClassName);
					}
					else{
						objectList.remove(tmpClassName);
					}
				}
			}
			
			//visitor.removeTestClassAnnotation(comUnit);
		
		} catch (JavaModelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return objectList;
	}
	
	//parse CompilationUnit info for visitor to collect 
	public static CompilationUnit parse(ICompilationUnit unit) {
		ASTParser parser = ASTParser.newParser(AST.JLS3);
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setSource(unit);
		parser.setResolveBindings(true);
		return (CompilationUnit) parser.createAST(null); // parse
	}
	
}
