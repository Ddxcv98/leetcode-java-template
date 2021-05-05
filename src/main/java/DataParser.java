import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DataParser {
    private static final Pattern arrayPattern = Pattern.compile("(?<=[\\[,]).+?(?=[,\\]])");
    private static final Pattern matrixPattern = Pattern.compile("(?<=[\\[,])\\[.+?](?=[,\\]])");

    private final String[] lines;

    private int line;

    public DataParser(String name) throws IOException {
        var file = getClass().getResource(name).getFile();
        var br = new BufferedReader(new FileReader(file));

        lines = br
            .lines()
            .filter(s -> !s.isBlank() && !s.startsWith("#"))
            .map(String::trim)
            .toArray(String[]::new);

        br.close();
    }

    public DataParser(String[] lines) {
        this.lines = lines;
    }

    private String getLine() {
        return lines[line++];
    }

    private Stream<MatchResult> getArrayStream() {
        return arrayPattern
            .matcher(getLine())
            .results();
    }

    private Stream<Stream<MatchResult>> getMatrixStream() {
        return matrixPattern
            .matcher(getLine())
            .results()
            .map(r -> arrayPattern.matcher(r.group()).results());
    }

    public boolean hasNext() {
        return line != lines.length;
    }

    public String readString() {
        var line = getLine();
        return line.substring(1, line.length() - 1);
    }

    public boolean readBoolean() {
        return Boolean.parseBoolean(getLine());
    }

    public int readInt() {
        return Integer.parseInt(getLine());
    }

    public double readDouble() {
        return Double.parseDouble(getLine());
    }

    public String[] readStringArray() {
        return getArrayStream()
            .map(MatchResult::group)
            .toArray(String[]::new);
    }

    public int[] readIntArray() {
        return getArrayStream()
            .mapToInt(r -> Integer.parseInt(r.group()))
            .toArray();
    }

    public double[] readDoubleArray() {
        return getArrayStream()
            .mapToDouble(r -> Double.parseDouble(r.group()))
            .toArray();
    }

    public String[][] readStringMatrix() {
        return getMatrixStream()
            .map(row -> row.map(MatchResult::group).toArray(String[]::new))
            .toArray(String[][]::new);
    }
    
    public int[][] readIntMatrix() {
        return getMatrixStream()
            .map(row -> row.mapToInt(col -> Integer.parseInt(col.group())).toArray())
            .toArray(int[][]::new);
    }

    public double[][] readDoubleMatrix() {
        return getMatrixStream()
            .map(row -> row.mapToDouble(col -> Double.parseDouble(col.group())).toArray())
            .toArray(double[][]::new);
    }

    public List<String> readStringList() {
        return getArrayStream()
            .map(MatchResult::group)
            .collect(Collectors.toList());
    }
    
    public List<Integer> readIntList() {
        return getArrayStream()
            .map(r -> Integer.parseInt(r.group()))
            .collect(Collectors.toList());
    }

    public List<Double> readDoubleList() {
        return getArrayStream()
            .map(r -> Double.parseDouble(r.group()))
            .collect(Collectors.toList());
    }
}
