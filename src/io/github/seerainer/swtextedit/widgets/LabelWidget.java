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

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import io.github.seerainer.swtextedit.layout.Grid;
import io.github.seerainer.swtextedit.util.StringUtil;

/**
 * Creates a new Label widget with the given parameters.
 *
 * @author philipp@seerainer.com
 */
public final class LabelWidget {

	/**
	 * Creates a new Label with the given Parameters.
	 *
	 * @param parent   The parent of the new label.
	 * @param style    The style of the new label.
	 * @param gridData The layout of the label (GridData).
	 * @param textID   The text of the label.
	 * @return Returns the new label.
	 */
	private static Label createLabel(final Composite parent, final int style, final GridData gridData,
			final String textID) {
		final var label = new Label(parent, style);
		label.setForeground(parent.getForeground());

		if (gridData != null) {
			label.setLayoutData(gridData);
		}

		if (!StringUtil.isValueEmpty(textID)) {
			label.setData("TEXTID", textID); //$NON-NLS-1$
		}

		return label;
	}

	/**
	 * @see io.github.seerainer.swtextedit.widgets.LabelWidget#createLabel(Composite,
	 *      int, GridData, String)
	 */
	public static Label hLine(final Composite parent) {
		return createLabel(parent, SWT.SEPARATOR | SWT.HORIZONTAL, Grid.newGridData(), null);
	}

	/**
	 * @see io.github.seerainer.swtextedit.widgets.LabelWidget#createLabel(Composite,
	 *      int, GridData, String)
	 */
	public static Label newLabel(final Composite parent, final GridData gridData, final String textID) {
		return createLabel(parent, SWT.LEFT, gridData, textID);
	}

	/**
	 * @see io.github.seerainer.swtextedit.widgets.LabelWidget#createLabel(Composite,
	 *      int, GridData, String)
	 */
	public static Label vLine(final Composite parent) {
		return createLabel(parent, SWT.SEPARATOR | SWT.VERTICAL, Grid.newGridData(false, true), null);
	}

	/** Private empty constructor. */
	private LabelWidget() {
	}
}
