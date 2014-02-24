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

import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;

public class WordWrapUtils {
    /**
     * Toggles the word wrap property of a given editor.
     * 
     * @param editor
     *            Editor in which the word wrap will be toggled
     * @author Florian Weßling <flo@cdhq.de>
     */
    public static void toggleWordWrap(IEditorPart editor) {
        if (editor == null) {
            return;
        }

        // editor (IEditorPart) adapter returns StyledText
        Object text = editor.getAdapter(Control.class);
        if (text instanceof StyledText) {
            StyledText styledText = (StyledText) text;

            // toggle wrapping
            styledText.setWordWrap(!styledText.getWordWrap());
        }
    }

    /**
     * Sets the word wrap property of a given editor.
     * 
     * @param editor
     *            Editor in which the word wrap will be toggled
     * @param state
     *            The desired state of the word wrap
     * @author Florian Weßling <flo@cdhq.de>
     */
    public static void setWordWrap(IEditorPart editor, boolean state) {
        if (editor == null) {
            return;
        }

        // editor (IEditorPart) adapter returns StyledText
        Object text = editor.getAdapter(Control.class);
        if (text instanceof StyledText) {
            StyledText styledText = (StyledText) text;

            // set wrapping
            styledText.setWordWrap(state);
        }
    }

    /**
     * Sets the word wrap property of all editors of a given workbench window.
     * 
     * @param window
     *            Workbench window in which the editors will be altered
     * @param state
     *            Desired state of word wrap
     * @author Florian Weßling <flo@cdhq.de>
     */
    public static void setWordWrapInWindow(IWorkbenchWindow window, boolean state) {
        System.out.println("SET IN WINDOW to "+state);
        if (window == null) {
            return;
        }

        IWorkbenchPage page = window.getActivePage();

        // iterate all open editors
        IEditorReference[] editors = page.getEditorReferences();

        for (IEditorReference e : editors) {
            // get editor and reactivate it
            IEditorPart editor = e.getEditor(true);

            WordWrapUtils.setWordWrap(editor, state);
        }
    }
}
