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
package io.github.seerainer.swtextedit.layout;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;

/**
 * Creates a new GridLayout / GridData widget with the given parameters.
 *
 * @author philipp@seerainer.com
 */
public final class Grid {

	/**
	 * Creates a new GridData with the given Parameters.
	 *
	 * @param hAlign Specifies the horizontal align.
	 * @param vAlign Specifies the vertical align.
	 * @param hSpace Specifies the horizontal space.
	 * @param vSpace Specifies the vertical space.
	 * @param hSpan  Specifies the number of column cells that will take up.
	 * @param vSpan  Specifies the number of rows that will take up.
	 * @param wHint  Specifies the preferred width in pixels.
	 * @param hHint  Specifies the preferred height in pixels.
	 * @return Returns the new gridData.
	 */
	private static GridData createGridData(final int hAlign, final int vAlign, final boolean hSpace,
			final boolean vSpace, final int hSpan, final int vSpan, final int wHint, final int hHint) {
		final var gridData = new GridData(hAlign, vAlign, hSpace, vSpace, hSpan, vSpan);

		if (wHint > -1) {
			gridData.widthHint = wHint;
		}

		if (hHint > -1) {
			gridData.heightHint = hHint;
		}

		return gridData;
	}

	/**
	 * Creates a new GridLayout with the given Parameters.
	 *
	 * @param nColumns Specifies the number of cell columns in the layout.
	 * @param equal    Specifies whether all columns will have the same width.
	 * @param wMargin  Specifies the horizontal margin.
	 * @param hMargin  Specifies the vertical margin.
	 * @param hSpace   Specifies the horizontal space. (opposite of GridData)
	 * @param vSpace   Specifies the vertical space. (opposite of GridData)
	 * @return Returns the new gridLayout.
	 */
	private static GridLayout createGridLayout(final int wMargin, final int hMargin, final int hSpace, final int vSpace,
			final int nColumns, final boolean equal) {
		final var gridLayout = new GridLayout(nColumns, equal);
		gridLayout.marginWidth = wMargin;
		gridLayout.marginHeight = hMargin;
		gridLayout.horizontalSpacing = hSpace;
		gridLayout.verticalSpacing = vSpace;
		gridLayout.marginTop = -2;

		return gridLayout;
	}

	/**
	 * @see io.github.seerainer.swtextedit.layout.Grid#createGridData(int, int,
	 *      boolean, boolean, int, int, int, int)
	 */
	public static GridData newGridData() {
		return createGridData(SWT.FILL, SWT.FILL, true, false, 1, 1, -1, -1);
	}

	/**
	 * @see io.github.seerainer.swtextedit.layout.Grid#createGridData(int, int,
	 *      boolean, boolean, int, int, int, int)
	 */
	public static GridData newGridData(final boolean hSpace, final boolean vSpace) {
		return createGridData(SWT.FILL, SWT.FILL, hSpace, vSpace, 1, 1, -1, -1);
	}

	/**
	 * @see io.github.seerainer.swtextedit.layout.Grid#createGridData(int, int,
	 *      boolean, boolean, int, int, int, int)
	 */
	public static GridData newGridData(final int hSpan, final int vSpan) {
		return createGridData(SWT.FILL, SWT.FILL, true, false, hSpan, vSpan, -1, -1);
	}

	/**
	 * @see io.github.seerainer.swtextedit.layout.Grid#createGridData(int, int,
	 *      boolean, boolean, int, int, int, int)
	 */
	public static GridData newGridData(final int hAlign, final int vAlign, final boolean hSpace, final boolean vSpace,
			final int wHint, final int hHint) {
		return createGridData(hAlign, vAlign, hSpace, vSpace, 1, 1, wHint, hHint);
	}

	/**
	 * @see io.github.seerainer.swtextedit.layout.Grid#createGridLayout(int, int,
	 *      int, int, int, boolean)
	 */
	public static GridLayout newGridLayout(final int wMargin, final int hMargin, final int hSpace, final int vSpace,
			final int nColumns, final boolean equal) {
		return createGridLayout(wMargin, hMargin, hSpace, vSpace, nColumns, equal);
	}

	/** Private empty constructor. */
	private Grid() {
	}
}
