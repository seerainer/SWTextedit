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

import io.github.seerainer.swtextedit.config.ConfigData;

/**
 * Utility class for the styled text widget.
 *
 * @author philipp@seerainer.com
 */
public final class TextUtil {

	/**
	 * Wraps the lines of the styled text widget.
	 *
	 * @param configData The configuration data of the GUI.
	 * @param text       The styled text widget.
	 */
	public static void wrap(final ConfigData configData, final StyledText text) {
		if (configData.isWrap()) {
			configData.setWrap(false);
		} else {
			configData.setWrap(true);
		}
		text.setWordWrap(configData.isWrap());
	}

	/** Private empty constructor. */
	private TextUtil() {
	}
}
