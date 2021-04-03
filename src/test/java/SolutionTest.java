import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class SolutionTest {
    private static final Solution solution = new Solution();

    private static DataParser t;
    private static DataParser o;

    @BeforeAll
    static void beforeAll() throws IOException {
        t = new DataParser("testcases.txt");
        o = new DataParser("outputs.txt");
    }

    @Test
    void auto() {
        var n = 0;

        while (o.hasNext()) {
            if (++n == -1) {
                System.out.println("Debug time");
            }

            /*var expected = ...;
            var actual = ...;
            assertEquals(expected, actual, "Test " + n);*/
        }

        System.out.println(n + (n == 1 ? " test" : " tests") + " completed");
    }

    @Test
    void manual() {

    }

    @Test
    void other() {

    }
}
