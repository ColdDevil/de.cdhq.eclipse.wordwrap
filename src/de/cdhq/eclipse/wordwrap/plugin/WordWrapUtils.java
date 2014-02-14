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

public class WordWrapUtils {
    /**
     * Toggles the word wrap property of a given editor.
     * 
     * @param editor
     *            Editor in which the word wrap will be toggled
     * @author Florian Weßling <flo@cdhq.de>
     */
    public static void toggleWordWrap(IEditorPart editor) {
        if (editor != null) {
            // editor (IEditorPart) adapter returns StyledText
            Object text = editor.getAdapter(Control.class);
            if (text instanceof StyledText) {
                StyledText styledText = (StyledText) text;

                // toggle wrapping
                styledText.setWordWrap(!styledText.getWordWrap());
            }
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
        if (editor != null) {
            // editor (IEditorPart) adapter returns StyledText
            Object text = editor.getAdapter(Control.class);
            if (text instanceof StyledText) {
                StyledText styledText = (StyledText) text;

                // set wrapping
                styledText.setWordWrap(state);
            }
        }
    }
}
