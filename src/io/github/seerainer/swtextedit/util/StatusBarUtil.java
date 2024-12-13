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
import org.eclipse.swt.widgets.Label;

/**
 * Utility class for the statusbar.
 *
 * @author philipp@seerainer.com
 */
public final class StatusBarUtil {

	/**
	 * Output some information about the text in the status bar.
	 *
	 * @param lab1    The filename of the text.
	 * @param lab2    The insert / overwrite state.
	 * @param lab3    The position of the cursor and the amount of characters in the
	 *                text.
	 * @param lab4    The position of the row and column.
	 * @param lab5    The amount of selected text.
	 * @param keycode The key which was pressed.
	 * @param text    The text widget where you get the information.
	 * @param file    The path and name of the file in the text widget.
	 */
	public static void status(final Label lab1, final Label lab2, final Label lab3, final Label lab4, final Label lab5,
			final int keycode, final StyledText text, final String file) {

		final var caretOffset = text.getCaretOffset();

		if (file == null) {
			lab1.setText(""); //$NON-NLS-1$
		} else {
			lab1.setText(file);
		}

		if (keycode == SWT.INSERT) {
			if ("INS".equals(lab2.getText())) { //$NON-NLS-1$
				lab2.setText("OVR"); //$NON-NLS-1$
			} else { // $NON-NLS-1$
				lab2.setText("INS"); //$NON-NLS-1$
			}
		}

		lab3.setText(caretOffset + " / " + text.getCharCount()); //$NON-NLS-1$
		lab4.setText((text.getLineAtOffset(caretOffset) + 1) + " / " //$NON-NLS-1$
				+ (caretOffset - text.getOffsetAtLine(text.getLineAtOffset(caretOffset)) + 1));
		lab5.setText(Integer.toString(text.getSelectionCount()));

		lab1.setToolTipText(lab1.getText());
		lab2.setToolTipText(lab2.getText());
		lab3.setToolTipText(lab3.getText());
		lab4.setToolTipText(lab4.getText());
		lab5.setToolTipText(lab5.getText());
	}

	/** Private empty constructor. */
	private StatusBarUtil() {
	}
}
