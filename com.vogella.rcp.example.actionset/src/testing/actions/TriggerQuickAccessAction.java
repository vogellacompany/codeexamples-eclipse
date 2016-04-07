package testing.actions;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.NotEnabledException;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.handlers.IHandlerService;
/**
 * Contributed via ActionSet as workaround for https://bugs.eclipse.org/bugs/show_bug.cgi?id=213385 
 * Extension be converted to a menu contribution and this class be removed once the above bug is solved
 *  
 */
public class TriggerQuickAccessAction implements IWorkbenchWindowActionDelegate {
	private IWorkbenchWindow window;

	@Override
	public void run(IAction action) {
		IHandlerService handlerService = window.getService(IHandlerService.class);
		try {
			handlerService.executeCommand("org.eclipse.ui.window.quickAccess", null); // //$NON-NLS-1$
		} catch (ExecutionException | NotDefinedException | NotEnabledException | NotHandledException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		// intentially left emtpy
	}

	@Override
	public void dispose() {
		// intentially left emtpy
	}

	@Override
	public void init(IWorkbenchWindow window) {
		this.window = window;
	}
}