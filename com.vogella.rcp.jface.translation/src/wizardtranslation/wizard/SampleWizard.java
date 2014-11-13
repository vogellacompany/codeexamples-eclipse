package wizardtranslation.wizard;

import org.eclipse.jface.wizard.Wizard;

public class SampleWizard extends Wizard {

	public SampleWizard() {
		setWindowTitle("New Wizard");
	}

	@Override
	public void addPages() {
		addPage(new TranslationPage());
	}

	@Override
	public boolean performFinish() {
		return true;
	}
	
	@Override
	public boolean canFinish() {
		return true;
	}

}
