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

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class WordWrapPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {
    public WordWrapPreferencePage() {
        super(FieldEditorPreferencePage.GRID);
    }

    @Override
    protected void createFieldEditors() {
        // enable auto wrap on startup?
        addField(new BooleanFieldEditor("de.cdhq.eclipse.wordwrap.autoenable.onstartup",
                                        "Automatically enable word wrap for all open editors on startup",
                                        getFieldEditorParent()));

        // is auto word wrap enabled for certain file extensions?
        addField(new BooleanFieldEditor("de.cdhq.eclipse.wordwrap.autoenable.forextensions",
                                        "Automatically enable word wrap for the following file extensions:",
                                        getFieldEditorParent()));

        // list of file extensions for auto word wrap
        addField(new ExtensionListFieldEditor("de.cdhq.eclipse.wordwrap.autoenable.forextensions.extensions",
                                              "File Extensions:",
                                              "Add",
                                              "Remove",
                                              getFieldEditorParent()));

        // auto wrap for all files?
        addField(new BooleanFieldEditor("de.cdhq.eclipse.wordwrap.autoenable.forall",
                                        "Automatically enable word wrap for all newly opened files",
                                        getFieldEditorParent()));
    }

    @Override
    public void init(IWorkbench workbench) {
        setPreferenceStore(Activator.getDefault().getPreferenceStore());
        setDescription("Word Wrap Preferences");
    }
}
