import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)

public class ProgramTest {
    int p1, p2, n1, n2, z1, z2;
    MyProgram program = new MyProgram();

    public ProgramTest(int p1, int p2, int n1, int n2, int z1, int z2) {
        this.p1 = p1;
        this.p2 = p2;
        this.n1 = n1;
        this.n2 = n2;
        this.z1 = z1;
        this.z2 = z2;
    }

    @Parameters public static Collection<Object[]> parameters() {
        return Arrays.asList (new Object [][] {{5, 1, -40, -1, 0, 0}, {1, 1, -1, -1, 0, 0}});
    }

    @Test
    public void positiveTest() {
        assertEquals(p2, program.programMethod(p1));
    }

    @Test
    public void negativeTest() {
        assertEquals(n2, program.programMethod(n1));
    }

    @Test
    public void zeroTest() {
        assertEquals(z1, program.programMethod(z2));
    }
}
