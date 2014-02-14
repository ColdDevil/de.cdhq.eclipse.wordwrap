/*******************************************************************************
 * Copyright (c) 2014 Florian Weßling.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Florian Weßling <flo@cdhq.de> - initial API and implementation
 *******************************************************************************/
package de.cdhq.eclipse.wordwrap.plugin;

import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IPartListener2;
import org.eclipse.ui.IStartup;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

/**
 * Handler for registering listeners at startup.
 * 
 * I know that using the org.eclipse.ui.startup extension point is not recommended [1] as it breaks the lazy-loading principle of plugins.
 * Unfortunately I don't have a better solution in mind yet so consider this as a workaround and tell me if you have any suggestions. ;-)
 * 
 * "The registration of the listener should have been done in the UI
 * thread since PlatformUI.getWorkbench().getActiveWorkbenchWindow()
 * returns null if it is called outside of the UI thread."[2]
 * 
 * [1] https://wiki.eclipse.org/FAQ_Can_I_activate_my_plug-in_when_the_workbench_starts%3F
 * [2] http://www.eclipse.org/forums/index.php/t/206897/
 * 
 * @author Florian Weßling <flo@cdhq.de>
 */
public class StartupHandler implements IStartup {
    @Override
    public void earlyStartup() {
        Display.getDefault().asyncExec(new Runnable() {
            public void run() {
                IWorkbenchWindow workbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
                if (workbenchWindow != null) {
                    workbenchWindow.getPartService().addPartListener(new IPartListener2() {
                        @Override
                        public void partVisible(IWorkbenchPartReference partRef) {
                        }

                        @Override
                        public void partOpened(IWorkbenchPartReference partRef) {
                            // a new part was opened: hand over part reference to word wrap handler
                            ActivateOnFileOpenHandler.handleFileOpenEvent(partRef);
                        }

                        @Override
                        public void partInputChanged(IWorkbenchPartReference partRef) {
                        }

                        @Override
                        public void partHidden(IWorkbenchPartReference partRef) {
                        }

                        @Override
                        public void partDeactivated(IWorkbenchPartReference partRef) {
                        }

                        @Override
                        public void partClosed(IWorkbenchPartReference partRef) {
                        }

                        @Override
                        public void partBroughtToTop(IWorkbenchPartReference partRef) {
                        }

                        @Override
                        public void partActivated(IWorkbenchPartReference partRef) {
                        }
                    });
                }
            }
        });
    }
}
