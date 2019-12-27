package ru.nsu.fit.markelov;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Random;
import java.util.stream.DoubleStream;

public class PrimesTest {

    @Test
    public void testSingle() {
//        if (true) return;

        ArrayList<Integer> numbers = PrimesArrayGenerator.generate(1000, 1, new Random(7));
        NonPrimeSearch nonPrimeSearch;

        // -----------------------------------------------------------------------------------------

        nonPrimeSearch = new LinearSearch(numbers);
        nonPrimeSearch.execute();
        System.out.println(nonPrimeSearch.getExecutionTime() + " - " + nonPrimeSearch.getType());
        Assert.assertTrue(nonPrimeSearch.isNonPrimeFound());

        // -----------------------------------------------------------------------------------------

        nonPrimeSearch = new ThreadPoolSearch(4, numbers);
        nonPrimeSearch.execute();
        System.out.println(nonPrimeSearch.getExecutionTime() + " - " + nonPrimeSearch.getType());
        Assert.assertTrue(nonPrimeSearch.isNonPrimeFound());

        // -----------------------------------------------------------------------------------------

        nonPrimeSearch = new StreamSearch(numbers);
        nonPrimeSearch.execute();
        System.out.println(nonPrimeSearch.getExecutionTime() + " - " + nonPrimeSearch.getType());
        Assert.assertTrue(nonPrimeSearch.isNonPrimeFound());
    }

    @Test
    public void testMultiple() {
        if (true) return;

        ArrayList<Integer> numbers;
        NonPrimeSearch nonPrimeSearch;
        ArrayList<Long> linearSearchTimeStamps;
        ArrayList<Long> streamSearchTimeStamps;
        ArrayList<ArrayList<Long>> threadPoolSearchTimeStampsList;

        long[][][] arr = new long[12][18][11];

        for (int nPrimes = 1, i = 0; nPrimes <= 2048; nPrimes *= 2) {
            linearSearchTimeStamps = new ArrayList<>();
            streamSearchTimeStamps = new ArrayList<>();
            threadPoolSearchTimeStampsList = new ArrayList<>(/*Collections.nCopies(15, new ArrayList<>())*/);
            for (int timeStampsCounter = 0; timeStampsCounter < 15; timeStampsCounter++) {
                threadPoolSearchTimeStampsList.add(new ArrayList<>());
            }

            for (int nonPrimesPerMille = 0, j = 0; nonPrimesPerMille <= 512; nonPrimesPerMille *= 2) {
                int nNonPrimes;
                if (j == 0) {
                    nonPrimesPerMille = 1;
                    nNonPrimes = 0;
                } else {
                    nNonPrimes = (int) Math.ceil((double) nPrimes * (double) nonPrimesPerMille / 1000.0d);
                    if (j > 1 && arr[i][0][j-1] == nNonPrimes) {
                        continue;
                    }
                }

                for (int seed = 0; seed < 50; seed++) {
                    numbers = PrimesArrayGenerator.generate(nPrimes, nNonPrimes, new Random(seed));

                    nonPrimeSearch = new LinearSearch(numbers);
                    nonPrimeSearch.execute();
                    linearSearchTimeStamps.add(nonPrimeSearch.getExecutionTime());
                    if (nNonPrimes == 0) {
                        Assert.assertFalse(nonPrimeSearch.isNonPrimeFound());
                    } else {
                        Assert.assertTrue(nonPrimeSearch.isNonPrimeFound());
                    }

                    nonPrimeSearch = new StreamSearch(numbers);
                    nonPrimeSearch.execute();
                    streamSearchTimeStamps.add(nonPrimeSearch.getExecutionTime());
                    if (nNonPrimes == 0) {
                        Assert.assertFalse(nonPrimeSearch.isNonPrimeFound());
                    } else {
                        Assert.assertTrue(nonPrimeSearch.isNonPrimeFound());
                    }

                    for (int nThreads = 2; nThreads <= 16; nThreads++) {
                        nonPrimeSearch = new ThreadPoolSearch(nThreads, numbers);
                        nonPrimeSearch.execute();
                        threadPoolSearchTimeStampsList.get(nThreads - 2).add(nonPrimeSearch.getExecutionTime());
                        if (nNonPrimes == 0) {
                            Assert.assertFalse(nonPrimeSearch.isNonPrimeFound());
                        } else {
                            Assert.assertTrue(nonPrimeSearch.isNonPrimeFound());
                        }
                    }
                }

                arr[i][0][j] = nNonPrimes;
                arr[i][1][j] = getAverageTime(linearSearchTimeStamps);
                arr[i][2][j] = getAverageTime(streamSearchTimeStamps);
                for (int timeStampsCounter = 0; timeStampsCounter < threadPoolSearchTimeStampsList.size(); timeStampsCounter++) {
                    arr[i][timeStampsCounter+3][j] = getAverageTime(threadPoolSearchTimeStampsList.get(timeStampsCounter));
                }

                j++;
            }

            i++;
        }

        printArray(arr);
        System.out.println(buildHtml(arr));
    }

    private long getAverageTime(ArrayList<Long> timeStamps) {
        return (long)timeStamps.stream()
                .flatMapToDouble(DoubleStream::of)
                .average()
                .orElse(Double.NaN);
    }

    private void printArray(long[][][] arr) {
        for (int nPrimes = 1, i = 0; nPrimes <= 2048; nPrimes *= 2) {
            System.out.println("nPrimes: " + nPrimes);
            for (int j = 0; j < arr[i].length; j++) {
                for (int k = 0; k < arr[i][j].length; k++) {
                    System.out.print(arr[i][j][k] + ", ");
                }
                System.out.println();
            }
            System.out.println();
            i++;
        }
    }

    private String buildHtml(long[][][] arr) {
        StringBuilder sb = new StringBuilder("<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "<title>Primes</title>\n" +
                "<style>\n" +
                "td {text-align: right; border: 1px solid black;}\n" +
                "span {float:left; margin-right: 5px; padding: 0 5px;}\n" +
                ".gray {background: #999999;}\n" +
                ".green {background: #66FF00;}\n" +
                ".red {background: #FF3300;}\n" +
                ".yellow {background: #FFCC33;}\n" +
                "</style>\n" +
                "</head>\n" +
                "<body>\n");

        for (int nPrimes = 1, i = 0; nPrimes <= 2048; nPrimes *= 2) {
            sb.append("<h2>nPrimes: ").append(nPrimes).append("</h2>\n");
            sb.append("<table>\n");
            for (int j = 0; j < arr[i].length; j++) {
                sb.append("<tr>");
                sb.append("<th>");
                if (j == 0) {
                    sb.append("nNonPrimes");
                } else if (j == 1) {
                    sb.append("Linear");
                } else if (j == 2) {
                    sb.append("Stream");
                } else {
                    sb.append("ThreadPool(").append(j-1).append(")");
                }

                sb.append("</th>");
                for (int k = 0; k < arr[i][j].length; k++) {
                    if (k > 1 && arr[i][j][k] == 0) {
                        break;
                    }

                    sb.append((j == 0) ? "<th>" : "<td>");
                    if (j == 1) {
                        sb.append("<span class=\"gray\">100%</span>");
                    } else if (j != 0) {
                        int percent = getPercent(arr[i][1][k], arr[i][j][k]);
                        sb.append("<span class=\"");
                        sb.append(percent < 100 ? "green" : percent > 100 ? "red" : "yellow");
                        sb.append("\">").append(percent < 100 ? "&nbsp;&nbsp;" : "").append(percent).append("%</span>");
                    }
                    sb.append(arr[i][j][k]);
                    sb.append((j == 0) ? "</th>" : "</td>");
                }
                sb.append("</tr>\n");
            }
            sb.append("</table>\n");
            i++;
        }

        sb.append("</body>\n").append("</html>");

        return sb.toString();
    }

    private int getPercent(long a, long b) {
        return (int) Math.round(((double) b) / ((double) a) * 100.0d);
    }
}
