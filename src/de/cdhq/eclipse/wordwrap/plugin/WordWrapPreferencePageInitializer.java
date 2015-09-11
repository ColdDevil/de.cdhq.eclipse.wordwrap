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

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

/**
 * This class initializes the preference page with default values.
 * 
 * @author Florian Weßling <flo@cdhq.de>
 */
public class WordWrapPreferencePageInitializer extends AbstractPreferenceInitializer {
    @Override
    public void initializeDefaultPreferences() {
        IPreferenceStore store = Activator.getDefault().getPreferenceStore();

        // enable auto word wrap for all open editors on startup by default
        store.setDefault("de.cdhq.eclipse.wordwrap.autoenable.onstartup", false);

        // enable auto word wrap by default for certain file extensions
        store.setDefault("de.cdhq.eclipse.wordwrap.autoenable.forextensions", false);

        // enable auto word wrap for LaTeX, Java, text and XML files
        store.setDefault("de.cdhq.eclipse.wordwrap.autoenable.forextensions.extensions", "tex;java;txt;xml");

        // disable auto word wrap for all newly opened files by default
        store.setDefault("de.cdhq.eclipse.wordwrap.autoenable.forall", true);
    }
}
