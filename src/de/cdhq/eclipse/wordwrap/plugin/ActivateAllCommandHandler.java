/*******************************************************************************
 * Copyright (c) 2013,2014 Florian Weßling.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Florian Weßling <flo@cdhq.de> - initial API and implementation
 *    Tamás Szekeres <firefoxpdm@yahoo.co.uk> - compatibility with XML editors
 *******************************************************************************/
package de.cdhq.eclipse.wordwrap.plugin;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * This class handles the word wrap toggle-all command. On execution it toggles
 * the soft word wrap function in all active editors.
 * 
 * @author Florian Weßling <flo@cdhq.de>
 */
public class ActivateAllCommandHandler extends AbstractHandler {

    public Object execute(ExecutionEvent event) throws ExecutionException {
        // get workbench window and current page to access editors
        IWorkbenchWindow wb = HandlerUtil.getActiveWorkbenchWindow(event); // org.eclipse.ui.internal.WorkbenchWindow
        IWorkbenchPage page = wb.getActivePage();

        // iterate open editors
        IEditorReference[] editors = page.getEditorReferences();

        for (IEditorReference e : editors) {
            IEditorPart editor = e.getEditor(true);

            if (editor != null) {
                // editor (IEditorPart) adapter returns StyledText
                Object text = editor.getAdapter(Control.class);
                if (text instanceof StyledText) {
                    StyledText styledText = (StyledText) text;

                    // enable wrapping
                    styledText.setWordWrap(true);
                }
            }
        }

        return null;
    }

}
