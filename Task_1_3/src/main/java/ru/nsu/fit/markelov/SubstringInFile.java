package ru.nsu.fit.markelov;

import java.io.FileReader;
import java.io.IOException;

public class SubstringInFile {

    private int mBufferSize;
    private Stack<Integer> mPositions;

   /**
    * Creates a new SubstringInFile with default buffer size.
    */
    public SubstringInFile() {
        this(-1);
    }

   /**
    * Creates a new SubstringInFile, given the buffer size.
    *
    * @param bufferSize a size of the buffer, used for temporary storing the file parts
    */
    public SubstringInFile(int bufferSize) {
        mBufferSize = bufferSize;
        mPositions = new Stack<>();
    }

   /**
    * Finds all the occurrences of the specified string in file.
    * <p>
    * The file is being read in buffer as stream of characters
    * with standard FileReader. The search is produced by z-function,
    * which takes O(m + n) char comparisons, where m and n are the
    * numbers of chars in file and string respectively.
    * <p>
    * Default and minimal buffer size is 16x[the size of pattern
    * string].
    *
    * @param  fileName the name of the file to find in
    * @param  str      the string to look for
    * @return          the array of indices (within whole file) of every occurrence of the specified string. If the file cannot be opened null is returned.
    * @see             String
    * @see             FileReader
    */
    public int[] find(String fileName, String str) {
        if (mBufferSize == -1 || mBufferSize < 16 * str.length()) {
            mBufferSize = 16 * str.length();
        }

        char[] buffer = new char[mBufferSize];
        int offset = str.length();
        int charsToRead = mBufferSize - offset;

        try (FileReader fileReader = new FileReader(fileName)) {
            for (int i = 0; ; i++) {
                int charsRead = fileReader.read(buffer, offset, charsToRead);

                if (charsRead == -1) { // end of stream
                    break;
                }
//                print(buffer, charsRead + offset);

                ZFunction zFunction = new ZFunction(buffer, charsRead, '#', str.toCharArray());
                int[] positions = zFunction.getPositions();
                for (int j = 0; j < positions.length; j++) {
                    int position = i * buffer.length + positions[j] - (i+1) * offset;
                    if (mPositions.count() == 0 || (int) mPositions.getData()[mPositions.count() - 1] != position) {
                        mPositions.push(position);
                    }
                }

                System.arraycopy(buffer, charsToRead, buffer, 0, offset);
            }
        } catch (IOException e) {
            System.out.println(e.toString());
            return null;
        }

        int[] positions = new int[mPositions.count()];
        for (int i = 0; i < mPositions.count(); i++) {
            positions[i] = (int) mPositions.getData()[i];
        }

        return positions;
    }

    /*private void print(char[] buffer, int len) {
        for (int i = 0; i < len; i++) {
            System.out.print(buffer[i]);
        }
        System.out.println();
        System.out.println();
    }*/
}
