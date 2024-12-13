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

import java.util.List;

import org.eclipse.swt.custom.StyledText;

/**
 * Utility class for the Undo / Redo action.
 *
 * @author philipp@seerainer.com
 */
public final class UndoUtil {

	/**
	 * Redo function of the styled text.
	 *
	 * @param redoStack  The list which save all redo actions.
	 * @param undoStack  The list which save all undo actions.
	 * @param styledText The text widget for the undo function.
	 */
	public static void redo(final List<UndoUtil> redoStack, final List<UndoUtil> undoStack,
			final StyledText styledText) {
		if (redoStack.size() <= 0) {
			return;
		}
		final var lastEdit = redoStack.remove(0);
		final var text = lastEdit.getText().length();
		final var caret = lastEdit.getCaret();
		try {
			if (lastEdit.isReplaced()) {
				styledText.replaceTextRange(caret, text, ""); //$NON-NLS-1$
			} else {
				styledText.replaceTextRange(caret - text, 0, lastEdit.getText());
			}
		} catch (final IllegalArgumentException e) {
			e.printStackTrace();
		}
		undoStack.add(0, lastEdit);
	}

	/**
	 * Undo function of the styled text.
	 *
	 * @param undoStack  The list which save all undo actions.
	 * @param redoStack  The list which save all redo actions.
	 * @param styledText The text widget for the undo function.
	 */
	public static void undo(final List<UndoUtil> undoStack, final List<UndoUtil> redoStack,
			final StyledText styledText) {
		if (undoStack.size() <= 0) {
			return;
		}
		final var lastEdit = undoStack.remove(0);
		final var text = lastEdit.getText().length();
		final var caret = lastEdit.getCaret();
		try {
			if (lastEdit.isReplaced()) {
				styledText.replaceTextRange(caret, 0, lastEdit.getText());
			} else {
				styledText.replaceTextRange(caret - text, text, ""); //$NON-NLS-1$
			}
		} catch (final IllegalArgumentException e) {
			e.printStackTrace();
		}
		redoStack.add(0, lastEdit);
	}

	/** Instance of the boolean value if the text was replaced. */
	private final boolean replaced;

	/** Instance of the caret position in the text. */
	private final int caret;

	/** Instance of the text for the undo action. */
	private final String text;

	/**
	 * Creates a new instance of the class with the given parameters.
	 *
	 * @param text     The text for the undo action.
	 * @param replaced The boolean value if the text was replaced.
	 * @param caret    The int value for the caret position.
	 */
	public UndoUtil(final String text, final boolean replaced, final int caret) {
		this.text = text;
		this.replaced = replaced;
		this.caret = caret;
	}

	/**
	 * @return Return caret.
	 */
	public int getCaret() {
		return caret;
	}

	/**
	 * @return Return text.
	 */
	public String getText() {
		return text;
	}

	/**
	 * @return Return replaced.
	 */
	public boolean isReplaced() {
		return replaced;
	}
}
