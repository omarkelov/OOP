package ru.nsu.fit.markelov;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class FilePatternMatcher extends StreamPatternMatcher {

    /**
     * Creates a new FilePatternMatcher with default buffer size.
     *
     * @param delimiter any character that is not met in the
     *                  stream passed to matchAll method.
     */
    public FilePatternMatcher(char delimiter) {
        super(delimiter);
    }

    /**
     * Creates a new FilePatternMatcher, given the buffer size.
     * <p>
     * Default buffer size is 16x[the length of pattern string].
     * Minimal buffer size is 2x[the length of pattern string].
     * If the size of the buffer under minimal is passed, minimal
     * buffer size is used.
     *
     * @param delimiter  any character that is not met in the
     *                   file passed to matchAll method.
     * @param bufferSize a size of the buffer, used for temporary
     *                   storing the file parts.
     */
    public FilePatternMatcher(char delimiter, int bufferSize) {
        super(delimiter, bufferSize);
    }

    /**
     * Finds all the occurrences of the specified string in the stream.
     * <p>
     * The search is produced by z-function, which takes O(m + n) char
     * comparisons, where m and n are the numbers of chars in stream
     * and string respectively.
     *
     * @param  fileName    the name of the file to be opened.
     * @param  pattern     the string to look for.
     * @return             the array of indices (within whole file) of every
     *                     occurrence of the specified string.
     * @throws IOException if the file cannot be read.
     * @see                String
     * @see                Reader
     */
    public int[] matchAll(String fileName, String pattern) throws IOException {
        try (FileReader fileReader = new FileReader(fileName)) {
            return matchAll(fileReader, pattern);
        } catch (IOException e) {
            throw e;
        }
    }
}
