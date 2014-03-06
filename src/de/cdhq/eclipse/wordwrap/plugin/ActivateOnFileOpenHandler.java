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

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.ide.ResourceUtil;

public class ActivateOnFileOpenHandler {
    /**
     * Handles the opening of an editor and tries to enable wrapping if auto word wrap is enabled.
     * 
     * @param partRef
     *            Reference to the workbench part that is opened (usually an IEditorPart)
     * @author Florian Weßling <flo@cdhq.de>
     */
    public static void handleFileOpenEvent(IWorkbenchPartReference partRef) {
        // get preference store of this plugin
        IPreferenceStore preferenceStore = Activator.getDefault().getPreferenceStore();

        // check if auto word wrap is enabled
        if (!preferenceStore.getBoolean("de.cdhq.eclipse.wordwrap.autoenable.enabled")) {
            return;
        }

        IWorkbenchPart part = partRef.getPart(true);
        if (part instanceof IEditorPart) {
            /*
             * Some of the possible editor types and their input:
             * 
             * - Text editor:
             *   part = org.eclipse.ui.editors.text.TextEditor
             *   editorInput = org.eclipse.ui.part.FileEditorInput(/ProjectName/Example.txt)
             *   editorInput = org.eclipse.ui.internal.editors.text.NonExistingFileEditorInput
             *   
             * - Java editor (JDT):
             *   part = org.eclipse.jdt.internal.ui.javaeditor.CompilationUnitEditor
             *   editorInput = org.eclipse.ui.part.FileEditorInput(/ProjectName/src/Example.java)
             *   
             * - XML editor (WTP):
             *   part = org.eclipse.wst.xml.ui.internal.tabletree.XMLMultiPageEditorPart
             *   editorInput = org.eclipse.ui.part.FileEditorInput(/ProjectName/Example.xml)
             * 
             */
            IEditorPart editorPart = (IEditorPart) part;

            // immediately activate word wrap when it is enabled for all files
            if (preferenceStore.getBoolean("de.cdhq.eclipse.wordwrap.autoenable.forall")) {
                WordWrapUtils.setWordWrap(editorPart, true);
                return;
            }

            // get file name of editor's input
            IEditorInput input = editorPart.getEditorInput();
            IFile file = ResourceUtil.getFile(input);

            // file can be null if an empty file is created
            if (file != null) {
                String fileExtension = file.getFileExtension();

                // stop if file extension is empty (i.e. null)
                if (fileExtension == null) {
                    return;
                }

                // get extensions from preference store
                String[] extensions = ExtensionListFieldEditor.parseString(preferenceStore.getString("de.cdhq.eclipse.wordwrap.autoenable.extensions"));

                // check if extension is covered by extensioned selected in preferences
                for (String ext : extensions) {
                    if (ext.equals(fileExtension)) {
                        // extension found: activate wrapping
                        WordWrapUtils.setWordWrap(editorPart, true);

                        // no further action necessary
                        return;
                    }
                }
            }
        }
    }
}
