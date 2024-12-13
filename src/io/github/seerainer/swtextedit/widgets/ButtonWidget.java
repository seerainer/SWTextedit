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

import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import io.github.seerainer.swtextedit.util.StringUtil;

/**
 * Creates a new Button widget with the given parameters.
 *
 * @author philipp@seerainer.com
 */
public final class ButtonWidget {

	/**
	 * Creates a new Button with the given Parameters.
	 *
	 * @param parent   The parent of the new button.
	 * @param style    The style of the new button.
	 * @param textID   Sets the text of the button.
	 * @param gridData The layout of the button (GridData).
	 * @param select   Adds the SelectionListener of the button.
	 * @return Returns the new button.
	 */
	private static Button createButton(final Composite parent, final int style, final String textID,
			final GridData gridData, final SelectionListener select) {
		final var button = new Button(parent, style);
		button.setForeground(parent.getForeground());

		if (!StringUtil.isValueEmpty(textID)) {
			button.setData("TEXTID", textID); //$NON-NLS-1$
		}

		if (gridData != null) {
			button.setLayoutData(gridData);
		}

		if (select != null) {
			button.addSelectionListener(select);
		}

		return button;
	}

	/**
	 * @see io.github.seerainer.swtextedit.widgets.ButtonWidget#createButton(Composite,
	 *      int, String, GridData, SelectionListener)
	 */
	public static Button newButton(final Composite parent, final int style, final String textID,
			final GridData gridData, final SelectionListener select) {
		return createButton(parent, style, textID, gridData, select);
	}

	/** Private empty constructor. */
	private ButtonWidget() {
	}
}
