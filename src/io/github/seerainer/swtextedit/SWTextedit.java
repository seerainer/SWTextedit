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
package io.github.seerainer.swtextedit;

import java.util.HashSet;

import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Widget;

import io.github.seerainer.swtextedit.config.CharacterEncoding;
import io.github.seerainer.swtextedit.config.ConfigData;
import io.github.seerainer.swtextedit.util.LangUtil;
import io.github.seerainer.swtextedit.widgets.FileDialogWidget;

/**
 * SWTextedit - a simple text editor
 *
 * @author philipp@seerainer.com
 */
public final class SWTextedit {

	/**
	 * Main method.
	 *
	 * @param args Argument loaded with SWTextedit.
	 */
	public static void main(final String[] args) {
		if (args.length > 0) {
			new SWTextedit(args[0]);
		} else {
			new SWTextedit(null);
		}
	}

	/** Instance of GuiConfigData for all configuration data. */
	private final ConfigData configData = new ConfigData();

	/** HashSet for the language control. */
	private final HashSet<Widget> widgets = new HashSet<>();

	/** Instance of the UI widgets. */
	private final Widgets guiWidgets = new Widgets(configData, widgets);

	/**
	 * Default Constructor of SWTextedit.
	 *
	 * @param file Argument loaded with SWTextedit.
	 */
	private SWTextedit(final String file) {
		openClose(file);
	}

	/**
	 * Drop target on the control.
	 *
	 * @param control the styledText
	 */
	private void dropTarget(final Control control) {
		final var dropTarget = new DropTarget(control, DND.DROP_COPY | DND.DROP_DEFAULT);
		dropTarget.setTransfer(FileTransfer.getInstance());
		dropTarget.addDropListener(new DropTargetAdapter() {
			@Override
			public void dragEnter(final DropTargetEvent event) {
				if (event.detail == DND.DROP_DEFAULT) {
					event.detail = DND.DROP_COPY;
				}
			}

			@Override
			public void dragOperationChanged(final DropTargetEvent event) {
				if (event.detail == DND.DROP_DEFAULT) {
					event.detail = DND.DROP_COPY;
				}
			}

			@Override
			public void dragOver(final DropTargetEvent event) {
				if (event.detail == DND.DROP_DEFAULT) {
					event.detail = DND.DROP_COPY;
				}
			}

			@Override
			public void drop(final DropTargetEvent event) {
				if (event.data != null) {
					configData.setFilename(((String[]) event.data)[0]);
					FileDialogWidget.open(guiWidgets.getStyledText(), guiWidgets.getShell(), configData);
				}
			}
		});
	}

	/**
	 * Opens and closes the program.
	 */
	private void openClose(final String file) {
		final var shell = guiWidgets.getShell();
		CharacterEncoding.setEncoding(CharacterEncoding.UTF8);
		configData.setFilename(file);
		guiWidgets.getStyledText().setLeftMargin(1);
		dropTarget(guiWidgets.getStyledText());
		LangUtil.setLang(widgets, configData);

		shell.open();

		FileDialogWidget.open(guiWidgets.getStyledText(), guiWidgets.getShell(), configData);

		final var display = shell.getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
	}
}
