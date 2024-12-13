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

import java.io.IOException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

/**
 * Utility class for the GUI.
 *
 * @author philipp@seerainer.com
 */
public final class ImageUtil {

	/**
	 * Generates a new graphic.
	 *
	 * @param display The parent of the image.
	 * @param image   Name of the image.
	 * @return Returns the graphic.
	 */
	public static Image newImage(final Display display, final String image) {
		try (final var is = ClassLoader.getSystemResource(image).openStream()) {
			final var img = new Image(display, is);
			img.setBackground(display.getSystemColor(SWT.COLOR_WIDGET_BACKGROUND));
			return img;
		} catch (final IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	/** Private empty constructor. */
	private ImageUtil() {
	}
}
