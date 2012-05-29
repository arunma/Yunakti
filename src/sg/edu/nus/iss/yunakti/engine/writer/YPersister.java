package sg.edu.nus.iss.yunakti.engine.writer;

import java.util.List;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.SingleMemberAnnotation;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.text.edits.MalformedTreeException;
import org.eclipse.text.edits.TextEdit;

import sg.edu.nus.iss.yunakti.engine.util.YConstants;
import sg.edu.nus.iss.yunakti.model.YModel;

public class YPersister {
	
	/*
public String writeAnnotation(YModel yModel){
		
		JavaCore.createClassFileFrom(new I)
	
		//CompilationUnit comUnit, String CUTQualifiedName
	
		AST ast = comUnit.getAST();
		ASTRewrite rewriter = ASTRewrite.create(ast);
		SingleMemberAnnotation testClassAnnotation = ast.newSingleMemberAnnotation();
		testClassAnnotation.setTypeName(ast.newName(YConstants.TEST_CASE_ANNOTATION));
		testClassAnnotation.setValue(ast.newName(CUTQualifiedName));
		
		TypeDeclaration td = (TypeDeclaration)comUnit.types().get(0);
		rewriter.getListRewrite(td, td.getModifiersProperty()).insertAt(testClassAnnotation, 0, null);
		String updatedUnit = "";
		TextEdit edits = null;
		try {
			updatedUnit = ((ICompilationUnit)comUnit.getJavaElement()).getSource();
			System.out.println("Updated unit String Before =====> " + updatedUnit);
		} catch (JavaModelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Document doc = new Document(updatedUnit);
		edits = rewriter.rewriteAST(doc, null);
		try {
			edits.apply(doc);
			System.out.println("Updated unit String After =====> " + doc.get());
		} catch (MalformedTreeException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (BadLocationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return doc.get();
	}
	
	public String removeTestClassAnnotation(CompilationUnit comUnit){
		
		AST ast = comUnit.getAST();
		ASTRewrite rewriter = ASTRewrite.create(ast);
		TypeDeclaration td = (TypeDeclaration)comUnit.types().get(0);
		
		List<SingleMemberAnnotation> annoList = getAnnoList();
		if(annoList != null && annoList.size() > 0){
			
			for(SingleMemberAnnotation tmpAnnotation : annoList){
				if("TC".equals(tmpAnnotation.resolveAnnotationBinding().getName())){
					
					rewriter.getListRewrite(td, td.getModifiersProperty()).remove(tmpAnnotation, null);
					break;
				}
			}
		}
		
		String updatedUnit = "";
		TextEdit edits = null;
		try {
			updatedUnit = ((ICompilationUnit)comUnit.getJavaElement()).getSource();
			System.out.println("Updated unit String Before =====> " + updatedUnit);
		} catch (JavaModelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Document doc = new Document(updatedUnit);
		edits = rewriter.rewriteAST(doc, null);
		try {
			edits.apply(doc);
			System.out.println("Updated unit String After =====> " + doc.get());
		} catch (MalformedTreeException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (BadLocationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return doc.get();
	}
*/
}
