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

import org.eclipse.swt.custom.StyledText;

/**
 * Utility class for string operations.
 *
 * @author philipp@seerainer.com
 */
public final class StringUtil {

	/**
	 * Checks if the String is empty.
	 *
	 * @param value This should be a String value.
	 * @return Returns true if the value is empty.
	 */
	public static boolean isValueEmpty(final Object value) {
		if (value != null && value.getClass().equals(String.class) && ((String) value).length() > 0) {
			return false;
		}
		return true;
	}

	/**
	 * Converts the text or the selected text to lowercase.
	 *
	 * @param text StyledText widget, contains the text.
	 * @return Returns the StyledText widget.
	 */
	public static StyledText lowercase(final StyledText text) {
		final var range = text.getSelectionRange();
		if (range.y > 0) {
			text.replaceTextRange(range.x, range.y, text.getSelectionText().toLowerCase());
		} else {
			text.setText(text.getText().toLowerCase());
		}

		return text;
	}

	/**
	 * Trimming leading and trailing whitespace of the text or the selected text.
	 *
	 * @param text StyledText widget, contains the text.
	 * @return Returns the StyledText widget.
	 */
	public static StyledText trim(final StyledText text) {
		final var range = text.getSelectionRange();
		if (range.y > 0) {
			text.replaceTextRange(range.x, range.y, text.getSelectionText().trim());
		} else {
			text.setText(text.getText().trim());
		}

		return text;
	}

	/**
	 * Converts the text or the selected text to uppercase.
	 *
	 * @param text StyledText widget, contains the text.
	 * @return Returns the StyledText widget.
	 */
	public static StyledText uppercase(final StyledText text) {
		final var range = text.getSelectionRange();
		if (range.y > 0) {
			text.replaceTextRange(range.x, range.y, text.getSelectionText().toUpperCase());
		} else {
			text.setText(text.getText().toUpperCase());
		}

		return text;
	}

	/** Private empty constructor. */
	private StringUtil() {
	}
}
