/*
 * SWTextedit
 * Copyright (C) 2006, 2024 Philipp Seerainer
 * philipp@seerainer.com
 * https://github.com/seerainer/SWTextedit
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
 */
package io.github.seerainer.swtextedit.dialog;

import static org.eclipse.swt.events.SelectionListener.widgetSelectedAdapter;
import static org.eclipse.swt.events.ShellListener.shellActivatedAdapter;
import static org.eclipse.swt.events.ShellListener.shellClosedAdapter;

import java.util.HashSet;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.ShellListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import io.github.seerainer.swtextedit.config.ConfigData;
import io.github.seerainer.swtextedit.config.Icons;
import io.github.seerainer.swtextedit.layout.Grid;
import io.github.seerainer.swtextedit.util.LangUtil;
import io.github.seerainer.swtextedit.widgets.ButtonWidget;
import io.github.seerainer.swtextedit.widgets.GroupWidget;
import io.github.seerainer.swtextedit.widgets.LabelWidget;
import io.github.seerainer.swtextedit.widgets.ShellWidget;
import io.github.seerainer.swtextedit.widgets.TextWidget;

/**
 * Find / Replace dialog.
 *
 * @author philipp@seerainer.com
 */
public final class FindReplace {

	/** Instances of the Buttons. */
	private Button forward;

	/**
	 * Instances of the Buttons.
	 */
	private Button backward;

	/**
	 * Instances of the Buttons.
	 */
	private Button sensitiveButton;

	/**
	 * Instances of the Buttons.
	 */
	private Button findButton;

	/**
	 * Instances of the Buttons.
	 */
	private Button replacefindButton;

	/**
	 * Instances of the Buttons.
	 */
	private Button replaceButton;

	/**
	 * Instances of the Buttons.
	 */
	private Button replaceallButton;

	/** Instance of GuiConfigData for all configuration data. */
	private ConfigData configData;

	/** HashSet for the language control. */
	private final HashSet<Control> widgets = new HashSet<>();

	/** Shows info of the dialog box. */
	private Label info;

	/** Instance of the menu to get the menu item for this class. */
	private Menu edit;

	/** Instance of Shell. */
	private Shell dialog;

	/** Instance of StyledText. */
	private StyledText text;

	/** Instances of the text fields. */
	private Text searchText;

	/**
	 * Instances of the text fields.
	 */
	private Text replaceText;

	/** Modify listener for the search field. */
	private final ModifyListener searchModify = e -> enableButtons();

	/** Modify listener for the replace field. */
	private final ModifyListener replaceModify = e -> enableButtons();

	/** Listener for the sensitive check button. */
	private final SelectionListener sensitive = widgetSelectedAdapter(e -> enableButtons());

	/** Listener for the find button. */
	private final SelectionListener find = widgetSelectedAdapter(e -> {
		if (findEntry()) {
			info.setText(""); //$NON-NLS-1$
		} else {
			info.setText(configData.getLangRes().getString("find_notfind")); //$NON-NLS-1$
		}
		enableButtons();
		text.setFocus();
		dialog.setFocus();
	});

	/** Listener for the replace/find button. */
	private final SelectionListener replacefind = widgetSelectedAdapter(e -> {
		replace();
		if (findEntry()) {
			info.setText(""); //$NON-NLS-1$
		} else {
			info.setText(configData.getLangRes().getString("find_notfind")); //$NON-NLS-1$
		}
		enableButtons();
		text.setFocus();
		dialog.setFocus();
	});

	/** Listener for the replace button. */
	private final SelectionListener replace = widgetSelectedAdapter(e -> {
		replace();
		enableButtons();
		text.setFocus();
		dialog.setFocus();
		info.setText(""); //$NON-NLS-1$
	});

	/** Listener for the replace all button. */
	private final SelectionListener replaceall = widgetSelectedAdapter(e -> {
		int counter;
		if (forward.getSelection()) {
			text.setCaretOffset(-1);
		} else {
			text.setCaretOffset(text.getCharCount());
		}
		for (counter = 0; findEntry(); counter++) {
			replace();
		}
		enableButtons();
		text.setFocus();
		dialog.setFocus();
		info.setText(counter + " " + configData.getLangRes().getString("find_replaced")); //$NON-NLS-1$ //$NON-NLS-2$
	});

	/** Listener for the close event. */
	private final SelectionListener close = widgetSelectedAdapter(e -> dialog.close());

	/** Listener if the shell gets the focus. */
	private final ShellListener shellFocus = shellActivatedAdapter(e -> enableButtons());

	/** Listener for closing the dialog. */
	private final ShellListener shellExit = shellClosedAdapter(e -> {
		if (e.doit) {
			edit.getItem(11).setEnabled(true);
		}
	});

	/**
	 * Public constructor.
	 *
	 * @param parent     The parent of the dialog.
	 * @param configData The configuration values of the GUI.
	 * @param text       The text widget for the text file.
	 * @param edit       The edit menu of the drop down menu.
	 */
	public FindReplace(final Shell parent, final ConfigData configData, final StyledText text, final Menu edit) {
		this.configData = configData;
		this.text = text;
		this.edit = edit;

		shell(parent);
		search();
		options();
		buttons();

		dialog.open();

		LangUtil.setLang(widgets, configData);
	}

	/**
	 * Buttons for find, replace/find, replace and replace all.
	 */
	private void buttons() {
		findButton = ButtonWidget.newButton(dialog, SWT.PUSH, "find_find", Grid.newGridData(2, 1), find); //$NON-NLS-1$
		widgets.add(findButton);
		findButton.setEnabled(false);
		findButton.setLocation(dialog.getBounds().width / 4, dialog.getBounds().height - 100);
		dialog.setDefaultButton(findButton);

		replacefindButton = ButtonWidget.newButton(dialog, SWT.PUSH, "find_replacefind", Grid.newGridData(2, 1), //$NON-NLS-1$
				replacefind);
		widgets.add(replacefindButton);
		replacefindButton.setEnabled(false);

		replaceButton = ButtonWidget.newButton(dialog, SWT.PUSH, "find_replace", Grid.newGridData(2, 1), replace); //$NON-NLS-1$
		widgets.add(replaceButton);
		replaceButton.setEnabled(false);

		replaceallButton = ButtonWidget.newButton(dialog, SWT.PUSH, "find_replaceall", Grid.newGridData(2, 1), //$NON-NLS-1$
				replaceall);
		widgets.add(replaceallButton);
		replaceallButton.setEnabled(false);

		info = LabelWidget.newLabel(dialog, new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1), null);

		widgets.add(ButtonWidget.newButton(dialog, SWT.PUSH, "button_close", Grid.newGridData(2, 1), close)); //$NON-NLS-1$
	}

	/**
	 * Enables the buttons.
	 */
	private void enableButtons() {
		if (text.getText().length() > 0 && searchText.getCharCount() > 0
				&& text.getText().length() >= searchText.getCharCount()) {
			findButton.setEnabled(true);
			replaceallButton.setEnabled(true);
		} else {
			findButton.setEnabled(false);
			replaceallButton.setEnabled(false);
		}
		if (searchText.getCharCount() > 0 && text.getSelectionCount() > 0
				&& searchText.getText().equalsIgnoreCase(text.getSelectionText()) && !sensitiveButton.getSelection()) {
			replaceButton.setEnabled(true);
			replacefindButton.setEnabled(true);
		} else if (searchText.getCharCount() > 0 && text.getSelectionCount() > 0
				&& searchText.getText().equals(text.getSelectionText()) && sensitiveButton.getSelection()) {
			replaceButton.setEnabled(true);
			replacefindButton.setEnabled(true);
		} else {
			replaceButton.setEnabled(false);
			replacefindButton.setEnabled(false);
		}
	}

	/**
	 * Function for searching.
	 *
	 * @return Return boolean state.
	 */
	private boolean findEntry() {
		var searchString = searchText.getText();
		var textString = text.getText();
		final var offset = text.getCaretOffset();
		var start = -1;

		if (!sensitiveButton.getSelection()) {
			searchString = searchString.toLowerCase();
			textString = textString.toLowerCase();
		}

		if (forward.getSelection()) {
			start = textString.indexOf(searchString, offset);
		} else if (text.getSelectionRange().y > searchString.length()) {
			start = textString.lastIndexOf(searchString, offset - 1);
		} else {
			start = textString.lastIndexOf(searchString, offset - text.getSelectionRange().y - 1);
		}

		if (start > -1) {
			text.setSelection(start, start + searchString.length());
			return true;
		}

		return false;
	}

	/**
	 * Moves the window to the top of the drawing order.
	 */
	public void forceActive() {
		dialog.forceActive();
	}

	/**
	 * @return Returns if the dialog is closed.
	 */
	public boolean isWidgetDisposed() {
		return dialog.isDisposed();
	}

	/**
	 * Options for direction, scope and case sensitive.
	 */
	private void options() {
		final var direction = GroupWidget.newGroup(dialog, SWT.SHADOW_IN, Grid.newGridData(2, 1),
				Grid.newGridLayout(5, 5, 5, 5, 1, false), "find_direction"); //$NON-NLS-1$
		widgets.add(direction);

		forward = ButtonWidget.newButton(direction, SWT.RADIO, "find_direction_forward", Grid.newGridData(), null); //$NON-NLS-1$
		widgets.add(forward);
		forward.setSelection(true);
		backward = ButtonWidget.newButton(direction, SWT.RADIO, "find_direction_backward", Grid.newGridData(), null); //$NON-NLS-1$
		widgets.add(backward);

		final var options = GroupWidget.newGroup(dialog, SWT.SHADOW_IN, Grid.newGridData(2, 1),
				Grid.newGridLayout(5, 5, 5, 5, 1, false), "find_options"); //$NON-NLS-1$
		widgets.add(options);

		sensitiveButton = ButtonWidget.newButton(options, SWT.CHECK, "find_options_sensitive", Grid.newGridData(), //$NON-NLS-1$
				sensitive);
		widgets.add(sensitiveButton);

		if (configData.isDarkMode()) {
			direction.setForeground(dialog.getForeground());
			options.setForeground(dialog.getForeground());
		}
	}

	/**
	 * Function for replacing.
	 */
	private void replace() {
		final var start = text.getSelectionRange().x;
		text.replaceTextRange(start, text.getSelectionCount(), replaceText.getText());
		text.setSelection(start, start + replaceText.getText().length());
	}

	/**
	 * The two text fields find and replace.
	 */
	private void search() {
		final var gridData = new GridData(SWT.FILL, SWT.CENTER, true, false);

		widgets.add(LabelWidget.newLabel(dialog, gridData, "find_find_text")); //$NON-NLS-1$

		searchText = TextWidget.newText(dialog, SWT.SINGLE | SWT.BORDER, Grid.newGridData(3, 1), true, true);
		searchText.addModifyListener(searchModify);
		searchText.setFocus();

		widgets.add(LabelWidget.newLabel(dialog, gridData, "find_replace_text")); //$NON-NLS-1$

		replaceText = TextWidget.newText(dialog, SWT.SINGLE | SWT.BORDER, Grid.newGridData(3, 1), false, true);
		replaceText.addModifyListener(replaceModify);
	}

	/**
	 * Creates a new Shell for the find / replace dialog.
	 *
	 * @param parent The parent of the dialog.
	 */
	private void shell(final Shell parent) {
		edit.getItem(11).setEnabled(false);

		dialog = ShellWidget.newShell(parent, SWT.TOOL | SWT.DIALOG_TRIM, "find", Icons.text, 500, 270, //$NON-NLS-1$
				Grid.newGridLayout(5, 5, 5, 5, 4, true), true, false);
		widgets.add(dialog);

		dialog.addShellListener(shellFocus);
		dialog.addShellListener(shellExit);
		dialog.setBackground(parent.getBackground());
		dialog.setForeground(parent.getForeground());
		dialog.setBackgroundMode(SWT.INHERIT_FORCE);
	}
}
