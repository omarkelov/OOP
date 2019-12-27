package ru.nsu.fit.markelov;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;
import java.util.stream.DoubleStream;

/**
 * Main class is used to generate html page with a content of several tables. Each table shows the
 * speedup of the program (searching of a prime number in array) that is executed on several
 * processor cores in comparison with execution on only one core.
 *
 * @author Oleg Markelov
 */
public class Main {

    private static final int THREADS_MIN = 2;
    private static final int THREADS_MAX = 24;
    private static final int SEEDS = 50;

    /**
     * Generates html page with a content of several tables. Each table shows the speedup of the
     * program (searching of a prime number in array) that is executed on several processor cores
     * in comparison with execution on only one core.
     *
     * @param args command line arguments.
     */
    public static void main(String[] args) {
        ArrayList<Integer> numbers;
        NonPrimeSearch nonPrimeSearch;
        ArrayList<Long> linearSearchTimeStamps;
        ArrayList<Long> streamSearchTimeStamps;
        ArrayList<ArrayList<Long>> threadPoolSearchTimeStampsList;

        long[][][] arr = new long[12][THREADS_MAX - THREADS_MIN + 4][11];

        for (int nPrimes = 1, i = 0; nPrimes <= 2048; nPrimes *= 2) {
            linearSearchTimeStamps = new ArrayList<>();
            streamSearchTimeStamps = new ArrayList<>();
            threadPoolSearchTimeStampsList = new ArrayList<>();
            for (int timeStampsCounter = 0; timeStampsCounter < THREADS_MAX - THREADS_MIN + 1; timeStampsCounter++) {
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

                for (int seed = 0; seed < SEEDS; seed++) {
                    numbers = PrimesArrayGenerator.generate(nPrimes, nNonPrimes, new Random(seed));

                    nonPrimeSearch = new LinearSearch(numbers);
                    nonPrimeSearch.execute();
                    linearSearchTimeStamps.add(nonPrimeSearch.getExecutionTime());

                    nonPrimeSearch = new StreamSearch(numbers);
                    nonPrimeSearch.execute();
                    streamSearchTimeStamps.add(nonPrimeSearch.getExecutionTime());

                    for (int nThreads = THREADS_MIN; nThreads <= THREADS_MAX; nThreads++) {
                        nonPrimeSearch = new ThreadPoolSearch(nThreads, numbers);
                        nonPrimeSearch.execute();
                        threadPoolSearchTimeStampsList.get(nThreads - THREADS_MIN).add(nonPrimeSearch.getExecutionTime());
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

        try (PrintWriter out = new PrintWriter("primes.html")) {
            out.println(buildHtml(arr));
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static long getAverageTime(ArrayList<Long> timeStamps) {
        return (long)timeStamps.stream()
                .flatMapToDouble(DoubleStream::of)
                .average()
                .orElse(Double.NaN);
    }

    private static void printArray(long[][][] arr) {
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

    private static String buildHtml(long[][][] arr) {
        DecimalFormat decimalFormat = new DecimalFormat("#.###");

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
//                        sb.append("<span class=\"gray\">100%</span>");
                        sb.append("<span>1</span>");
                    } else if (j != 0) {
                        /*int percent = getPercent(arr[i][1][k], arr[i][j][k]);
                        sb.append("<span class=\"");
                        sb.append(percent < 100 ? "green" : percent > 100 ? "red" : "yellow");
                        sb.append("\">");
                        if (percent < 10) {
                            sb.append("&nbsp;&nbsp;&nbsp;&nbsp;");
                        } else if (percent < 100) {
                            sb.append("&nbsp;&nbsp;");
                        }
                        sb.append(percent).append("%</span>");*/
                        sb.append("<span>").append(decimalFormat.format(getSpeedup(arr[i][1][k], arr[i][j][k]))).append("</span>");
                    }
                    //sb.append("<span>").append(arr[i][j][k]).append("</span>");
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

    private static int getPercent(long a, long b) {
        return (int) Math.round(((double) b) / ((double) a) * 100.0d);
    }

    private static Double getSpeedup(long a, long b) {
        return ((double) a) / ((double) b);
    }
}
