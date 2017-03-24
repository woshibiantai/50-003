import org.junit.Test;

public class RussianTest {
//    Black box test:
    @Test
    public void multiplyTestNull() {
        Integer a = null;
        Integer b = null;
        int result = Russian.multiply(a,b);
    }

//    White box test:
    @Test
    public void multiplyTest() {
        int result = Russian.multiply(1,2);
        assert (result == 2);
    }

    @Test
    public void multiplyTestZero() {
        int result = Russian.multiply(0,0);
        assert (result == 0);
    }

//    Fault based test:
    @Test
    public void multiplyTestNegative() {
        int result = Russian.multiply(1,-2);
        assert (result == -2);
    }
}
