package ru.nsu.fit.markelov;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

/**
 * <code>FilePatternMatcher</code> class provides a method
 * for searching all the occurrences in the <code>FileReader</code>
 * char stream created with specified file name.
 * <p>
 * <code>FilePatternMatcher</code> extends <code>StreamPatternMatcher</code>.
 * <p>
 * The search is produced by z-function, which takes O(m + n) char
 * comparisons, where m and n are the numbers of chars in stream
 * and string respectively.
 * <p>
 * <code>FilePatternMatcher</code> uses own char buffer, that
 * default size is 16x[the length of pattern string] and minimal
 * size is 4x[the length of pattern string].
 *
 * @author Oleg Markelov
 * @see    StreamPatternMatcher
 * @see    ZFunction
 */
public class FilePatternMatcher extends StreamPatternMatcher {

    /**
     * Creates a new <code>FilePatternMatcher</code> with default
     * buffer size.
     *
     * @param delimiter any character that is not met in the stream
     *                  passed to <code>matchAll</code> method.
     */
    public FilePatternMatcher(char delimiter) {
        super(delimiter);
    }

    /**
     * Creates a new <code>FilePatternMatcher</code>, given the
     * buffer size.
     * <p>
     * If the size of the buffer under minimal is passed, minimal
     * buffer size is used.
     *
     * @param delimiter  any character that is not met in the stream
     *                   passed to <code>matchAll</code> method.
     * @param bufferSize a size of the buffer, used for temporary
     *                   storing the stream parts.
     */
    public FilePatternMatcher(char delimiter, int bufferSize) {
        super(delimiter, bufferSize);
    }

    /**
     * Finds all the occurrences of the specified string in the file.
     *
     * @param  fileName    the name of the file to be opened.
     * @param  pattern     the string to look for.
     * @return             the array of indices (within whole file) of every
     *                     occurrence of the specified string.
     * @throws IOException if the file cannot be read.
     */
    public int[] matchAll(String fileName, String pattern) throws IOException {
        try (FileReader fileReader = new FileReader(fileName)) {
            return matchAll(fileReader, pattern);
        } catch (IOException e) {
            throw e;
        }
    }
}
