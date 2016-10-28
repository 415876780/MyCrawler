/*
 * Copyright (C) 2014 hu
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
 */
package ccnu.computer.net;

import org.mozilla.universalchardet.UniversalDetector;

/**
 * 字符集自动检�? * @author hu
 */
public class CharsetDetector {

    /**
     * 根据字节数组，猜测可能的字符集，如果�?��失败，返回utf-8
     * @param bytes 待检测的字节数组
     * @return 可能的字符集，如果检测失败，返回utf-8
     */
    public static String guessEncoding(byte[] bytes) {
        String DEFAULT_ENCODING = "UTF-8";
        UniversalDetector detector = new UniversalDetector(null);
        detector.handleData(bytes, 0, bytes.length);
        detector.dataEnd();
        String encoding = detector.getDetectedCharset();
        detector.reset();
        if (encoding == null) {
            encoding = DEFAULT_ENCODING;
        }
        return encoding;
    }
}
