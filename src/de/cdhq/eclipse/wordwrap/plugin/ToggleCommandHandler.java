/*******************************************************************************
 * Copyright (c) 2013,2014 Florian Weßling.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Florian Weßling <flo@cdhq.de> - initial API and implementation
 *    Ahti Kitsik <ahti@codehoop.com> - initial API and implementation
 *    Tamás Szekeres <firefoxpdm@yahoo.co.uk> - compatibility with XML editors
 *******************************************************************************/
package de.cdhq.eclipse.wordwrap.plugin;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * This class handles the word wrap toggle command. On execution it toggles the
 * soft word wrap function for the active editor.
 * 
 * @author Florian Weßling <flo@cdhq.de>
 */
public class ToggleCommandHandler extends AbstractHandler {

    public Object execute(ExecutionEvent event) throws ExecutionException {
        /*
         * get active editor where word wrap will be toggled
         * 
         * Some of the possible editor types:
         * - Text editor: org.eclipse.ui.editors.text.TextEditor
         * - Java editor (JDT): org.eclipse.jdt.internal.ui.javaeditor.CompilationUnitEditor
         * - XML editor (WTP): org.eclipse.wst.xml.ui.internal.tabletree.XMLMultiPageEditorPart
         */
        WordWrapUtils.toggleWordWrap(HandlerUtil.getActiveEditor(event));

        return null;
    }

}
