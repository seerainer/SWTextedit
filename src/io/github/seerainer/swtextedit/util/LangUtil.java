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

import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.TrayItem;
import org.eclipse.swt.widgets.Widget;

import io.github.seerainer.swtextedit.config.ConfigData;

/**
 * Utility class for the language.
 *
 * @author philipp@seerainer.com
 */
public final class LangUtil {

	/** Instance of Iterator. */
	private static Iterator<?> iter;

	/** Instance of Widget. */
	private static Widget widget;

	/**
	 * Detect the language of the system.
	 *
	 * @return Return the language string.
	 */
	public static String parseLang() {
		final var lang = Locale.getDefault().getDisplayLanguage();

		if ("Deutsch".equals(lang)) { // $NON-NLS-1$
			return "DE"; //$NON-NLS-1$
		}
		return "EN"; //$NON-NLS-1$
	}

	/**
	 * Calls the methods setText & setToolTipText.
	 *
	 * @param widgets    All widgets of the GUI.
	 * @param configData The configuration data of the GUI.
	 */
	public static void setLang(final HashSet<?> widgets, final ConfigData configData) {
		setText(widgets, configData);
		setToolTipText(widgets, configData);
	}

	/**
	 * Changes the language (text) of the specific widget.
	 *
	 * @param widgets    The widget of which the language should be changed.
	 * @param configData Instance of the configuration data.
	 */
	private static void setText(final HashSet<?> widgets, final ConfigData configData) {
		for (iter = widgets.iterator(); iter.hasNext();) {
			widget = (Widget) iter.next();
			if (widget.getData("TEXTID") != null) { // $NON-NLS-1$
				if (widget.getClass().equals(MenuItem.class)) {
					((MenuItem) widget).setText(configData.getLangRes().getString((String) (widget.getData("TEXTID")))); //$NON-NLS-1$
				} else if (widget.getClass().equals(Button.class)) {
					((Button) widget).setText(configData.getLangRes().getString((String) (widget.getData("TEXTID")))); //$NON-NLS-1$
				} else if (widget.getClass().equals(Shell.class)) {
					((Shell) widget).setText(configData.getLangRes().getString((String) (widget.getData("TEXTID")))); //$NON-NLS-1$
				} else if (widget.getClass().equals(Label.class)) {
					((Label) widget).setText(configData.getLangRes().getString((String) (widget.getData("TEXTID")))); //$NON-NLS-1$
				} else if (widget.getClass().equals(Group.class)) {
					((Group) widget).setText(configData.getLangRes().getString((String) (widget.getData("TEXTID")))); //$NON-NLS-1$
				} else if (widget.getClass().equals(Link.class)) {
					((Link) widget).setText(configData.getLangRes().getString((String) (widget.getData("TEXTID")))); //$NON-NLS-1$
				} else {
					System.out.println("Widget can't be found!"); //$NON-NLS-1$
				}
			}
		}
	}

	/**
	 * Changes the language (tooltip) of the specific widget.
	 *
	 * @param widgets    The widget of which the language should be changed.
	 * @param configData Instance of the configuration data.
	 */
	private static void setToolTipText(final HashSet<?> widgets, final ConfigData configData) {
		for (iter = widgets.iterator(); iter.hasNext();) {
			widget = (Widget) iter.next();
			if (widget.getData("TOOLTIPID") != null) { // $NON-NLS-1$
				if (widget.getClass().equals(ToolItem.class)) {
					((ToolItem) widget)
							.setToolTipText(configData.getLangRes().getString((String) (widget.getData("TOOLTIPID")))); //$NON-NLS-1$
				} else if (widget.getClass().equals(TrayItem.class)) {
					((TrayItem) widget)
							.setToolTipText(configData.getLangRes().getString((String) (widget.getData("TOOLTIPID")))); //$NON-NLS-1$
				} else if (widget.getClass().equals(Label.class)) {
					((Label) widget)
							.setToolTipText(configData.getLangRes().getString((String) (widget.getData("TOOLTIPID")))); //$NON-NLS-1$
				} else {
					System.out.println("Widget can't be found!"); //$NON-NLS-1$
				}
			}
		}
	}

	/** Private empty constructor. */
	private LangUtil() {
	}
}
