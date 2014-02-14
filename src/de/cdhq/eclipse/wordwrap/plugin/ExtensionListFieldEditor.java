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

import java.util.Arrays;

import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Text;

/**
 * A field editor for displaying, editing and storing the list of extensions.
 * 
 * The code is inspired by the Eclipse Corner article "Simplifying Preference Pages with Field Editors" by Ryan Cooper [1].
 * 
 * [1] http://www.eclipse.org/articles/Article-Field-Editors/field_editors.html
 * 
 * @author Florian Weßling <flo@cdhq.de>
 */
public class ExtensionListFieldEditor extends FieldEditor {
    // Height of list in dialog units (see org.eclipse.jface.preference.FieldEditor.convertVerticalDLUsToPixels())
    private static final int    LIST_HEIGHT_IN_DLUS = 80;

    // The string used to seperate list items in a single String representation
    private static final String SEPARATOR           = ";";

    // Top-level control for the field editor.
    private Composite           top;

    // List of extensions
    private List                list;

    // Text field for adding new extensions
    private Text                textField;

    // Button for adding the contents of the text field to the list
    private Button              add;

    // Button for removing the currently-selected list item
    private Button              remove;

    public ExtensionListFieldEditor(String name, String labelText, String addButtonText, String removeButtonText, Composite parent) {
        super(name, labelText, parent);
        add.setText(addButtonText);
        remove.setText(removeButtonText);
    }

    /**
     * @see org.eclipse.jface.preference.FieldEditor#doFillIntoGrid (Composite, int)
     */
    protected void doFillIntoGrid(Composite parent, int numColumns) {
        // Top-level control for the field editor
        top = parent;
        GridData gd = new GridData(GridData.FILL_HORIZONTAL);
        gd.horizontalSpan = numColumns;
        top.setLayoutData(gd);

        // List of extensions
        list = new List(top, SWT.BORDER | SWT.V_SCROLL);
        GridData listData = new GridData(GridData.FILL_HORIZONTAL);
        listData.heightHint = convertVerticalDLUsToPixels(list, LIST_HEIGHT_IN_DLUS);
        listData.horizontalSpan = numColumns;
        list.setLayoutData(listData);
        list.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                selectionChanged();
            }
        });

        // Composite for the buttons and the text field
        Composite addRemoveGroup = new Composite(top, SWT.NONE);
        GridData addRemoveData = new GridData(GridData.FILL_HORIZONTAL);
        addRemoveData.horizontalSpan = numColumns;
        addRemoveGroup.setLayoutData(addRemoveData);
        GridLayout addRemoveLayout = new GridLayout();
        addRemoveLayout.numColumns = numColumns;
        addRemoveLayout.marginHeight = 0;
        addRemoveLayout.marginWidth = 0;
        addRemoveGroup.setLayout(addRemoveLayout);

        // Composite only for the buttons
        Composite buttonGroup = new Composite(addRemoveGroup, SWT.NONE);
        buttonGroup.setLayoutData(new GridData());
        GridLayout buttonLayout = new GridLayout();
        buttonLayout.marginHeight = 0;
        buttonLayout.marginWidth = 0;
        buttonGroup.setLayout(buttonLayout);

        // Add button
        add = new Button(buttonGroup, SWT.NONE);
        add.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                addStringToList();
            }
        });
        add.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        // Remove button
        remove = new Button(buttonGroup, SWT.NONE);
        remove.setEnabled(false);
        remove.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                list.remove(list.getSelectionIndex());
                selectionChanged();
            }
        });
        remove.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        // Text field
        textField = new Text(addRemoveGroup, SWT.BORDER);
        GridData textData = new GridData(GridData.FILL_HORIZONTAL);
        textData.horizontalSpan = numColumns - 1;
        textData.verticalAlignment = GridData.BEGINNING;
        textField.setLayoutData(textData);
    }

    /**
     * Necessary for layout purposes
     * 
     * @see org.eclipse.jface.preference.FieldEditor#adjustForNumColumns(int)
     */
    protected void adjustForNumColumns(int numColumns) {
        ((GridData) top.getLayoutData()).horizontalSpan = numColumns;
    }

    /**
     * Necessary for layout purposes
     * 
     * @see org.eclipse.jface.preference.FieldEditor#getNumberOfControls()
     */
    public int getNumberOfControls() {
        // The button composite and the text field.
        return 2;
    }

    /**
     * Loads list of extensions from preference store
     * 
     * @see org.eclipse.jface.preference.FieldEditor#doLoad()
     */
    protected void doLoad() {
        String items = getPreferenceStore().getString(getPreferenceName());
        setList(items);
    }

    /**
     * Loads list of default extensions from preference store
     * 
     * @see org.eclipse.jface.preference.FieldEditor#doLoadDefault()
     */
    protected void doLoadDefault() {
        String items = getPreferenceStore().getDefaultString(getPreferenceName());
        setList(items);
    }

    /**
     * Saves list of extensions in preference store
     * 
     * @see org.eclipse.jface.preference.FieldEditor#doStore()
     */
    protected void doStore() {
        String s = createListString(list.getItems());

        if (s != null) {
            getPreferenceStore().setValue(getPreferenceName(), s);
        }
    }

    /**
     * Parses the string into seperate list items and adds them to the list
     * 
     * @param items
     *            String of concatenated items
     */
    private void setList(String items) {
        list.setItems(parseString(items));
    }

    /**
     * Adds the string in the text field to the list
     */
    private void addStringToList() {
        String str = textField.getText();

        // string must not be empty or contain the separator string or contain an extension already listed
        if (str != null && str.length() > 0 && !str.contains(SEPARATOR) && !Arrays.asList(list.getItems()).contains(str)) {
            list.add(str);
            textField.setText("");
        }
    }

    /**
     * Creates the single String representation of the list that is stored in the preference store
     */
    private String createListString(String[] items) {
        StringBuffer path = new StringBuffer("");

        for (int i = 0; i < items.length; i++) {
            path.append(items[i]);
            path.append(SEPARATOR);
        }

        return path.toString();
    }

    /**
     * Parses the single String representation of the list into an array of list items
     */
    public static String[] parseString(String stringList) {
        return stringList.split(SEPARATOR);
    }

    /**
     * Sets the enablement of the remove button depending on the selection in the list.
     */
    private void selectionChanged() {
        int index = list.getSelectionIndex();
        remove.setEnabled(index >= 0);
    }
}
