package wizardtranslation.wizard;

import javax.inject.Inject;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

public class TranslateableWizardDialog extends WizardDialog {

	@Inject

	
	public TranslateableWizardDialog(Shell parentShell, IWizard newWizard) {
		super(parentShell, newWizard);
	}

	@Override
	protected Button createButton(Composite parent, int id, String label,
			boolean defaultButton) {
		Button createButton = super.createButton(parent, id, label, defaultButton);
		
		if(IDialogConstants.FINISH_ID == id){
			createButton.setText("Blah");
		}
		
		return createButton;
	}
}
