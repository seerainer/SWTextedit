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

import java.util.HashSet;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import io.github.seerainer.swtextedit.config.ConfigData;
import io.github.seerainer.swtextedit.config.Icons;
import io.github.seerainer.swtextedit.layout.Grid;
import io.github.seerainer.swtextedit.util.LangUtil;
import io.github.seerainer.swtextedit.widgets.ButtonWidget;
import io.github.seerainer.swtextedit.widgets.GroupWidget;
import io.github.seerainer.swtextedit.widgets.LabelWidget;
import io.github.seerainer.swtextedit.widgets.LinkWidget;
import io.github.seerainer.swtextedit.widgets.ShellWidget;
import io.github.seerainer.swtextedit.widgets.TextWidget;

/**
 * About dialog for information around the application.
 *
 * @author philipp@seerainer.com
 */
public final class About {

	/** Instances of the Groups. */
	private Group info;

	/**
	 * Instances of the Groups.
	 */
	private Group license;

	/** Instance of GUIConfigData for all configuration variables. */
	private ConfigData configData;

	/** HashSet for the language control. */
	private final HashSet<Control> widgets = new HashSet<>();

	/** Instances for the version. */
	private Label version;

	/** Instance of Shell. */
	private Shell dialog;

	/** Listener for the mail. */
	private final SelectionListener piseeMail = widgetSelectedAdapter(
			e -> Program.launch(configData.getLangRes().getString("mail"))); //$NON-NLS-1$

	/** Listener for the website. */
	private final SelectionListener piseeHP = widgetSelectedAdapter(
			e -> Program.launch(configData.getLangRes().getString("web"))); //$NON-NLS-1$

	/** Listener for the eclipse license. */
	private final SelectionListener epl = widgetSelectedAdapter(
			e -> Program.launch(configData.getLangRes().getString("epl"))); //$NON-NLS-1$

	/** Listener for the GPL license. */
	private final SelectionListener gpl = widgetSelectedAdapter(
			e -> Program.launch(configData.getLangRes().getString("gpl"))); //$NON-NLS-1$

	/** Listener for the OK button. */
	private final SelectionListener close = widgetSelectedAdapter(e -> dialog.close());

	/**
	 * Public constructor.
	 *
	 * @param parent     The parent of the dialog.
	 * @param configData The configuration values of the GUI.
	 */
	public About(final Shell parent, final ConfigData configData) {
		this.configData = configData;

		shell(parent);
		groups();
		okButton();

		LangUtil.setLang(widgets, configData);

		version.setText(version.getText() + configData.getVersion());

		dialog.open();
	}

	/**
	 * Two groups for program information and the license.
	 */
	private void groups() {
		info = GroupWidget.newGroup(dialog, SWT.SHADOW_IN, Grid.newGridData(),
				Grid.newGridLayout(10, 10, 1, 5, 1, false), "about_info"); //$NON-NLS-1$
		widgets.add(info);

		license = GroupWidget.newGroup(dialog, SWT.SHADOW_OUT, Grid.newGridData(true, true),
				Grid.newGridLayout(10, 10, 1, 5, 1, false), "about_license"); //$NON-NLS-1$
		widgets.add(license);

		if (configData.isDarkMode()) {
			final var lght = new Color(0xD0, 0xD0, 0xD0);
			info.setForeground(lght);
			license.setForeground(lght);
		}

		infoGroup();
		licenseGroup();
	}

	/**
	 * Information and weblinks.
	 */
	private void infoGroup() {
		final var name = LabelWidget.newLabel(info, Grid.newGridData(), "about_info_name"); //$NON-NLS-1$
		widgets.add(name);
		version = LabelWidget.newLabel(info, Grid.newGridData(), "about_info_version"); //$NON-NLS-1$
		widgets.add(version);

		if (configData.isDarkMode()) {
			final var lght = new Color(0xD0, 0xD0, 0xD0);
			name.setForeground(lght);
			version.setForeground(lght);
		}

		LabelWidget.hLine(info);

		widgets.add(LinkWidget.newLink(info, SWT.NONE, Grid.newGridData(), "about_info_author", piseeMail)); //$NON-NLS-1$
		widgets.add(LinkWidget.newLink(info, SWT.NONE, Grid.newGridData(), "about_info_web", piseeHP)); //$NON-NLS-1$
	}

	/**
	 * Info about the license.
	 */
	private void licenseGroup() {
		final var swtlic = LabelWidget.newLabel(license, Grid.newGridData(), "about_license_swt"); //$NON-NLS-1$
		widgets.add(swtlic);
		widgets.add(LinkWidget.newLink(license, SWT.NONE, Grid.newGridData(), "about_license_epl", epl)); //$NON-NLS-1$

		LabelWidget.hLine(license);

		widgets.add(LinkWidget.newLink(license, SWT.NONE, Grid.newGridData(), "about_license_gpl", gpl)); //$NON-NLS-1$

		final var text = TextWidget.newText(license, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL,
				Grid.newGridData(true, true), false, false);
		text.setText(configData.getLangRes().getString("license")); //$NON-NLS-1$

		if (!configData.isDarkMode()) {
			return;
		}

		final var lght = new Color(0xD0, 0xD0, 0xD0);
		text.setForeground(lght);
		swtlic.setForeground(lght);
	}

	/**
	 * Button for closing the dialog.
	 */
	private void okButton() {
		final var button = ButtonWidget.newButton(dialog, SWT.PUSH, "button_ok", //$NON-NLS-1$
				Grid.newGridData(SWT.CENTER, SWT.FILL, true, false, 75, -1), close);
		widgets.add(button);
		button.setFocus();
		button.setLocation(dialog.getBounds().width / 2, dialog.getBounds().height - 50);
		dialog.setDefaultButton(button);
	}

	/**
	 * Creates a new Shell for the about dialog.
	 *
	 * @param parent The parent of the dialog.
	 */
	private void shell(final Shell parent) {
		dialog = ShellWidget.newShell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL, "about", Icons.text, 470, 530, //$NON-NLS-1$
				Grid.newGridLayout(5, 5, 5, 5, 1, false), true, false);
		dialog.setBackground(parent.getBackground());
		dialog.setForeground(parent.getForeground());
		dialog.setBackgroundMode(SWT.INHERIT_FORCE);
		widgets.add(dialog);
	}
}
