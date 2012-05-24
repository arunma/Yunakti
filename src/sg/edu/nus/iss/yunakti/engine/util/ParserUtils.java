package sg.edu.nus.iss.yunakti.engine.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.Signature;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

public class ParserUtils {
	
	/*public static void parse(final Set<ICompilationUnit> compilationUnits, final ASTRequestor requestor, final SubMonitor progressMonitor) {
        final Map<IJavaProject, Set<ICompilationUnit>> compilationUnitsByProject = getCompilationUnitsByProject(compilationUnits);
        progressMonitor.beginTask("Parsing", compilationUnitsByProject.size());
        for (final Map.Entry<IJavaProject, Set<ICompilationUnit>> entry : compilationUnitsByProject.entrySet()) {
            final ASTParser parser = createParser();
            parser.setProject(entry.getKey());
            parser.createASTs(entry.getValue().toArray(new ICompilationUnit[entry.getValue().size()]), new String[0], requestor, progressMonitor.newChild(1));
            EngineUtil.checkCanceled(progressMonitor);
        }
    }*/
	
	
	public static CompilationUnit parse(ICompilationUnit unit) {
		//SubProgressMonitor subMonitor= new SubProgressMonitor(monitor, 20); //Fix this Arun. Add progress bar while parsing. It looks cool
		ASTParser parser = createParser(unit);
		return (CompilationUnit) parser.createAST(null); 
	}
	
	private static Map<IJavaProject, Set<ICompilationUnit>> getCompilationUnitsByProject(final Set<ICompilationUnit> compilationUnits) {
        final Map<IJavaProject, Set<ICompilationUnit>> result = new HashMap<IJavaProject, Set<ICompilationUnit>>();
        for (final ICompilationUnit compilationUnit : compilationUnits) {
            if (!result.containsKey(compilationUnit.getJavaProject())) {
                result.put(compilationUnit.getJavaProject(), new HashSet<ICompilationUnit>());
            }
            result.get(compilationUnit.getJavaProject()).add(compilationUnit);
        }
        return result;
    }
	
	 private static ASTParser createParser(ICompilationUnit unit) {
	        final ASTParser parser = ASTParser.newParser(AST.JLS3);
	        parser.setResolveBindings(true);
			parser.setSource(unit);
	        parser.setKind(ASTParser.K_COMPILATION_UNIT);
			parser.setResolveBindings(true);
	        return parser;
	 }
	 
	 

	public static String getClassName(String className) {

		System.out.println("Class Name : "+className);
		if (StringUtils.isNotBlank(className)){
			String paramType = Signature.getSignatureSimpleName(className);
			int indexOfLt = paramType.indexOf("<");
			return paramType.substring(0, (indexOfLt == -1 ? (paramType.length()): indexOfLt));
		}
		
		return StringUtils.EMPTY;
	}
}
