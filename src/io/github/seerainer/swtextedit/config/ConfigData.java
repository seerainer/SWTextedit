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
package io.github.seerainer.swtextedit.config;

import java.util.ResourceBundle;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.Display;

import io.github.seerainer.swtextedit.util.LangUtil;

/**
 * All configuration data of the GUI.<br>
 * All variables are initialized with the default values.
 *
 * @author philipp@seerainer.com
 */
public final class ConfigData {

	/** Constants for the program version. */
	private final String version = "0.2.0"; //$NON-NLS-1$

	/** DarkMode */
	private final boolean darkMode = "win32".equals(SWT.getPlatform()) && Display.isSystemDarkTheme(); //$NON-NLS-1$

	/** Boolean value if text has changed. */
	private boolean hasChanged = false;

	/** Specifies if the shell should be maximized. */
	private final boolean maximize = true;

	/** Specifies if the text in the styledtext should be wrapped. */
	private boolean wrap = true;

	/** Values for the size of the Shell. */
	private final int shellWidth = 800;

	/** Values for the size of the Shell. */
	private final int shellHeight = 600;

	/** Value for the undo / redo action. */
	private final int undoStackSize = 9999;

	/** The background color of the text widget. */
	private Color backgroundColor = new Color(null, 255, 255, 255);

	/** The foreground color of the text widget. */
	private Color foregroundColor = new Color(null, 0, 0, 0);

	/** The selection background color of the text widget. */
	private Color selectionBackground = new Color(null, 178, 180, 191);

	/** The selection foreground color of the text widget. */
	private Color selectionForeground = new Color(null, 0, 0, 0);

	/** The font of the text widget. */
	private Font font = new Font(null, new FontData("Tahoma", 10, SWT.NORMAL)); //$NON-NLS-1$

	/** The path and name of the open file. */
	private String filename = null;

	/** Instance of the system language. */
	private String language = LangUtil.parseLang();

	/** The language file of the specific language. */
	private ResourceBundle langRes = ResourceBundle.getBundle(getLanguage());

	/** Public empty constructor. */
	public ConfigData() {
	}

	/**
	 * @return Return backgroundColor.
	 */
	public Color getBackgroundColor() {
		return backgroundColor;
	}

	/**
	 * @return Return filename.
	 */
	public String getFilename() {
		return filename;
	}

	/**
	 * @return Return font.
	 */
	public Font getFont() {
		return font;
	}

	/**
	 * @return Return foregroundColor.
	 */
	public Color getForegroundColor() {
		return foregroundColor;
	}

	/**
	 * @return Return langRes.
	 */
	public ResourceBundle getLangRes() {
		return langRes;
	}

	/**
	 * @return Return language.
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * @return Return selectionBackground.
	 */
	public Color getSelectionBackground() {
		return selectionBackground;
	}

	/**
	 * @return Return selectionForeground.
	 */
	public Color getSelectionForeground() {
		return selectionForeground;
	}

	/**
	 * @return Return shellHeight.
	 */
	public int getShellHeight() {
		return shellHeight;
	}

	/**
	 * @return Return shellWidth.
	 */
	public int getShellWidth() {
		return shellWidth;
	}

	/**
	 * @return Return undoStackSize.
	 */
	public int getUndoStackSize() {
		return undoStackSize;
	}

	/**
	 * @return Return version.
	 */
	public String getVersion() {
		return version;
	}

	public boolean isDarkMode() {
		return darkMode;
	}

	/**
	 * @return Return hasChanged.
	 */
	public boolean isHasChanged() {
		return hasChanged;
	}

	/**
	 * @return Return maximize.
	 */
	public boolean isMaximize() {
		return maximize;
	}

	/**
	 * @return Return wrap.
	 */
	public boolean isWrap() {
		return wrap;
	}

	/**
	 * @param backgroundColor Set backgroundColor.
	 */
	public void setBackgroundColor(final Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	/**
	 * @param filename Set filename.
	 */
	public void setFilename(final String filename) {
		this.filename = filename;
	}

	/**
	 * @param font Set font.
	 */
	public void setFont(final Font font) {
		this.font = font;
	}

	/**
	 * @param foregroundColor Set foregroundColor.
	 */
	public void setForegroundColor(final Color foregroundColor) {
		this.foregroundColor = foregroundColor;
	}

	/**
	 * @param hasChanged Set hasChanged.
	 */
	public void setHasChanged(final boolean hasChanged) {
		this.hasChanged = hasChanged;
	}

	/**
	 * @param language Set language.
	 */
	public void setLanguage(final String language) {
		this.language = language;
		this.langRes = ResourceBundle.getBundle(language);
	}

	/**
	 * @param selectionBackground Set selectionBackground.
	 */
	public void setSelectionBackground(final Color selectionBackground) {
		this.selectionBackground = selectionBackground;
	}

	/**
	 * @param selectionForeground Set selectionForeground.
	 */
	public void setSelectionForeground(final Color selectionForeground) {
		this.selectionForeground = selectionForeground;
	}

	/**
	 * @param wrap Set wrap.
	 */
	public void setWrap(final boolean wrap) {
		this.wrap = wrap;
	}
}
