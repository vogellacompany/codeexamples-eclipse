package com.vogella.rcp.editor.example.editor.text.config;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.CompletionProposal;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.text.contentassist.IContextInformationValidator;

public class TaskCompletionProcessor implements IContentAssistProcessor {

	private String[] proposals = new String[] { "ID:", "Summary:", "Description:", "Done:", "Duedate:", "Dependent:" };

	@Override
	public ICompletionProposal[] computeCompletionProposals(ITextViewer viewer, int offset) {

		IDocument document = viewer.getDocument();

		try {
			int lineOfOffset = document.getLineOfOffset(offset);
			int lineOffset = document.getLineOffset(lineOfOffset);

			// do not show any content assist in case the offset is not at the
			// beginning of a line
			if (offset != lineOffset) {
				return new ICompletionProposal[0];
			}
		} catch (BadLocationException e) {
			// ignore here and just continue
		}

		List<ICompletionProposal> completionProposals = new ArrayList<ICompletionProposal>();

		for (String c : proposals) {
			// Only add proposal if it is not already present
			if (!(viewer.getDocument().get().contains(c))) {
				completionProposals.add(new CompletionProposal(c, offset, 0, c.length()));
			}
		}

		return completionProposals.toArray(new ICompletionProposal[completionProposals.size()]);
	}

	@Override
	public IContextInformation[] computeContextInformation(ITextViewer viewer, int offset) {
		return null;
	}

	@Override
	public char[] getCompletionProposalAutoActivationCharacters() {
		return null;
	}

	@Override
	public char[] getContextInformationAutoActivationCharacters() {
		return null;
	}

	@Override
	public String getErrorMessage() {
		return null;
	}

	@Override
	public IContextInformationValidator getContextInformationValidator() {
		return null;
	}

}
