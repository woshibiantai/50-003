import org.junit.Assert;
import org.junit.Test;

public class FindMaxTest {
    @Test
    public void MaxError () {
        FindMax max = new FindMax();
        int[] error = {};

        max.max(error);
    }

    @Test
    public void MaxFailure() {
        FindMax max = new FindMax();
        int[] error = {131,1231};
        Assert.assertEquals(1231,max.max(error));
    }

    @Test
    public void MaxPass() {
        FindMax max = new FindMax();
        int[] error = {131,1231,54545};
        Assert.assertEquals(1231,max.max(error));
    }
}
