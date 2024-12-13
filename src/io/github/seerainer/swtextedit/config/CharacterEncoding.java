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

import java.nio.charset.Charset;

/**
 * All available character encoding names.
 *
 * @author philipp@seerainer.com
 */
public final class CharacterEncoding {

	public static final String ASCII = "US-ASCII"; //$NON-NLS-1$

	public static final String ISO = "ISO-8859-1"; //$NON-NLS-1$

	public static final String UTF8 = "UTF-8"; //$NON-NLS-1$

	public static final String UTF16BE = "UTF-16BE"; //$NON-NLS-1$

	public static final String UTF16LE = "UTF-16LE"; //$NON-NLS-1$

	public static final String UTF16 = "UTF-16"; //$NON-NLS-1$

	/**
	 * @return Return encoding.
	 */
	public static String getEncoding() {
		return Charset.defaultCharset().displayName();
	}

	/**
	 * @param encoding Set encoding.
	 */
	public static void setEncoding(final String encoding) {
		System.setProperty("file.encoding", encoding); //$NON-NLS-1$
	}
}
