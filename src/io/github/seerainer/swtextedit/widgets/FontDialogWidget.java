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

import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.ColorDialog;
import org.eclipse.swt.widgets.FontDialog;
import org.eclipse.swt.widgets.Shell;

import io.github.seerainer.swtextedit.config.ConfigData;

/**
 * Dialog for the font, fore- and backgroundcolor of the styled text.
 *
 * @author philipp@seerainer.com
 */
public final class FontDialogWidget {

	/** Instance of the color dialog. */
	private static ColorDialog colorDialog;

	/** Instance of default additive color model. */
	private static RGB rgb;

	/**
	 * Dialog for the background color of the styled text.
	 *
	 * @param parent     The parent of the dialog.
	 * @param text       The text widget where the background color should be
	 *                   changed.
	 * @param configData The configuration data of the GUI.
	 */
	public static void backColor(final Shell parent, final StyledText text, final ConfigData configData) {
		colorDialog = new ColorDialog(parent);
		colorDialog.setRGB(configData.getBackgroundColor().getRGB());
		rgb = colorDialog.open();

		if (rgb == null) {
			return;
		}

		configData.setBackgroundColor(new Color(parent.getDisplay(), rgb));
		text.setBackground(configData.getBackgroundColor());
	}

	/**
	 * Dialog for the font of the styled text.
	 *
	 * @param parent     The parent of the dialog.
	 * @param text       The text widget where the font should be changed.
	 * @param configData The configuration data of the GUI.
	 */
	public static void font(final Shell parent, final StyledText text, final ConfigData configData) {
		final var fontDialog = new FontDialog(parent);
		fontDialog.setFontList(configData.getFont().getFontData());
		final var fontData = fontDialog.open();

		if (fontData == null) {
			return;
		}

		configData.setFont(new Font(parent.getDisplay(), fontData));
		text.setFont(configData.getFont());
	}

	/**
	 * Dialog for the foreground color of the styled text.
	 *
	 * @param parent     The parent of the dialog.
	 * @param text       The text widget where the foreground color should be
	 *                   changed.
	 * @param configData The configuration data of the GUI.
	 */
	public static void foreColor(final Shell parent, final StyledText text, final ConfigData configData) {
		colorDialog = new ColorDialog(parent);
		colorDialog.setRGB(configData.getForegroundColor().getRGB());
		rgb = colorDialog.open();

		if (rgb == null) {
			return;
		}

		configData.setForegroundColor(new Color(parent.getDisplay(), rgb));
		text.setForeground(configData.getForegroundColor());
	}

	/**
	 * Dialog for the selection background color of the styled text.
	 *
	 * @param parent     The parent of the dialog.
	 * @param text       The text widget where the background color should be
	 *                   changed.
	 * @param configData The configuration data of the GUI.
	 */
	public static void selectBackColor(final Shell parent, final StyledText text, final ConfigData configData) {
		colorDialog = new ColorDialog(parent);
		colorDialog.setRGB(configData.getSelectionBackground().getRGB());
		rgb = colorDialog.open();

		if (rgb == null) {
			return;
		}

		configData.setSelectionBackground(new Color(parent.getDisplay(), rgb));
		text.setSelectionBackground(configData.getSelectionBackground());
	}

	/**
	 * Dialog for the selection foreground color of the styled text.
	 *
	 * @param parent     The parent of the dialog.
	 * @param text       The text widget where the foreground color should be
	 *                   changed.
	 * @param configData The configuration data of the GUI.
	 */
	public static void selectForeColor(final Shell parent, final StyledText text, final ConfigData configData) {
		colorDialog = new ColorDialog(parent);
		colorDialog.setRGB(configData.getSelectionForeground().getRGB());
		rgb = colorDialog.open();

		if (rgb == null) {
			return;
		}

		configData.setSelectionForeground(new Color(parent.getDisplay(), rgb));
		text.setSelectionForeground(configData.getSelectionForeground());
	}

	/** Private empty constructor. */
	private FontDialogWidget() {
	}
}
