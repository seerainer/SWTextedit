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

import java.util.Enumeration;
import java.util.HashSet;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import io.github.seerainer.swtextedit.config.ConfigData;
import io.github.seerainer.swtextedit.config.Icons;
import io.github.seerainer.swtextedit.layout.Grid;
import io.github.seerainer.swtextedit.util.LangUtil;
import io.github.seerainer.swtextedit.widgets.ShellWidget;

/**
 * Shows all important system properties.
 *
 * @author philipp@seerainer.com
 */
public final class SystemProperties {

	/** HashSet for the language control. */
	private final HashSet<Shell> widgets = new HashSet<>();

	/** Instance of Shell. */
	private Shell sysProps;

	/** Public constructor. */
	public SystemProperties(final Shell parent, final ConfigData configData) {
		shell(parent);
		table(configData);

		LangUtil.setLang(widgets, configData);

		sysProps.open();
	}

	/**
	 * Creates a new Shell for the system information dialog.
	 *
	 * @param parent The parent of the dialog.
	 */
	private void shell(final Shell parent) {
		sysProps = ShellWidget.newShell(parent, SWT.SHELL_TRIM | SWT.APPLICATION_MODAL, "systeminfo_title", //$NON-NLS-1$
				Icons.text, 1024, 768, Grid.newGridLayout(0, 0, 0, 0, 1, false), true, true);
		widgets.add(sysProps);
	}

	/**
	 * Creates a table with the system properties.
	 */
	private void table(final ConfigData configData) {
		final var tbl = new Table(sysProps, SWT.FULL_SELECTION);
		tbl.setLayoutData(Grid.newGridData(true, true));
		tbl.setLinesVisible(true);

		final var col1 = new TableColumn(tbl, SWT.LEAD, 0);
		final var col2 = new TableColumn(tbl, SWT.LEAD, 1);

		final var properties = System.getProperties();

		for (final Enumeration<?> e = properties.keys(); e.hasMoreElements();) {
			final var item = new TableItem(tbl, SWT.NONE);
			final Object key = e.nextElement();
			item.setText(new String[] { (String) key, (String) properties.get(key) });
		}

		final var env = System.getenv();

		env.keySet().forEach((final String envName) -> new TableItem(tbl, SWT.NONE)
				.setText(new String[] { envName, env.get(envName) }));

		col1.pack();
		col2.pack();

		if (!configData.isDarkMode()) {
			return;
		}

		tbl.setBackground(new Color(0x30, 0x30, 0x30));
		tbl.setForeground(new Color(0xEE, 0xEE, 0xEE));
		tbl.setHeaderBackground(new Color(0x40, 0x40, 0x40));
		tbl.setHeaderForeground(new Color(0xDD, 0xDD, 0xDD));
	}
}
