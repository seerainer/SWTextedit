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

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import io.github.seerainer.swtextedit.util.ImageUtil;
import io.github.seerainer.swtextedit.util.StringUtil;

/**
 * Creates a new Shell widget with the given parameters.
 *
 * @author philipp@seerainer.com
 */
public final class ShellWidget {

	/**
	 * Creates a new Shell with the given Parameters.
	 *
	 * @param parent     Receives the parent of the shell (Shell).
	 * @param style      Receives the style of the shell.
	 * @param textID     Receives the text of the shell.
	 * @param icon       The icon of the shell.
	 * @param width      The width of the shell.
	 * @param height     The height of the shell.
	 * @param gridLayout The layout of the shell (GridLayout).
	 * @param middle     Specifies if the shell should be placed in the middle of
	 *                   the screen.
	 * @param maximize   Specifies if the shell should be maximized.
	 * @return Returns the new Shell.
	 */
	private static Shell createShell(final Display display, final Shell parent, final int style, final String textID,
			final String icon, final int width, final int height, final GridLayout gridLayout, final boolean middle,
			final boolean maximize) {
		Shell shell = null;

		if (display != null) {
			shell = new Shell(display, style);
		} else {
			shell = new Shell(parent, style);
		}

		if (!StringUtil.isValueEmpty(textID)) {
			shell.setData("TEXTID", textID); //$NON-NLS-1$
		}

		if (!StringUtil.isValueEmpty(icon)) {
			shell.setImage(ImageUtil.newImage(shell.getDisplay(), icon));
		}

		shell.setSize(width, height);

		if (gridLayout != null) {
			shell.setLayout(gridLayout);
		}

		if (middle) {
			final var r = shell.getDisplay().getBounds();
			final var s = shell.getBounds();
			final var shellX = (r.width - s.width) / 2;
			final var shellY = (r.height - s.height) / 2;
			shell.setLocation(shellX, shellY);
		}

		if (maximize) {
			shell.setMaximized(maximize);
		}

		return shell;
	}

	/**
	 * @see io.github.seerainer.swtextedit.widgets.ShellWidget#createShell(Shell,
	 *      int, String, String, int, int, GridLayout, boolean, boolean)
	 */
	public static Shell newShell(final Display display, final int style, final String textID, final String icon,
			final int width, final int height, final GridLayout gridLayout, final boolean middle,
			final boolean maximize) {
		return createShell(display, null, style, textID, icon, width, height, gridLayout, middle, maximize);
	}

	/**
	 * @see io.github.seerainer.swtextedit.widgets.ShellWidget#createShell(Shell,
	 *      int, String, String, int, int, GridLayout, boolean, boolean)
	 */
	public static Shell newShell(final Shell parent, final int style, final String textID, final String icon,
			final int width, final int height, final GridLayout gridLayout, final boolean middle,
			final boolean maximize) {
		return createShell(null, parent, style, textID, icon, width, height, gridLayout, middle, maximize);
	}

	/** Private empty constructor. */
	private ShellWidget() {
	}
}
