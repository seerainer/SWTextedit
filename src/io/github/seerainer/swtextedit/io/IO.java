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
package io.github.seerainer.swtextedit.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import org.eclipse.swt.custom.StyledText;

import io.github.seerainer.swtextedit.config.CharacterEncoding;

/**
 * Input / Output class.
 *
 * @author philipp@seerainer.com
 */
public final class IO {

	/**
	 * Method for opening a file into the text widget.
	 *
	 * @param file The file which will be converted into a String.
	 * @param text The String of the file loaded into this text widget.
	 * @return Returns the success of opening the file.
	 */
	public static boolean open(final File file, final StyledText text) {
		try (final var br = new BufferedReader(
				new InputStreamReader(new FileInputStream(file), CharacterEncoding.getEncoding()))) {
			final var buff = new StringBuilder();

			br.lines().forEach(line -> buff.append(line + "\n"));//$NON-NLS-1$

			text.setText(buff.toString());
			return true;
		} catch (final Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Method for saving the String into a file.
	 *
	 * @param file The file which will be saved.
	 * @param text The content of the file as String.
	 * @return Returns the success of saving the file.
	 */
	public static boolean save(final File file, final String text) {
		try (final var bw = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(file), CharacterEncoding.getEncoding()))) {
			bw.write(text);
			return true;
		} catch (final Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	/** Private empty constructor. */
	private IO() {
	}
}
