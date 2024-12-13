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

import static org.eclipse.swt.events.KeyListener.keyPressedAdapter;
import static org.eclipse.swt.events.KeyListener.keyReleasedAdapter;
import static org.eclipse.swt.events.MenuListener.menuShownAdapter;
import static org.eclipse.swt.events.MouseListener.mouseDownAdapter;
import static org.eclipse.swt.events.MouseListener.mouseUpAdapter;
import static org.eclipse.swt.events.SelectionListener.widgetSelectedAdapter;
import static org.eclipse.swt.events.ShellListener.shellActivatedAdapter;
import static org.eclipse.swt.events.ShellListener.shellClosedAdapter;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ExtendedModifyListener;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MenuListener;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.ShellListener;
import org.eclipse.swt.widgets.MenuItem;

import io.github.seerainer.swtextedit.config.CharacterEncoding;
import io.github.seerainer.swtextedit.dialog.About;
import io.github.seerainer.swtextedit.dialog.FindReplace;
import io.github.seerainer.swtextedit.dialog.SystemProperties;
import io.github.seerainer.swtextedit.io.IO;
import io.github.seerainer.swtextedit.util.ItemUtil;
import io.github.seerainer.swtextedit.util.LangUtil;
import io.github.seerainer.swtextedit.util.PrintUtil;
import io.github.seerainer.swtextedit.util.StatusBarUtil;
import io.github.seerainer.swtextedit.util.StringUtil;
import io.github.seerainer.swtextedit.util.TextUtil;
import io.github.seerainer.swtextedit.util.UndoUtil;
import io.github.seerainer.swtextedit.widgets.FileDialogWidget;
import io.github.seerainer.swtextedit.widgets.FontDialogWidget;

/**
 * Creating the UI events.
 *
 * @author philipp@seerainer.com
 */
public final class Events {

	private Widgets widgets;

	/** Instance of the Find/Replace Dialog. */
	private FindReplace findReplace;

	/** Instances for the undo / redo function. */
	private final List<UndoUtil> undoStack = new LinkedList<>();

	/**
	 * Instances for the undo / redo function.
	 */
	private final List<UndoUtil> redoStack = new LinkedList<>();

	/** Listener for the undo / redo function. */
	ExtendedModifyListener undoredo = e -> {
		redoStack.clear();
		final var stackSize = widgets.getConfigData().getUndoStackSize();
		final var newText = widgets.getStyledText().getText().substring(e.start, e.start + e.length);
		final var newTextValue = !StringUtil.isValueEmpty(newText);
		if (e.replacedText.length() > 0) {
			if (undoStack.size() == stackSize) {
				undoStack.remove(undoStack.size() - 1);
			}
			if (newTextValue) {
				undoStack.add(0, new UndoUtil(e.replacedText, e.replacedText.length() > 0,
						widgets.getStyledText().getCaretOffset() - newText.length()));
				if (undoStack.size() == stackSize) {
					undoStack.remove(undoStack.size() - 1);
				}
				undoStack.add(0, new UndoUtil(newText, false, widgets.getStyledText().getCaretOffset()));
			} else {
				undoStack.add(0, new UndoUtil(e.replacedText, e.replacedText.length() > 0,
						widgets.getStyledText().getCaretOffset()));
			}
		} else if (newTextValue) {
			if (undoStack.size() == stackSize) {
				undoStack.remove(undoStack.size() - 1);
			}
			undoStack.add(0, new UndoUtil(newText, false, widgets.getStyledText().getCaretOffset()));
		}
	};

	/** Listener if a key is pressed. */
	KeyListener keyPressed = keyPressedAdapter(e -> {
		ItemUtil.enableMenuItems(widgets.getEditMenu(), widgets.getToolBar(), widgets.getConfigData(),
				widgets.getStyledText(), undoStack, redoStack);
		StatusBarUtil.status(widgets.getStatus1(), widgets.getStatus2(), widgets.getStatus3(), widgets.getStatus4(),
				widgets.getStatus5(), e.keyCode, widgets.getStyledText(), widgets.getConfigData().getFilename());
	});

	/** Listener if a key is released. */
	KeyListener keyReleased = keyReleasedAdapter(e -> {
		ItemUtil.enableMenuItems(widgets.getEditMenu(), widgets.getToolBar(), widgets.getConfigData(),
				widgets.getStyledText(), undoStack, redoStack);
		StatusBarUtil.status(widgets.getStatus1(), widgets.getStatus2(), widgets.getStatus3(), widgets.getStatus4(),
				widgets.getStatus5(), 0, widgets.getStyledText(), widgets.getConfigData().getFilename());
	});

	/** Listener for the file menu to enable or disable. */
	MenuListener enableSaveItem = menuShownAdapter(
			e -> widgets.getFileMenu().getItem(2).setEnabled(widgets.getConfigData().isHasChanged()));

	/** Listener for the edit menu to enable or disable. */
	MenuListener enableEditItems = menuShownAdapter(e -> {
		ItemUtil.enableMenuItems(widgets.getEditMenu(), widgets.getToolBar(), widgets.getConfigData(),
				widgets.getStyledText(), undoStack, redoStack);
		ItemUtil.enableMenuItems(widgets.getEditPopup(), widgets.getToolBar(), widgets.getConfigData(),
				widgets.getStyledText(), undoStack, redoStack);
	});

	/** Listener for modifying the text. */
	ModifyListener textChanged = e -> {
		if (!widgets.getConfigData().isHasChanged()) {
			widgets.getConfigData().setHasChanged(true);
			widgets.getFileMenu().getItem(2).setEnabled(true);
		}
	};

	/** Listener if a mouse key is pressed. */
	MouseListener mousePressed = mouseDownAdapter(e -> {
		ItemUtil.enableMenuItems(widgets.getEditMenu(), widgets.getToolBar(), widgets.getConfigData(),
				widgets.getStyledText(), undoStack, redoStack);
		StatusBarUtil.status(widgets.getStatus1(), widgets.getStatus2(), widgets.getStatus3(), widgets.getStatus4(),
				widgets.getStatus5(), 0, widgets.getStyledText(), widgets.getConfigData().getFilename());
	});

	/** Listener if a mouse key is released. */
	MouseListener mouseReleased = mouseUpAdapter(e -> {
		ItemUtil.enableMenuItems(widgets.getEditMenu(), widgets.getToolBar(), widgets.getConfigData(),
				widgets.getStyledText(), undoStack, redoStack);
		StatusBarUtil.status(widgets.getStatus1(), widgets.getStatus2(), widgets.getStatus3(), widgets.getStatus4(),
				widgets.getStatus5(), 0, widgets.getStyledText(), widgets.getConfigData().getFilename());
	});

	/** Listener for a new textfile. */
	SelectionListener newFile = widgetSelectedAdapter(e -> {
		if (widgets.getConfigData().isHasChanged()) {
			final var state = FileDialogWidget.saveYesNoCancel(widgets.getShell(), widgets.getConfigData());

			if (state == SWT.YES) {
				if (FileDialogWidget.fileDialog(widgets.getShell(), widgets.getConfigData(), SWT.SAVE,
						widgets.getStyledText())) {
					clearData(true);
				}
			} else if (state == SWT.NO) {
				clearData(true);
			}
		} else {
			clearData(true);
		}
		ItemUtil.enableMenuItems(widgets.getEditMenu(), widgets.getToolBar(), widgets.getConfigData(),
				widgets.getStyledText(), undoStack, redoStack);
		StatusBarUtil.status(widgets.getStatus1(), widgets.getStatus2(), widgets.getStatus3(), widgets.getStatus4(),
				widgets.getStatus5(), 0, widgets.getStyledText(), widgets.getConfigData().getFilename());
	});

	/** Listener for opening a file. */
	SelectionListener open = widgetSelectedAdapter(e -> {
		if (widgets.getConfigData().isHasChanged()) {
			final var state = FileDialogWidget.saveYesNoCancel(widgets.getShell(), widgets.getConfigData());

			if (state == SWT.YES) {
				if (FileDialogWidget.fileDialog(widgets.getShell(), widgets.getConfigData(), SWT.SAVE,
						widgets.getStyledText())
						&& FileDialogWidget.fileDialog(widgets.getShell(), widgets.getConfigData(), SWT.OPEN,
								widgets.getStyledText())) {
					clearData(false);
				}
			} else if ((state == SWT.NO) && FileDialogWidget.fileDialog(widgets.getShell(), widgets.getConfigData(),
					SWT.OPEN, widgets.getStyledText())) {
				clearData(false);
			}
		} else if (FileDialogWidget.fileDialog(widgets.getShell(), widgets.getConfigData(), SWT.OPEN,
				widgets.getStyledText())) {
			clearData(false);
		}
		ItemUtil.enableMenuItems(widgets.getEditMenu(), widgets.getToolBar(), widgets.getConfigData(),
				widgets.getStyledText(), undoStack, redoStack);
		StatusBarUtil.status(widgets.getStatus1(), widgets.getStatus2(), widgets.getStatus3(), widgets.getStatus4(),
				widgets.getStatus5(), 0, widgets.getStyledText(), widgets.getConfigData().getFilename());
	});

	/** Listener for saving a file. */
	SelectionListener save = widgetSelectedAdapter(e -> {
		if (StringUtil.isValueEmpty(widgets.getConfigData().getFilename())) {
			if (FileDialogWidget.fileDialog(widgets.getShell(), widgets.getConfigData(), SWT.SAVE,
					widgets.getStyledText())) {
				widgets.getConfigData().setHasChanged(false);
			}
		} else if (IO.save(new File(widgets.getConfigData().getFilename()), widgets.getStyledText().getText())) {
			widgets.getConfigData().setHasChanged(false);
		}
		ItemUtil.enableMenuItems(widgets.getEditMenu(), widgets.getToolBar(), widgets.getConfigData(),
				widgets.getStyledText(), undoStack, redoStack);
		StatusBarUtil.status(widgets.getStatus1(), widgets.getStatus2(), widgets.getStatus3(), widgets.getStatus4(),
				widgets.getStatus5(), 0, widgets.getStyledText(), widgets.getConfigData().getFilename());
	});

	/** Listener for saving a file as. */
	SelectionListener saveas = widgetSelectedAdapter(e -> {
		if (FileDialogWidget.fileDialog(widgets.getShell(), widgets.getConfigData(), SWT.SAVE, widgets.getStyledText())) {
			widgets.getConfigData().setHasChanged(false);
		}
		ItemUtil.enableMenuItems(widgets.getEditMenu(), widgets.getToolBar(), widgets.getConfigData(),
				widgets.getStyledText(), undoStack, redoStack);
		StatusBarUtil.status(widgets.getStatus1(), widgets.getStatus2(), widgets.getStatus3(), widgets.getStatus4(),
				widgets.getStatus5(), 0, widgets.getStyledText(), widgets.getConfigData().getFilename());
	});

	/** Listener for printing the textfile. */
	SelectionListener print = widgetSelectedAdapter(
			e -> PrintUtil.printDialog(widgets.getShell(), widgets.getStyledText()));

	/** Listener for the exit button. */
	SelectionListener exit = widgetSelectedAdapter(e -> widgets.getShell().close());

	/** Listener for the undo event of the text widget. */
	SelectionListener undo = widgetSelectedAdapter(e -> {
		widgets.getStyledText().removeExtendedModifyListener(undoredo);
		UndoUtil.undo(undoStack, redoStack, widgets.getStyledText());
		widgets.getStyledText().addExtendedModifyListener(undoredo);
		ItemUtil.enableMenuItems(widgets.getEditMenu(), widgets.getToolBar(), widgets.getConfigData(),
				widgets.getStyledText(), undoStack, redoStack);
		StatusBarUtil.status(widgets.getStatus1(), widgets.getStatus2(), widgets.getStatus3(), widgets.getStatus4(),
				widgets.getStatus5(), 0, widgets.getStyledText(), widgets.getConfigData().getFilename());
	});

	/** Listener for the redo event of the text widget. */
	SelectionListener redo = widgetSelectedAdapter(e -> {
		widgets.getStyledText().removeExtendedModifyListener(undoredo);
		UndoUtil.redo(redoStack, undoStack, widgets.getStyledText());
		widgets.getStyledText().addExtendedModifyListener(undoredo);
		ItemUtil.enableMenuItems(widgets.getEditMenu(), widgets.getToolBar(), widgets.getConfigData(),
				widgets.getStyledText(), undoStack, redoStack);
		StatusBarUtil.status(widgets.getStatus1(), widgets.getStatus2(), widgets.getStatus3(), widgets.getStatus4(),
				widgets.getStatus5(), 0, widgets.getStyledText(), widgets.getConfigData().getFilename());
	});

	/** Listener for the cut event of the text widget. */
	SelectionListener cut = widgetSelectedAdapter(e -> {
		widgets.getStyledText().cut();
		ItemUtil.enableMenuItems(widgets.getEditMenu(), widgets.getToolBar(), widgets.getConfigData(),
				widgets.getStyledText(), undoStack, redoStack);
		StatusBarUtil.status(widgets.getStatus1(), widgets.getStatus2(), widgets.getStatus3(), widgets.getStatus4(),
				widgets.getStatus5(), 0, widgets.getStyledText(), widgets.getConfigData().getFilename());
	});

	/** Listener for the copy event of the text widget. */
	SelectionListener copy = widgetSelectedAdapter(e -> {
		widgets.getStyledText().copy();
		ItemUtil.enableMenuItems(widgets.getEditMenu(), widgets.getToolBar(), widgets.getConfigData(),
				widgets.getStyledText(), undoStack, redoStack);
	});

	/** Listener for the paste event of the text widget. */
	SelectionListener paste = widgetSelectedAdapter(e -> {
		widgets.getStyledText().paste();
		ItemUtil.enableMenuItems(widgets.getEditMenu(), widgets.getToolBar(), widgets.getConfigData(),
				widgets.getStyledText(), undoStack, redoStack);
		StatusBarUtil.status(widgets.getStatus1(), widgets.getStatus2(), widgets.getStatus3(), widgets.getStatus4(),
				widgets.getStatus5(), 0, widgets.getStyledText(), widgets.getConfigData().getFilename());
	});

	/** Listener for the delete event of the text widget. */
	SelectionListener del = widgetSelectedAdapter(e -> {
		widgets.getStyledText().insert(""); //$NON-NLS-1$
		ItemUtil.enableMenuItems(widgets.getEditMenu(), widgets.getToolBar(), widgets.getConfigData(),
				widgets.getStyledText(), undoStack, redoStack);
		StatusBarUtil.status(widgets.getStatus1(), widgets.getStatus2(), widgets.getStatus3(), widgets.getStatus4(),
				widgets.getStatus5(), 0, widgets.getStyledText(), widgets.getConfigData().getFilename());
	});

	/** Listener for the select all event of the text widget. */
	SelectionListener selAll = widgetSelectedAdapter(e -> {
		widgets.getStyledText().selectAll();
		ItemUtil.enableMenuItems(widgets.getEditMenu(), widgets.getToolBar(), widgets.getConfigData(),
				widgets.getStyledText(), undoStack, redoStack);
		StatusBarUtil.status(widgets.getStatus1(), widgets.getStatus2(), widgets.getStatus3(), widgets.getStatus4(),
				widgets.getStatus5(), 0, widgets.getStyledText(), widgets.getConfigData().getFilename());
	});

	/** Listener for the delete all event of the text widget. */
	SelectionListener delAll = widgetSelectedAdapter(e -> {
		widgets.getStyledText().setText(""); //$NON-NLS-1$
		ItemUtil.enableMenuItems(widgets.getEditMenu(), widgets.getToolBar(), widgets.getConfigData(),
				widgets.getStyledText(), undoStack, redoStack);
		StatusBarUtil.status(widgets.getStatus1(), widgets.getStatus2(), widgets.getStatus3(), widgets.getStatus4(),
				widgets.getStatus5(), 0, widgets.getStyledText(), widgets.getConfigData().getFilename());
	});

	/** Listener for converting the text to uppercase. */
	SelectionListener uppercase = widgetSelectedAdapter(e -> {
		widgets.setStyledText(StringUtil.uppercase(widgets.getStyledText()));
		ItemUtil.enableMenuItems(widgets.getEditMenu(), widgets.getToolBar(), widgets.getConfigData(),
				widgets.getStyledText(), undoStack, redoStack);
	});

	/** Listener for converting the text to lowercase. */
	SelectionListener lowercase = widgetSelectedAdapter(e -> {
		widgets.setStyledText(StringUtil.lowercase(widgets.getStyledText()));
		ItemUtil.enableMenuItems(widgets.getEditMenu(), widgets.getToolBar(), widgets.getConfigData(),
				widgets.getStyledText(), undoStack, redoStack);
	});

	/** Listener for trimming leading and trailing whitespace. */
	SelectionListener trim = widgetSelectedAdapter(e -> {
		widgets.setStyledText(StringUtil.trim(widgets.getStyledText()));
		ItemUtil.enableMenuItems(widgets.getEditMenu(), widgets.getToolBar(), widgets.getConfigData(),
				widgets.getStyledText(), undoStack, redoStack);
	});

	/** Listener for find / replace dialog. */
	SelectionListener find = widgetSelectedAdapter(e -> {
		if (findReplace == null || findReplace.isWidgetDisposed()) {
			findReplace = new FindReplace(widgets.getShell(), widgets.getConfigData(), widgets.getStyledText(),
					widgets.getEditMenu());
		}
		findReplace.forceActive();
	});

	/** Listener for the wrap style of the styledtext widget. */
	SelectionListener wrap = widgetSelectedAdapter(
			e -> TextUtil.wrap(widgets.getConfigData(), widgets.getStyledText()));

	/** Listener for the font of the styledtext widget. */
	SelectionListener font = widgetSelectedAdapter(
			e -> FontDialogWidget.font(widgets.getShell(), widgets.getStyledText(), widgets.getConfigData()));

	/** Listener for the background color of the styledtext widget. */
	SelectionListener backColor = widgetSelectedAdapter(
			e -> FontDialogWidget.backColor(widgets.getShell(), widgets.getStyledText(), widgets.getConfigData()));

	/** Listener for the foreground color of the styledtext widget. */
	SelectionListener foreColor = widgetSelectedAdapter(
			e -> FontDialogWidget.foreColor(widgets.getShell(), widgets.getStyledText(), widgets.getConfigData()));

	/** Listener for the selection background color of the styledtext widget. */
	SelectionListener selectBackColor = widgetSelectedAdapter(
			e -> FontDialogWidget.selectBackColor(widgets.getShell(), widgets.getStyledText(), widgets.getConfigData()));

	/** Listener for the selection foreground color of the styledtext widget. */
	SelectionListener selectForeColor = widgetSelectedAdapter(
			e -> FontDialogWidget.selectForeColor(widgets.getShell(), widgets.getStyledText(), widgets.getConfigData()));

	/** Listener for the encoding of the text. */
	SelectionListener enc = widgetSelectedAdapter(e -> {
		final var enc1 = ((MenuItem) e.getSource()).getText();
		if (!CharacterEncoding.getEncoding().equals(enc1)) {
			CharacterEncoding.setEncoding(enc1);
		}
	});

	/** Listener for the language of the program. */
	SelectionListener lang = widgetSelectedAdapter(e -> {
		var lang1 = (String) e.widget.getData("TEXTID"); //$NON-NLS-1$
		lang1 = lang1.substring(lang1.length() - 2).toUpperCase();
		if (!widgets.getConfigData().getLanguage().equals(lang1)) {
			widgets.getConfigData().setLanguage(lang1);
			LangUtil.setLang(widgets.getWidgets(), widgets.getConfigData());
		}
	});

	/** Listener for the system info message box. */
	SelectionListener systemconfig = widgetSelectedAdapter(
			e -> new SystemProperties(widgets.getShell(), widgets.getConfigData()));

	/** Listener for the about dialog. */
	SelectionListener about = widgetSelectedAdapter(e -> new About(widgets.getShell(), widgets.getConfigData()));

	/** Listener for the selection of the text widget. */
	SelectionListener selectText = widgetSelectedAdapter(e -> {
		ItemUtil.enableMenuItems(widgets.getEditMenu(), widgets.getToolBar(), widgets.getConfigData(),
				widgets.getStyledText(), undoStack, redoStack);
		StatusBarUtil.status(widgets.getStatus1(), widgets.getStatus2(), widgets.getStatus3(), widgets.getStatus4(),
				widgets.getStatus5(), 0, widgets.getStyledText(), widgets.getConfigData().getFilename());
	});

	/** Listener if the shell gets the focus. */
	ShellListener shellFocus = shellActivatedAdapter(e -> {
		ItemUtil.enableMenuItems(widgets.getEditMenu(), widgets.getToolBar(), widgets.getConfigData(),
				widgets.getStyledText(), undoStack, redoStack);
		widgets.getFileMenu().getItem(2).setEnabled(widgets.getConfigData().isHasChanged());
		StatusBarUtil.status(widgets.getStatus1(), widgets.getStatus2(), widgets.getStatus3(), widgets.getStatus4(),
				widgets.getStatus5(), 0, widgets.getStyledText(), widgets.getConfigData().getFilename());
	});

	/** Listener for closing the shell. */
	ShellListener shellExit = shellClosedAdapter(e -> {
		if (widgets.getConfigData().isHasChanged()) {
			e.doit = switch (FileDialogWidget.saveYesNoCancel(widgets.getShell(), widgets.getConfigData())) {
			case SWT.YES -> FileDialogWidget.fileDialog(widgets.getShell(), widgets.getConfigData(), SWT.SAVE,
					widgets.getStyledText());
			case SWT.NO -> true;
			default -> false;
			};
		} else {
			e.doit = true;
		}

	});

	/**
	 * Default Constructor of Events.
	 *
	 * @param widgets Instance of Widgets.
	 */
	Events(final Widgets widgets) {
		this.widgets = widgets;
	}

	/**
	 * Reset all values for a new file.
	 *
	 * @param untitled True for a new untitled text file.
	 */
	private void clearData(final boolean untitled) {
		if (untitled) {
			widgets.getStyledText().setText(""); //$NON-NLS-1$
			widgets.getConfigData().setFilename(null);
		}
		widgets.getConfigData().setHasChanged(false);
		undoStack.clear();
		redoStack.clear();
	}
}
