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
package io.github.seerainer.swtextedit;

import java.util.HashSet;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Widget;

import io.github.seerainer.swtextedit.config.CharacterEncoding;
import io.github.seerainer.swtextedit.config.ConfigData;
import io.github.seerainer.swtextedit.config.Icons;
import io.github.seerainer.swtextedit.layout.Grid;
import io.github.seerainer.swtextedit.widgets.LabelWidget;
import io.github.seerainer.swtextedit.widgets.MenuWidget;
import io.github.seerainer.swtextedit.widgets.ShellWidget;
import io.github.seerainer.swtextedit.widgets.StyledTextWidget;
import io.github.seerainer.swtextedit.widgets.ToolbarWidget;

/**
 * Creating the UI widgets.
 *
 * @author philipp@seerainer.com
 */
public final class Widgets {

	/** Instance of GuiConfigData for all configuration data. */
	private final ConfigData configData;

	/** HashSet for the language control. */
	private final HashSet<Widget> widgets;

	/** Instance of the UI events. */
	private final Events events;

	/** Instances of Label for the statusbar. */
	private Label status1;

	/**
	 * Instances of Label for the statusbar.
	 */
	private Label status2;

	/**
	 * Instances of Label for the statusbar.
	 */
	private Label status3;

	/**
	 * Instances of Label for the statusbar.
	 */
	private Label status4;

	/**
	 * Instances of Label for the statusbar.
	 */
	private Label status5;

	/** Instances of Menu. */
	private Menu fileMenu;

	/**
	 * Instances of Menu.
	 */
	private Menu editMenu;

	/**
	 * Instances of Menu.
	 */
	private Menu editPopup;

	/** Instance of Display */
	private Display display;

	/** Instance of the parent. */
	private Shell shell;

	/** Instance of StyledText. */
	private StyledText styledText;

	/** Instance of ToolBar. */
	private ToolBar toolBar;

	/**
	 * Default Constructor of Widgets.
	 *
	 * @param configData The configuration data.
	 * @param widgets    HashSet for the widgets.
	 */
	Widgets(final ConfigData configData, final HashSet<Widget> widgets) {
		this.configData = configData;
		this.widgets = widgets;
		this.events = new Events(this);

		shell();
		menu();
		toolbar();
		content();
		statusBar();
	}

	/**
	 * The content of the shell.
	 */
	private void content() {
		styledText = StyledTextWidget.newStyledText(shell, SWT.BORDER | SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL,
				popupMenu(), Grid.newGridData(true, true), true, configData.getFont(), configData.getBackgroundColor(),
				configData.getForegroundColor(), configData.getSelectionBackground(),
				configData.getSelectionForeground(), configData.isWrap());

		styledText.addExtendedModifyListener(events.undoredo);
		styledText.addKeyListener(events.keyPressed);
		styledText.addKeyListener(events.keyReleased);
		styledText.addModifyListener(events.textChanged);
		styledText.addMouseListener(events.mousePressed);
		styledText.addMouseListener(events.mouseReleased);
		styledText.addSelectionListener(events.selectText);

		if (!configData.isDarkMode()) {
			return;
		}
		styledText.setBackground(new Color(0x30, 0x30, 0x30));
		styledText.setForeground(new Color(0xD0, 0xD0, 0xD0));
		styledText.setBackgroundMode(SWT.INHERIT_FORCE);
	}

	/**
	 * Edit menu.
	 */
	private void editMenu() {
		editMenu = MenuWidget.newMenu(shell, SWT.DROP_DOWN, events.enableEditItems);
		widgets.add(MenuWidget.newMenuName(shell.getMenuBar(), SWT.CASCADE, "text_menu_edit", editMenu)); //$NON-NLS-1$

		widgets.add(
				MenuWidget.newMenuItem(editMenu, "text_menu_edit_undo", Icons.undo, SWT.CTRL + 'Z', events.undo)); //$NON-NLS-1$
		widgets.add(
				MenuWidget.newMenuItem(editMenu, "text_menu_edit_redo", Icons.redo, SWT.CTRL + 'Y', events.redo)); //$NON-NLS-1$

		new MenuItem(editMenu, SWT.SEPARATOR);

		widgets.add(MenuWidget.newMenuItem(editMenu, "text_menu_edit_cut", Icons.cut, SWT.CTRL + 'X', events.cut)); //$NON-NLS-1$
		widgets.add(
				MenuWidget.newMenuItem(editMenu, "text_menu_edit_copy", Icons.copy, SWT.CTRL + 'C', events.copy)); //$NON-NLS-1$
		widgets.add(
				MenuWidget.newMenuItem(editMenu, "text_menu_edit_paste", Icons.paste, SWT.CTRL + 'V', events.paste)); //$NON-NLS-1$
		widgets.add(MenuWidget.newMenuItem(editMenu, "text_menu_edit_del", Icons.del, SWT.NONE, events.del)); //$NON-NLS-1$

		new MenuItem(editMenu, SWT.SEPARATOR);

		widgets.add(MenuWidget.newMenuItem(editMenu, "text_menu_edit_selall", null, SWT.CTRL + 'A', events.selAll)); //$NON-NLS-1$
		widgets.add(MenuWidget.newMenuItem(editMenu, "text_menu_edit_delall", null, SWT.CTRL + SWT.SHIFT + SWT.DEL, //$NON-NLS-1$
				events.delAll));

		new MenuItem(editMenu, SWT.SEPARATOR);

		widgets.add(MenuWidget.newMenuItem(editMenu, "text_menu_edit_uppercase", null, SWT.NONE, events.uppercase)); //$NON-NLS-1$
		widgets.add(MenuWidget.newMenuItem(editMenu, "text_menu_edit_lowercase", null, SWT.NONE, events.lowercase)); //$NON-NLS-1$

		new MenuItem(editMenu, SWT.SEPARATOR);

		widgets.add(MenuWidget.newMenuItem(editMenu, "text_menu_edit_trim", null, SWT.NONE, events.trim)); //$NON-NLS-1$
	}

	/**
	 * File menu.
	 */
	private void fileMenu() {
		fileMenu = MenuWidget.newMenu(shell, SWT.DROP_DOWN, events.enableSaveItem);
		widgets.add(MenuWidget.newMenuName(shell.getMenuBar(), SWT.CASCADE, "text_menu_file", fileMenu)); //$NON-NLS-1$

		widgets.add(MenuWidget.newMenuItem(fileMenu, "text_menu_file_new", Icons.newfile, SWT.CTRL + 'N', //$NON-NLS-1$
				events.newFile));
		widgets.add(
				MenuWidget.newMenuItem(fileMenu, "text_menu_file_open", Icons.open, SWT.CTRL + 'O', events.open)); //$NON-NLS-1$
		widgets.add(
				MenuWidget.newMenuItem(fileMenu, "text_menu_file_save", Icons.save, SWT.CTRL + 'S', events.save)); //$NON-NLS-1$
		widgets.add(MenuWidget.newMenuItem(fileMenu, "text_menu_file_saveas", null, SWT.CTRL + SWT.SHIFT + 'S', //$NON-NLS-1$
				events.saveas));

		new MenuItem(fileMenu, SWT.SEPARATOR);

		widgets.add(
				MenuWidget.newMenuItem(fileMenu, "text_menu_file_print", Icons.print, SWT.CTRL + 'P', events.print)); //$NON-NLS-1$

		new MenuItem(fileMenu, SWT.SEPARATOR);

		widgets.add(MenuWidget.newMenuItem(fileMenu, "text_menu_file_exit", null, SWT.ESC, events.exit)); //$NON-NLS-1$
	}

	/**
	 * @return Return configData.
	 */
	ConfigData getConfigData() {
		return configData;
	}

	/**
	 * @return Return editMenu.
	 */
	Menu getEditMenu() {
		return editMenu;
	}

	/**
	 * @return Return editPopup.
	 */
	Menu getEditPopup() {
		return editPopup;
	}

	/**
	 * @return Return fileMenu.
	 */
	Menu getFileMenu() {
		return fileMenu;
	}

	/**
	 * @return Return shell.
	 */
	Shell getShell() {
		return shell;
	}

	/**
	 * @return Return status1.
	 */
	Label getStatus1() {
		return status1;
	}

	/**
	 * @return Return status2.
	 */
	Label getStatus2() {
		return status2;
	}

	/**
	 * @return Return status3.
	 */
	Label getStatus3() {
		return status3;
	}

	/**
	 * @return Return status4.
	 */
	Label getStatus4() {
		return status4;
	}

	/**
	 * @return Return status5.
	 */
	Label getStatus5() {
		return status5;
	}

	/**
	 * @return Return styledText.
	 */
	StyledText getStyledText() {
		return styledText;
	}

	/**
	 * @return Return toolBar.
	 */
	ToolBar getToolBar() {
		return toolBar;
	}

	/**
	 * @return Return widgets.
	 */
	HashSet<Widget> getWidgets() {
		return widgets;
	}

	/**
	 * Help menu.
	 */
	private void helpMenu() {
		final var helpMenu = MenuWidget.newMenu(shell, SWT.DROP_DOWN, null);
		widgets.add(MenuWidget.newMenuName(shell.getMenuBar(), SWT.CASCADE, "text_menu_help", helpMenu)); //$NON-NLS-1$

		widgets.add(MenuWidget.newMenuItem(helpMenu, "text_menu_help_system", null, SWT.NONE, events.systemconfig)); //$NON-NLS-1$

		new MenuItem(helpMenu, SWT.SEPARATOR);

		widgets.add(MenuWidget.newMenuItem(helpMenu, "text_menu_help_about", Icons.about, SWT.NONE, events.about)); //$NON-NLS-1$
	}

	/**
	 * Menubar of the Shell.
	 */
	private void menu() {
		shell.setMenuBar(new Menu(shell, SWT.BAR));

		fileMenu();
		editMenu();
		searchMenu();
		viewMenu();
		helpMenu();
	}

	/**
	 * Context menu of the text widget.
	 */
	private Menu popupMenu() {
		editPopup = MenuWidget.newMenu(shell, SWT.POP_UP, events.enableEditItems);

		widgets.add(
				MenuWidget.newMenuItem(editPopup, "text_menu_edit_undo", Icons.undo, SWT.CTRL + 'Z', events.undo)); //$NON-NLS-1$
		widgets.add(
				MenuWidget.newMenuItem(editPopup, "text_menu_edit_redo", Icons.redo, SWT.CTRL + 'Y', events.redo)); //$NON-NLS-1$

		new MenuItem(editPopup, SWT.SEPARATOR);

		widgets.add(MenuWidget.newMenuItem(editPopup, "text_menu_edit_cut", Icons.cut, SWT.CTRL + 'X', events.cut)); //$NON-NLS-1$
		widgets.add(
				MenuWidget.newMenuItem(editPopup, "text_menu_edit_copy", Icons.copy, SWT.CTRL + 'C', events.copy)); //$NON-NLS-1$
		widgets.add(MenuWidget.newMenuItem(editPopup, "text_menu_edit_paste", Icons.paste, SWT.CTRL + 'V', //$NON-NLS-1$
				events.paste));
		widgets.add(MenuWidget.newMenuItem(editPopup, "text_menu_edit_del", Icons.del, SWT.NONE, events.del)); //$NON-NLS-1$

		new MenuItem(editPopup, SWT.SEPARATOR);

		widgets.add(MenuWidget.newMenuItem(editPopup, "text_menu_edit_selall", null, SWT.CTRL + 'A', events.selAll)); //$NON-NLS-1$
		widgets.add(MenuWidget.newMenuItem(editPopup, "text_menu_edit_delall", null, SWT.NONE, events.delAll)); //$NON-NLS-1$

		return editPopup;
	}

	/**
	 * Search menu.
	 */
	private void searchMenu() {
		final var searchMenu = MenuWidget.newMenu(shell, SWT.DROP_DOWN, null);
		widgets.add(MenuWidget.newMenuName(shell.getMenuBar(), SWT.CASCADE, "text_menu_search", searchMenu)); //$NON-NLS-1$

		widgets.add(MenuWidget.newMenuItem(searchMenu, "text_menu_search_find", Icons.search, SWT.CTRL + 'F', //$NON-NLS-1$
				events.find));
	}

	/**
	 * @param styledText Set styledText.
	 */
	void setStyledText(final StyledText styledText) {
		this.styledText = styledText;
	}

	/**
	 * Initializing the shell.
	 */
	private void shell() {
		display = Display.getDefault();

		if (configData.isDarkMode()) {
			display.setData("org.eclipse.swt.internal.win32.useDarkModeExplorerTheme", Boolean.TRUE); //$NON-NLS-1$
			display.setData("org.eclipse.swt.internal.win32.useShellTitleColoring", Boolean.TRUE); //$NON-NLS-1$
			display.setData("org.eclipse.swt.internal.win32.menuBarForegroundColor", new Color(0xD0, 0xD0, 0xD0)); //$NON-NLS-1$
			display.setData("org.eclipse.swt.internal.win32.menuBarBackgroundColor", new Color(0x30, 0x30, 0x30)); //$NON-NLS-1$
			display.setData("org.eclipse.swt.internal.win32.all.use_WS_BORDER", Boolean.TRUE); //$NON-NLS-1$
			display.setData("org.eclipse.swt.internal.win32.Text.useDarkThemeIcons", Boolean.TRUE); //$NON-NLS-1$
		}

		shell = ShellWidget.newShell(display, SWT.SHELL_TRIM, "text", Icons.text, configData.getShellWidth(), //$NON-NLS-1$
				configData.getShellHeight(), Grid.newGridLayout(0, 0, 0, 0, 1, false), false, configData.isMaximize());
		widgets.add(shell);

		shell.addShellListener(events.shellFocus);
		shell.addShellListener(events.shellExit);

		if (!configData.isDarkMode()) {
			return;
		}

		shell.setBackground(new Color(0x30, 0x30, 0x30));
		shell.setForeground(new Color(0xD0, 0xD0, 0xD0));
		shell.setBackgroundMode(SWT.INHERIT_FORCE);
	}

	/**
	 * The status line of the shell.
	 */
	private void statusBar() {
		final var statusBar = ToolbarWidget.newToolBar(shell, Grid.newGridData(),
				Grid.newGridLayout(5, 0, 5, 0, 9, false));

		if (configData.isDarkMode()) {
			statusBar.setBackground(new Color(0x30, 0x30, 0x30));
			statusBar.setForeground(new Color(0xD0, 0xD0, 0xD0));
			statusBar.setBackgroundMode(SWT.INHERIT_FORCE);
		}

		var gridData = new GridData(SWT.FILL, SWT.CENTER, true, true);

		status1 = LabelWidget.newLabel(statusBar, gridData, null);

		LabelWidget.vLine(statusBar);

		gridData = Grid.newGridData(SWT.FILL, SWT.CENTER, false, true, 180, -1);

		status2 = LabelWidget.newLabel(statusBar, gridData, null);
		status2.setText("INS"); //$NON-NLS-1$

		LabelWidget.vLine(statusBar);

		status3 = LabelWidget.newLabel(statusBar, gridData, null);

		LabelWidget.vLine(statusBar);

		status4 = LabelWidget.newLabel(statusBar, gridData, null);

		LabelWidget.vLine(statusBar);

		status5 = LabelWidget.newLabel(statusBar, gridData, null);

		if (!configData.isDarkMode()) {
			return;
		}

		final var color = new Color(0xD0, 0xD0, 0xD0);
		status1.setForeground(color);
		status2.setForeground(color);
		status3.setForeground(color);
		status4.setForeground(color);
		status5.setForeground(color);
	}

	/**
	 * The toolbar of the shell.
	 */
	private void toolbar() {
		toolBar = ToolbarWidget.newToolBar(shell, Grid.newGridData(), null);

		if (configData.isDarkMode()) {
			toolBar.setBackground(new Color(0x30, 0x30, 0x30));
			toolBar.setForeground(new Color(0xD0, 0xD0, 0xD0));
			toolBar.setBackgroundMode(SWT.INHERIT_FORCE);
		}

		widgets.add(ToolbarWidget.newToolItem(toolBar, Icons.newfile, events.newFile, "text_toolbar_new")); //$NON-NLS-1$
		widgets.add(ToolbarWidget.newToolItem(toolBar, Icons.open, events.open, "text_toolbar_open")); //$NON-NLS-1$
		widgets.add(ToolbarWidget.newToolItem(toolBar, Icons.save, events.save, "text_toolbar_save")); //$NON-NLS-1$
		widgets.add(ToolbarWidget.newToolItem(toolBar, Icons.print, events.print, "text_toolbar_print")); //$NON-NLS-1$

		new ToolItem(toolBar, SWT.SEPARATOR);

		widgets.add(ToolbarWidget.newToolItem(toolBar, Icons.undo, events.undo, "text_toolbar_undo")); //$NON-NLS-1$
		widgets.add(ToolbarWidget.newToolItem(toolBar, Icons.redo, events.redo, "text_toolbar_redo")); //$NON-NLS-1$

		new ToolItem(toolBar, SWT.SEPARATOR);

		widgets.add(ToolbarWidget.newToolItem(toolBar, Icons.cut, events.cut, "text_toolbar_cut")); //$NON-NLS-1$
		widgets.add(ToolbarWidget.newToolItem(toolBar, Icons.copy, events.copy, "text_toolbar_copy")); //$NON-NLS-1$
		widgets.add(ToolbarWidget.newToolItem(toolBar, Icons.paste, events.paste, "text_toolbar_paste")); //$NON-NLS-1$
		widgets.add(ToolbarWidget.newToolItem(toolBar, Icons.del, events.del, "text_toolbar_del")); //$NON-NLS-1$

		new ToolItem(toolBar, SWT.SEPARATOR);

		widgets.add(ToolbarWidget.newToolItem(toolBar, Icons.search, events.find, "text_toolbar_find")); //$NON-NLS-1$

		new ToolItem(toolBar, SWT.SEPARATOR);

		widgets.add(ToolbarWidget.newToolItem(toolBar, Icons.about, events.about, "text_toolbar_about")); //$NON-NLS-1$
	}

	/**
	 * View menu.
	 */
	private void viewMenu() {
		final var viewMenu = MenuWidget.newMenu(shell, SWT.DROP_DOWN, null);
		widgets.add(MenuWidget.newMenuName(shell.getMenuBar(), SWT.CASCADE, "text_menu_view", viewMenu)); //$NON-NLS-1$

		widgets.add(MenuWidget.newMenuItemStyle(viewMenu, SWT.CHECK, "text_menu_view_wrap", SWT.CTRL + 'W', events.wrap, //$NON-NLS-1$
				configData.isWrap()));

		new MenuItem(viewMenu, SWT.SEPARATOR);

		widgets.add(
				MenuWidget.newMenuItem(viewMenu, "text_menu_view_font", null, SWT.CTRL + SWT.SHIFT + 'F', events.font)); //$NON-NLS-1$
		widgets.add(MenuWidget.newMenuItem(viewMenu, "text_menu_view_forecolor", null, SWT.NONE, events.foreColor)); //$NON-NLS-1$
		widgets.add(MenuWidget.newMenuItem(viewMenu, "text_menu_view_backcolor", null, SWT.NONE, events.backColor)); //$NON-NLS-1$
		widgets.add(MenuWidget.newMenuItem(viewMenu, "text_menu_view_selectforecolor", null, SWT.NONE, //$NON-NLS-1$
				events.selectForeColor));
		widgets.add(MenuWidget.newMenuItem(viewMenu, "text_menu_view_selectbackcolor", null, SWT.NONE, //$NON-NLS-1$
				events.selectBackColor));

		new MenuItem(viewMenu, SWT.SEPARATOR);

		final var encMenu = MenuWidget.newMenu(shell, SWT.DROP_DOWN, null);
		widgets.add(MenuWidget.newMenuName(viewMenu, SWT.CASCADE, "text_menu_view_enc", encMenu)); //$NON-NLS-1$

		final var encoding = CharacterEncoding.getEncoding();
		widgets.add(MenuWidget.newMenuItemStyle(encMenu, SWT.RADIO, "text_menu_view_enc_ascii", SWT.NONE, events.enc, //$NON-NLS-1$
				CharacterEncoding.ASCII.equals(encoding)));
		widgets.add(MenuWidget.newMenuItemStyle(encMenu, SWT.RADIO, "text_menu_view_enc_iso", SWT.NONE, events.enc, //$NON-NLS-1$
				CharacterEncoding.ISO.equals(encoding)));
		widgets.add(MenuWidget.newMenuItemStyle(encMenu, SWT.RADIO, "text_menu_view_enc_utf8", SWT.NONE, events.enc, //$NON-NLS-1$
				CharacterEncoding.UTF8.equals(encoding)));
		widgets.add(MenuWidget.newMenuItemStyle(encMenu, SWT.RADIO, "text_menu_view_enc_utf16be", SWT.NONE, events.enc, //$NON-NLS-1$
				CharacterEncoding.UTF16BE.equals(encoding)));
		widgets.add(MenuWidget.newMenuItemStyle(encMenu, SWT.RADIO, "text_menu_view_enc_utf16le", SWT.NONE, events.enc, //$NON-NLS-1$
				CharacterEncoding.UTF16LE.equals(encoding)));
		widgets.add(MenuWidget.newMenuItemStyle(encMenu, SWT.RADIO, "text_menu_view_enc_utf16", SWT.NONE, events.enc, //$NON-NLS-1$
				CharacterEncoding.UTF16.equals(encoding)));

		new MenuItem(viewMenu, SWT.SEPARATOR);

		final var langMenu = MenuWidget.newMenu(shell, SWT.DROP_DOWN, null);
		widgets.add(MenuWidget.newMenuName(viewMenu, SWT.CASCADE, "text_menu_view_lang", langMenu)); //$NON-NLS-1$

		final var lang = configData.getLanguage();
		widgets.add(MenuWidget.newMenuItemStyle(langMenu, SWT.RADIO, "text_menu_view_lang_en", SWT.NONE, events.lang, //$NON-NLS-1$
				"EN".equals(lang))); //$NON-NLS-1$
		widgets.add(MenuWidget.newMenuItemStyle(langMenu, SWT.RADIO, "text_menu_view_lang_de", SWT.NONE, events.lang, //$NON-NLS-1$
				"DE".equals(lang))); //$NON-NLS-1$
	}
}
