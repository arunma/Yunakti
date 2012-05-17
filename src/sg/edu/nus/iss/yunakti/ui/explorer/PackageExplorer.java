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
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

public class PackageExplorer extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		/*
		 * IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		 * IProject iProject = root.getProject();
		 * 
		 * IJavaProject currentProject = JavaCore.create(iProject); try {
		 * IPackageFragmentRoot[] rootList =
		 * currentProject.getAllPackageFragmentRoots(); for(int i = 0; i <
		 * rootList.length; i ++){
		 * System.out.println("Root package name ======> " + i + " is " +
		 * rootList[i].getElementName()); } } catch (JavaModelException e1) { //
		 * TODO Auto-generated catch block e1.printStackTrace(); }
		 */

		IStructuredSelection selection = (IStructuredSelection) HandlerUtil
				.getActiveMenuSelection(event);

		List<IJavaElement> elmAllPackList = null;
		Object firstElement = selection.getFirstElement();
		
		elmAllPackList = listAllPackage(selection);

		if(elmAllPackList == null){
			MessageDialog.openInformation(HandlerUtil.getActiveShell(event),"Information", "Please select a Java source file");
		}

		if (firstElement instanceof IPackageFragment) {
			IPackageFragment pack = (IPackageFragment) firstElement;
			List<IJavaElement> subPackageList = null;
			subPackageList = getSubPackList(elmAllPackList, pack);
			
			List<ICompilationUnit> unitList = getAllCompilationUnits(subPackageList);
			if(unitList != null && unitList.size() > 0){
				
				for (ICompilationUnit tmpUnit : unitList){
					
					CompilationUnit comUnit = parse(tmpUnit);
					CompilationUnitVisitor visitor = new CompilationUnitVisitor();
					comUnit.accept(visitor);
					Set<String> objectList = visitor.getInvokedObjects();
					
					try {
						
						String currentClassName = "";
						currentClassName = getUnitQualifiedName(tmpUnit);
						
						
						System.out.println("Current Class Name ======> " + currentClassName);
						
						if(objectList == null || objectList.size() == 0){
							System.out.println("No helper class founder.");
						}else{
							
							for(String tmpClassName : objectList){
								if(!tmpClassName.equals(currentClassName) && isHelperClass(selection,tmpClassName)){
									System.out.println("Helper Class =======> " + tmpClassName);
								}
							}
						}
						
					} catch (JavaModelException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
			/*
			try {
				if (pack.hasChildren()) {
					System.out.println("Has sub packages? =====> "
							+ pack.hasSubpackages());

					System.out.println("Children found under this package =====> "
									+ pack.getElementName());
					IJavaElement[] elmList = pack.getChildren();
					System.out.println("Children list length =======> "
							+ elmList.length);
					for (int i = 0; i < elmList.length; i++) {
						System.out.println("Now printing element " + i
								+ " ====>" + elmList[i].getElementName()
								+ " / " + elmList[i].getElementType());
					}
				} else {
					System.out.println("This package has no child =====> "
							+ pack.getElementName());
				}
			
			} catch (Exception e) {
				System.out.println("Error enountered ======> " + e.getMessage());
			}
			*/

		}else if(firstElement instanceof ICompilationUnit){
			
			//System.out.println("You have chosen a JAVA file");
			ICompilationUnit unit = (ICompilationUnit) firstElement;
			CompilationUnit comUnit = parse(unit);
			CompilationUnitVisitor visitor = new CompilationUnitVisitor();
			comUnit.accept(visitor);
			Set<String> objectList = visitor.getInvokedObjects();
			
			try {
				
				String currentClassName = "";
				currentClassName = getUnitQualifiedName(unit);
				
				
				System.out.println("Current Class Name ======> " + currentClassName);
				if(objectList == null || objectList.size() == 0){
					System.out.println("No helper class founder.");
				}else{
					for(String tmpClassName : objectList){
						if(!tmpClassName.equals(currentClassName) && isHelperClass(selection,tmpClassName)){
							System.out.println("Helper Class =======> " + tmpClassName);
						}
					}
				}
			
			} catch (JavaModelException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			/*
			for (MethodDeclaration method : visitor.getMethods()) {
				//method.r
				System.out.println("Method name: " + method.getName()
						+ " Return type: " + method.getReturnType2());
			}
			IType[] typeList = null;
			try {
				typeList = unit.getAllTypes();
			} catch (JavaModelException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//System.out.println("Type list length ========> " + typeList.length);
			int typeLength = typeList.length;
			for(int len = 0; len < typeLength; len ++){
				System.out.println("The " + len + " type name is ======> " + typeList[len].getElementName());
				System.out.println("The " + len + " type TYPE is ======> " + typeList[len].getElementType());
				
			}
			*/
			
		}else {
			System.out.println("You have NOT selected a package");
		}
		return null;
	}
	
	private CompilationUnit parse(ICompilationUnit unit) {
		ASTParser parser = ASTParser.newParser(AST.JLS3);
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setSource(unit);
		parser.setResolveBindings(true);
		return (CompilationUnit) parser.createAST(null); // parse
	}
	
	private List<IJavaElement> listAllPackage(IStructuredSelection selection){
		
		List<IJavaElement> allPackList = new ArrayList<IJavaElement>();
		
		IJavaElement[] elmPackList = null;
		Object firstElement = selection.getFirstElement();

		IJavaProject currentProject = null;
		if (firstElement instanceof IJavaElement) {
			//System.out.println("Current project is JAVA Project");
			currentProject = ((IJavaElement) firstElement).getJavaProject();
			IPackageFragmentRoot[] rootList = null;
			try {
				rootList = currentProject.getPackageFragmentRoots();

				for (int i = 0; i < rootList.length; i++) {
					if(rootList[i].getKind() == IPackageFragmentRoot.K_SOURCE){
						//System.out.println("Root package name ======> " + i + " is " + rootList[i].getElementName());
						elmPackList = rootList[i].getChildren();
						for(int j = 0; j < elmPackList.length; j ++){
							//System.out.println("Children name ======> " + i + " is " + elmPackList[j].getElementName());
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
	
	private List<IJavaElement> getSubPackList(List<IJavaElement> allPackList, IPackageFragment selectedPackage){
		
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
		System.out.println("The sub package size is =========> " + subPackList.size());
		return subPackList;
	}
	
	private List<ICompilationUnit> getAllCompilationUnits(List<IJavaElement> packageList){
		
		List<ICompilationUnit> unitList = new ArrayList<ICompilationUnit>();
		
		for(IJavaElement tmpJavaElement : packageList){
			
			//packageList.
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
	
	private boolean isHelperClass(IStructuredSelection selection, String helperClassName) throws JavaModelException{
		
		List<ICompilationUnit> allUnitList = getAllCompilationUnits(listAllPackage(selection));
		if(allUnitList != null && allUnitList.size() > 0){
			
			for(ICompilationUnit tmpUnit : allUnitList){
				
				if(helperClassName.equals(getUnitQualifiedName(tmpUnit))){
					return true;
				}
			}
		}
		return false;
	}
	
	private String getUnitQualifiedName(ICompilationUnit tmpUnit) throws JavaModelException{
		
		IType[] tmpTypeList = tmpUnit.getTypes();
		return tmpTypeList[0].getFullyQualifiedName();
	}

	/*
	protected String getPersistentProperty(IResource res, QualifiedName qn) {
		try {
			return res.getPersistentProperty(qn);
		} catch (CoreException e) {
			return "";
		}
	}

	// TODO: Include this in the HTML output

	private void analyze(ICompilationUnit cu) {
		// Cool JDT allows you to analyze the code easily
		// I don't see really a use case here but I just wanted to do this here
		// as I consider this as cool and
		// what to have a place where I can store the data
		try {

			IType type = null;
			IType[] allTypes;
			allTypes = cu.getAllTypes();


			for (int t = 0; t < allTypes.length; t++) {
				if (Flags.isPublic((allTypes[t].getFlags()))) {
					type = allTypes[t];
					break;
				}
			}
			if (type != null) {
				String classname = type.getFullyQualifiedName();
				IMethod[] methods = type.getMethods();
			}

		} catch (JavaModelException e) {
			e.printStackTrace();
		}

	}

	protected void setPersistentProperty(IResource res, QualifiedName qn,
			String value) {
		try {
			res.setPersistentProperty(qn, value);
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

	private void write(String dir, ICompilationUnit cu) {
		try {
			cu.getCorrespondingResource().getName();
			String test = cu.getCorrespondingResource().getName();
			// Need
			String[] name = test.split("\\.");
			System.out.println(test);
			System.out.println(name.length);
			String htmlFile = dir + "\\" + name[0] + ".html";

			System.out.println(htmlFile);
			FileWriter output = new FileWriter(htmlFile);
			BufferedWriter writer = new BufferedWriter(output);
			writer.write("<html>");
			writer.write("<head>");
			writer.write("</head>");
			writer.write("<body>");
			writer.write("<pre>");
			writer.write(cu.getSource());
			writer.write("</pre>");
			writer.write("</body>");
			writer.write("</html>");
			writer.flush();
		} catch (JavaModelException e) {
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	*/

}
