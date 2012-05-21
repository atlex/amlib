package org.am.utils;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

/**
 * The StringConverter.
 *
 * @author Alexander Maximenya
 * @version 1.0
 */
public class StringConverter {
    /**
     * Windows cyrilic charset
     */
    public static final String CP1251 = "Cp1251";
    public static final String ISO_8859_1 = "ISO-8859-1";


    /**
     * Do not instantiate StringConverter.
     */
    private StringConverter() {
    }

    /**
     * Converts an input string to a string in other charset.
     * The default input charset is <b>ISO-8859-1</b>
     * The default output charset is <b>Cp1251</b>
     *
     * @param inputString an input string
     * @return            a string in charset <b>Cp1251</b>
     */
    public static String convert(String inputString)  {
        return convert(inputString, ISO_8859_1, CP1251);
    }

    /**
     * Converts an input string to a string in other charset.
     * The default output charset is <b>Cp1251</b>
     *
     * @param inputString     an input string
     * @param newInputCharset an input charset
     * @return                a string in charset <b>Cp1251</b>
     */
    public static String convert(String inputString, String newInputCharset)  {
        return convert(inputString, newInputCharset, CP1251);
    }

    /**
     * Converts an input string to a string in other charset.
     *
     * @param inputString     an input string
     * @param newInputCharset an input charset
     * @param newOutputString an output charset
     * @return                a string in output charset
     */
    public static String convert(String inputString, String newInputCharset, String newOutputString)  {
        Charset inCharset = Charset.forName(newInputCharset);
        Charset outCharset = Charset.forName(newOutputString);
        CharsetEncoder input = inCharset.newEncoder();
        CharsetDecoder output = outCharset.newDecoder();

        try {
            ByteBuffer bbuf = input.encode(CharBuffer.wrap(inputString));
            CharBuffer cbuf = output.decode(bbuf);
            return cbuf.toString();
        } catch (CharacterCodingException e) {
            return null;
        }
    }
}