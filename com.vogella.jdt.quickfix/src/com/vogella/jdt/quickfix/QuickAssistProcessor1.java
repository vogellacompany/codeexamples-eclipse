package com.vogella.jdt.quickfix;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.internal.ui.javaeditor.CompilationUnitEditor;
import org.eclipse.jdt.internal.ui.text.correction.AssistContext;
import org.eclipse.jdt.internal.ui.text.java.AbstractJavaCompletionProposal;
import org.eclipse.jdt.ui.actions.AddGetterSetterAction;
import org.eclipse.jdt.ui.text.java.IInvocationContext;
import org.eclipse.jdt.ui.text.java.IJavaCompletionProposal;
import org.eclipse.jdt.ui.text.java.IProblemLocation;
import org.eclipse.jdt.ui.text.java.IQuickAssistProcessor;
import org.eclipse.jface.viewers.StyledString;

@SuppressWarnings("restriction")
public class QuickAssistProcessor1 implements IQuickAssistProcessor {

	@Override
	public boolean hasAssists(IInvocationContext context) throws CoreException {
		ASTNode coveredNode = context.getCoveredNode();

		return coveredNode.getNodeType() == ASTNode.TYPE_DECLARATION;
	}
	
	@Override
	public IJavaCompletionProposal[] getAssists(IInvocationContext context, IProblemLocation[] locations)
			throws CoreException {
		return new IJavaCompletionProposal[] { new AbstractJavaCompletionProposal() {
			public org.eclipse.jface.viewers.StyledString getStyledDisplayString() {
				ICompilationUnit compilationUnit = context.getCompilationUnit();
				return new StyledString(
						"Generate Getter and setter for " + compilationUnit.findPrimaryType().getElementName());
			}
			
			protected int getPatternMatchRule(String pattern, String string) {
				// override the match rule since we do not work with a pattern, but just want to open the "Generate Getters and Setters..." dialog
				return -1;
			};
			
			public void apply(org.eclipse.jface.text.ITextViewer viewer, char trigger, int stateMask, int offset) {
				
				if(context instanceof AssistContext) {
					AssistContext assistContext = (AssistContext) context;
					AddGetterSetterAction addGetterSetterAction = new AddGetterSetterAction((CompilationUnitEditor)assistContext.getEditor());
					
					addGetterSetterAction.run();
				}
				
			}
		} };
	}
}
