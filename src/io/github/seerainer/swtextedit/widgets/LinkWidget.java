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
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Link;

import io.github.seerainer.swtextedit.util.StringUtil;

/**
 * Creates a new Link widget with the given parameters.
 *
 * @author philipp@seerainer.com
 */
public final class LinkWidget {

	/**
	 * Creates a new Link with the given Parameters.
	 *
	 * @param parent       The parent of the new link.
	 * @param style        The style of the new link.
	 * @param gridData     The layout of the link (GridData).
	 * @param textID       The text of the link.
	 * @param linkListener The listener to open the browser with the associated
	 *                     link.
	 * @return Returns the new link.
	 */
	private static Link createLink(final Composite parent, final int style, final GridData gridData,
			final String textID, final SelectionListener linkListener) {
		final var link = new Link(parent, style);
		link.setForeground(parent.getForeground());

		if (gridData != null) {
			link.setLayoutData(gridData);
		}

		if (!StringUtil.isValueEmpty(textID)) {
			link.setData("TEXTID", textID); //$NON-NLS-1$
		}

		if (linkListener != null) {
			link.addSelectionListener(linkListener);
		}

		return link;
	}

	/**
	 * @see io.github.seerainer.swtextedit.widgets.LinkWidget#createLink(Composite,
	 *      int, GridData, String, SelectionListener)
	 */
	public static Link newLink(final Composite parent, final int style, final GridData gridData, final String textID,
			final SelectionListener linkListener) {
		return createLink(parent, style, gridData, textID, linkListener);
	}

	/** Private empty constructor. */
	private LinkWidget() {
	}
}
