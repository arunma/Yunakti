package sg.edu.nus.iss.yunakti.engine.util;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import sg.edu.nus.iss.yunakti.model.YClass;
import sg.edu.nus.iss.yunakti.model.YTYPE;

public class WorkspaceUtils {
	
	private WorkspaceUtils(){}
	
	private static WorkspaceUtils instance = null;
	private List<YClass> allClasses=new ArrayList<YClass>();
	   
	public static WorkspaceUtils getInstance() {
	      if(instance == null) {
	         instance = new WorkspaceUtils();
	      }
	      return instance;
	}
	   
	
	public void gatherAllClassesInWorkspace() throws JavaModelException, CoreException{
		
		
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot(); 
		IProject[] projectArray = root.getProjects();
		
		if (projectArray!=null){
			
			for (IProject eachProject : projectArray) {
				
				//We have no business looking at non-java projects
				if (eachProject.isNatureEnabled(YConstants.JAVA_NATURE)) {
					IJavaProject javaProject = JavaCore.create(eachProject); 
					getAllClassesInProject(javaProject);
					
				}
				
			}
			
		}


	}

	public List<YClass> getAllClasses() {
		return allClasses;
	}


	private void getAllClassesInProject(IJavaProject javaProject) throws JavaModelException {
		
		
		IPackageFragment[] packageFragments = javaProject.getPackageFragments();
		for (IPackageFragment eachPackageFragment : packageFragments) {
		

			if (eachPackageFragment.getKind() == IPackageFragmentRoot.K_SOURCE) {
				getYClassesFromCompilationUnits(eachPackageFragment);
			}
			
		}
		
	}

	private void getYClassesFromCompilationUnits(IPackageFragment eachPackageFragment) throws JavaModelException {

		
		ICompilationUnit[] compilationUnits = eachPackageFragment.getCompilationUnits();
		YClassVisitor visitor=null;
		YClass yClass=null;
		CompilationUnit compilationUnit = null;
		
		
		for (ICompilationUnit eachCompilationUnit : compilationUnits) {
			
				yClass=new YClass();
				visitor=new YClassVisitor(yClass);
				
				compilationUnit = ParserUtils.parse(eachCompilationUnit);
				compilationUnit.accept(visitor);
				
		}
		
		
	}
	
	

 class YClassVisitor extends ASTVisitor{

	private YClass yClass;
	
	public YClassVisitor(YClass yClass) {
		this.yClass=yClass;
	}
	
	@Override
	public boolean visit(TypeDeclaration node) {
		
		System.out.println("Type declaration node : "+node.resolveBinding().getQualifiedName());
		yClass=new YClass(node.resolveBinding().getQualifiedName());
		yClass.setyClassType(YTYPE.TEST_HELPER);
		allClasses.add(yClass);
		return super.visit(node);
		
	}

	public YClass getyClass() {
		return yClass;
	}
	
	@Override
	public boolean visit(SimpleName simpleName){
		
		System.out.println("Simple name : "+simpleName);
		return super.visit(simpleName); 
	}
}
	
}


