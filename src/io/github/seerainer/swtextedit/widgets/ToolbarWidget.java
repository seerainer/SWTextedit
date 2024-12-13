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
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import io.github.seerainer.swtextedit.util.ImageUtil;
import io.github.seerainer.swtextedit.util.StringUtil;

/**
 * Creates a new ToolBar & ToolItem widget with the given parameters.
 *
 * @author philipp@seerainer.com
 */
public final class ToolbarWidget {

	/**
	 * Creates a new ToolBar with the given parameters.
	 *
	 * @param parent   The parent of the toolbar.
	 * @param gridData The layout of the toolbar.
	 * @param layout   The layout of the toolbar.
	 * @return Returns the new toolbar.
	 */
	private static ToolBar createToolBar(final Composite parent, final GridData gridData, final GridLayout layout) {
		final var toolBar = new ToolBar(parent, SWT.FLAT | SWT.SHADOW_OUT);

		if (gridData != null) {
			toolBar.setLayoutData(gridData);
		}

		if (layout != null) {
			toolBar.setLayout(layout);
		}

		return toolBar;
	}

	/**
	 * Creates a new ToolItem with the given parameters.
	 *
	 * @param toolBar   The parent of the tool item (ToolBar).
	 * @param image     The graphic of the item.
	 * @param listener  The selection listener of the item.
	 * @param toolTipID The tooltip of the item.
	 * @return Returns the new tool item.
	 */
	private static ToolItem createToolItem(final ToolBar toolBar, final String image, final SelectionListener listener,
			final String toolTipID) {
		final var toolItem = new ToolItem(toolBar, SWT.PUSH);

		if (!StringUtil.isValueEmpty(image)) {
			toolItem.setImage(ImageUtil.newImage(toolBar.getDisplay(), image));
			toolItem.setDisabledImage(new Image(toolBar.getDisplay(), toolItem.getImage(), SWT.IMAGE_GRAY));
		}

		if (listener != null) {
			toolItem.addSelectionListener(listener);
		}

		if (!StringUtil.isValueEmpty(toolTipID)) {
			toolItem.setData("TOOLTIPID", toolTipID); //$NON-NLS-1$
		}

		return toolItem;
	}

	/**
	 * @see io.github.seerainer.swtextedit.widgets.ToolbarWidget#createToolBar(Composite,
	 *      GridData, GridLayout)
	 */
	public static ToolBar newToolBar(final Composite parent, final GridData gridData, final GridLayout layout) {
		return createToolBar(parent, gridData, layout);
	}

	/**
	 * @see io.github.seerainer.swtextedit.widgets.ToolbarWidget#createToolItem(ToolBar,
	 *      String, SelectionListener, String)
	 */
	public static ToolItem newToolItem(final ToolBar toolBar, final String image, final SelectionListener listener,
			final String toolTipID) {
		return createToolItem(toolBar, image, listener, toolTipID);
	}

	/** Private empty constructor. */
	private ToolbarWidget() {
	}
}
