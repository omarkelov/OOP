package ru.nsu.fit.markelov;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

/**
 * <code>StreamPatternMatcher</code> class provides a method
 * for searching all the occurrences in the <code>Reader</code>
 * char stream.
 * <p>
 * The search is produced by z-function, which takes O(m + n) char
 * comparisons, where m and n are the numbers of chars in stream
 * and string respectively.
 * <p>
 * <code>StreamPatternMatcher</code> uses own char buffer, that
 * default size is 16x[the length of pattern string] and minimal
 * size is 4x[the length of pattern string].
 *
 * @author Oleg Markelov
 * @see    Reader
 * @see    ZFunction
 */
public class StreamPatternMatcher {

    private char delimiter;
    private int bufferSize;

    /**
     * Creates a new <code>StreamPatternMatcher</code> with default
     * buffer size.
     *
     * @param delimiter any character that is not met in the stream
     *                  passed to <code>matchAll</code> method.
     */
    public StreamPatternMatcher(char delimiter) {
        this(delimiter, -1);
    }

    /**
     * Creates a new <code>StreamPatternMatcher</code>, given the
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
    public StreamPatternMatcher(char delimiter, int bufferSize) {
        this.delimiter = delimiter;
        this.bufferSize = bufferSize;
    }

    /**
     * Finds all the occurrences of the specified string in the stream.
     *
     * @param  charStream  stream of chars to match in.
     * @param  pattern     the string to look for.
     * @return             the array of indices (within whole stream) of every
     *                     occurrence of the specified string.
     * @throws IOException if the stream cannot be read.
     */
    public int[] matchAll(Reader charStream, String pattern) throws IOException {
        ArrayList<Integer> positionsList = new ArrayList<>();
        correctBufferSize(pattern.length());

        char[] buffer = new char[bufferSize];
        System.arraycopy(pattern.toCharArray(), 0, buffer, 0, pattern.length());
        buffer[pattern.length()] = delimiter;

        int prefixLen = pattern.length() + 1;
        int extraLen = pattern.length();
        int charsToRead = bufferSize - (prefixLen + extraLen);

        for (int i = 0; ; i++) {
            int charsRead = charStream.read(buffer, prefixLen + extraLen, charsToRead);

            if (charsRead == -1) { // end of stream
                break;
            }

            int[] zValues = ZFunction.getZValues(buffer);

            int charsEnd = prefixLen + extraLen + charsRead;
            for (int j = prefixLen; j < charsEnd; j++) {
                if (zValues[j] == pattern.length()) {
                    // position in the current buffer
                    int bufferPosition = j - prefixLen;
                    // position in the whole stream
                    int position = i * (buffer.length - prefixLen) + bufferPosition - (i+1) * extraLen;

                    // to avoid adding duplicates if it's found in the 'extra' zone
                    if (positionsList.size() == 0 || positionsList.get(positionsList.size() - 1) != position) {
                        positionsList.add(position);
                    }
                }
            }

            // moving the end of the buffer to the beginning (to avoid a match miss on the boarder)
            System.arraycopy(buffer, prefixLen + charsToRead, buffer, prefixLen, pattern.length());
        }

        return positionsList.stream().mapToInt(i->i).toArray();
    }

    /**
     * Sets the delimiter.
     */
    public void setDelimiter(char delimiter) {
        this.delimiter = delimiter;
    }

    /**
     * Sets the buffer size.
     */
    public void setBufferSize(int bufferSize) {
        this.bufferSize = bufferSize;
    }

    private void correctBufferSize(int patternSize) {
        if (bufferSize == -1) {
            bufferSize = 16 * patternSize;
        }

        if (bufferSize < 4 * patternSize) {
            bufferSize = 4 * patternSize;
        }
    }
}
