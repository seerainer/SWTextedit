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
package io.github.seerainer.swtextedit.widgets;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

import io.github.seerainer.swtextedit.config.ConfigData;
import io.github.seerainer.swtextedit.io.IO;
import io.github.seerainer.swtextedit.util.StringUtil;

/**
 * Dialog for opening or saving.
 *
 * @author philipp@seerainer.com
 */
public final class FileDialogWidget {

	/** The status of opening and saving the file. */
	private static boolean status;

	/** Instance of the file for opening and saving. */
	private static File file;

	/** String of the path and name of the file. */
	private static String path;

	/**
	 * Message box for Warnings.
	 *
	 * @param shell The parent of the message box.
	 * @param text  The text which will be shown.
	 */
	private static void errorMsg(final Shell shell, final String text) {
		MessageBoxWidget.newMessageBox(shell, SWT.ICON_WARNING | SWT.OK, text, path).open();
	}

	/**
	 * FileDialog for opening or saving a textfile.
	 *
	 * @param shell      The parent of the dialog.
	 * @param configData The configuration values of the GUI.
	 * @param style      The style which may be OPEN or SAVE.
	 * @param text       The text widget for the text file.
	 * @return Returns the path and filename.
	 */
	public static boolean fileDialog(final Shell shell, final ConfigData configData, final int style,
			final StyledText text) {
		final var fileDialog = new FileDialog(shell, style);
		fileDialog.setFilterNames(new String[] { configData.getLangRes().getString("filedialog_filternames_all") }); //$NON-NLS-1$
		fileDialog.setFilterExtensions(new String[] { "*.*" }); //$NON-NLS-1$
		fileDialog.setOverwrite(true);
		status = false;

		if (style == SWT.OPEN) {
			do {
				path = fileDialog.open();
				if (StringUtil.isValueEmpty(path)) {
					return false;
				}
				file = new File(path);
				if (file.getAbsoluteFile().exists()) {
					status = IO.open(file, text);
					if (status) {
						configData.setFilename(path);
					} else {
						errorMsg(shell, configData.getLangRes().getString("filedialog_error_open")); //$NON-NLS-1$
					}
				} else {
					errorMsg(shell, configData.getLangRes().getString("filedialog_error_found")); //$NON-NLS-1$
				}
			} while (!status);
		} else if (style == SWT.SAVE) {
			do {
				path = fileDialog.open();
				if (StringUtil.isValueEmpty(path)) {
					return false;
				}
				file = new File(path);
				status = IO.save(file, text.getText());
				if (status) {
					configData.setFilename(path);
				} else {
					errorMsg(shell, configData.getLangRes().getString("filedialog_error_save")); //$NON-NLS-1$
				}
			} while (!status);
		}

		return true;
	}

	/**
	 * For opening a text file at startup or drag-and-drop.
	 *
	 * @param text       The text widget for the text file.
	 * @param shell      The parent of the message box.
	 * @param configData The configuration values of the GUI.
	 */
	public static void open(final StyledText text, final Shell shell, final ConfigData configData) {
		path = configData.getFilename();
		if (!StringUtil.isValueEmpty(path)) {
			file = new File(path);
			if (file.getAbsoluteFile().exists()) {
				status = IO.open(file, text);
				if (!status) {
					errorMsg(shell, configData.getLangRes().getString("filedialog_error_open")); //$NON-NLS-1$
				}
			} else {
				errorMsg(shell, configData.getLangRes().getString("filedialog_error_found")); //$NON-NLS-1$
			}
		}

		configData.setHasChanged(false);
	}

	/**
	 * Message box of "Yes", "No" & "Cancel".
	 *
	 * @param shell      The parent of the message box.
	 * @param configData The configuration values of the GUI.
	 * @return Returns the choice of the user.
	 */
	public static int saveYesNoCancel(final Shell shell, final ConfigData configData) {
		final String message;

		if (StringUtil.isValueEmpty(configData.getFilename())) {
			message = configData.getLangRes().getString("filedialog_save_untitled"); //$NON-NLS-1$
		} else {
			message = "\"" + configData.getFilename() + "\""; //$NON-NLS-1$ //$NON-NLS-2$
		}

		return MessageBoxWidget.newMessageBox(shell, SWT.ICON_WARNING | SWT.YES | SWT.NO | SWT.CANCEL,
				configData.getLangRes().getString("filedialog_save"), message).open(); //$NON-NLS-1$
	}

	/** Private empty constructor. */
	private FileDialogWidget() {
	}
}
