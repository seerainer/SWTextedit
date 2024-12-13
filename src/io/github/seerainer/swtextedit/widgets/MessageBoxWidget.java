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

import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import io.github.seerainer.swtextedit.util.StringUtil;

/**
 * Creates a new MessageBox widget with the given parameters.
 *
 * @author philipp@seerainer.com
 */
public final class MessageBoxWidget {

	/**
	 * Creates a new MessageBox with the given Parameters.
	 *
	 * @param parent  The parent of the new mb.
	 * @param style   The style of the new mb.
	 * @param text    The title text of the mb.
	 * @param message The message or the question.
	 * @return Returns the new mb.
	 */
	private static MessageBox createMessageBox(final Shell parent, final int style, final String text,
			final String message) {
		final var mb = new MessageBox(parent, style);

		if (!StringUtil.isValueEmpty(text)) {
			mb.setText(text);
		}

		if (!StringUtil.isValueEmpty(message)) {
			mb.setMessage(message);
		}

		return mb;
	}

	/**
	 * @see io.github.seerainer.swtextedit.widgets.MessageBoxWidget#createMessageBox(Shell,
	 *      int, String, String)
	 */
	public static MessageBox newMessageBox(final Shell parent, final int style, final String text,
			final String message) {
		return createMessageBox(parent, style, text, message);
	}

	/** Private empty constructor. */
	private MessageBoxWidget() {
	}
}
