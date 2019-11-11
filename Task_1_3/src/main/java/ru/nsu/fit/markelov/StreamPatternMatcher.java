package ru.nsu.fit.markelov;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;

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
 * size is 2x[the length of pattern string].
 *
 * @author Oleg Markelov
 * @see    Reader
 * @see    ZFunction
 */
public class StreamPatternMatcher {

    private char mDelimiter;
    private int mBufferSize;

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
        mDelimiter = delimiter;
        mBufferSize = bufferSize;
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
        int offset = pattern.length();
        int prefixLen = offset + 1;
        correctBufferSize(offset);
        int charsToRead = mBufferSize - offset;

        char[] chars = new char[offset + 1];
        System.arraycopy(pattern.toCharArray(), 0, chars, 0, pattern.length());
        chars[offset] = mDelimiter;

        char[] fileBuffer = new char[mBufferSize];
        for (int i = 0; ; i++) {
            int charsRead;

            try {
                charsRead = charStream.read(fileBuffer, offset, charsToRead);
            } catch (IOException e) {
                throw e;
            }

            if (charsRead == -1) { // end of stream
                break;
            }

            int strLen = charsRead + offset;
            chars = Arrays.copyOf(chars, prefixLen + strLen);
            System.arraycopy(fileBuffer, 0, chars, pattern.length() + 1, strLen);

            int[] zValues = ZFunction.getZValues(chars);

            for (int j = offset + 1; j < offset + 1 + strLen; j++) {
                if (zValues[j] == offset) {
                    int zValue = j - offset - 1;
                    int position = i * fileBuffer.length + zValue - (i+1) * offset;
                    if (positionsList.size() == 0 || positionsList.get(positionsList.size() - 1) != position) {
                        positionsList.add(position);
                    }
                }
            }

            System.arraycopy(fileBuffer, charsToRead, fileBuffer, 0, offset);
        }

        return positionsList.stream().mapToInt(i->i).toArray();
    }

    /**
     * Sets the delimiter.
     */
    public void setDelimiter(char delimiter) {
        mDelimiter = delimiter;
    }

    /**
     * Sets the buffer size.
     */
    public void setBufferSize(int bufferSize) {
        mBufferSize = bufferSize;
    }

    private void correctBufferSize(int patternSize) {
        if (mBufferSize == -1) {
            mBufferSize = 16 * patternSize;
        }

        if (mBufferSize < 2 * patternSize) {
            mBufferSize = 2 * patternSize;
        }
    }
}
