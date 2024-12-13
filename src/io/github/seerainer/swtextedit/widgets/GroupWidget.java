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

import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

import io.github.seerainer.swtextedit.util.StringUtil;

/**
 * Creates a new Group widget with the given parameters.
 *
 * @author philipp@seerainer.com
 */
public final class GroupWidget {

	/**
	 * Creates a new Group with the given parameters.
	 *
	 * @param parent     The parent of the new group.
	 * @param style      The style of the new group.
	 * @param gridData   The layout of the group (GridData).
	 * @param gridLayout The layout of the widgets in the group (GridLayout).
	 * @param textID     The text of the group.
	 * @return Returns the new group.
	 */
	private static Group createGroup(final Composite parent, final int style, final GridData gridData,
			final GridLayout gridLayout, final String textID) {
		final var group = new Group(parent, style);
		group.setForeground(parent.getForeground());

		if (gridData != null) {
			group.setLayoutData(gridData);
		}

		if (gridLayout != null) {
			group.setLayout(gridLayout);
		}

		if (!StringUtil.isValueEmpty(textID)) {
			group.setData("TEXTID", textID); //$NON-NLS-1$
		}

		return group;
	}

	/**
	 * @see io.github.seerainer.swtextedit.widgets.GroupWidget#createGroup(Composite,
	 *      int, GridData, GridLayout, String)
	 */
	public static Group newGroup(final Composite parent, final int style, final GridData gridData,
			final GridLayout gridLayout, final String textID) {
		return createGroup(parent, style, gridData, gridLayout, textID);
	}

	/** Private empty constructor. */
	private GroupWidget() {
	}
}
