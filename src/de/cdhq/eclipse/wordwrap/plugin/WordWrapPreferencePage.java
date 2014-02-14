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
        // is auto word wrap enabled?
        addField(new BooleanFieldEditor("de.cdhq.eclipse.wordwrap.autoenable.enabled",
                                        "Enable word wrap automatically for the following file extensions",
                                        getFieldEditorParent()));

        // list of file extensions for auto word wrap
        addField(new ExtensionListFieldEditor("de.cdhq.eclipse.wordwrap.autoenable.extensions",
                                              "File Extensions:",
                                              "Add",
                                              "Remove",
                                              getFieldEditorParent()));
    }

    @Override
    public void init(IWorkbench workbench) {
        setPreferenceStore(Activator.getDefault().getPreferenceStore());
        setDescription("Word Wrap Preferences");
    }
}
