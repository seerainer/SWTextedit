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
package io.github.seerainer.swtextedit.util;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.printing.PrintDialog;
import org.eclipse.swt.printing.Printer;
import org.eclipse.swt.widgets.Shell;

/**
 * Utility class for printing the text.
 *
 * @author philipp@seerainer.com
 */
public final class PrintUtil {

	/**
	 * Opens the print dialog.
	 *
	 * @param shell The parent of the dialog.
	 * @param text  The text of the text widget.
	 */
	public static void printDialog(final Shell shell, final StyledText text) {
		final var dialog = new PrintDialog(shell, SWT.NONE);
		final var data = dialog.open();
		if (data == null) {
			return;
		}
		final var printer = new Printer(data);
		final var runnable = text.print(printer);
		new Thread(() -> {
			runnable.run();
			printer.dispose();
		}).start();
	}

	/** Private empty constructor. */
	private PrintUtil() {
	}
}
