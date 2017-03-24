import org.jmock.Expectations;
import org.junit.Assert;
import org.junit.Test;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;

public class TestSortingWithMock {
    @Test
    public void findMaxTest() {
        Mockery context = new JUnit4Mockery();

        int[] inputArr = {3,5,2,6,7,2,4};
        int[] outputArr = {2,2,3,4,5,6,7};

        final Sorter sorter = context.mock(Sorter.class);

        context.checking(new Expectations() {{
            oneOf(sorter).sort(inputArr);
            will(returnValue(outputArr));
        }});

        FindMaxUsingSorting findMaxUsingSorting = new FindMaxUsingSorting();
        int output = findMaxUsingSorting.findmax(inputArr,sorter);
        Assert.assertEquals(output,7);
        System.out.println(output);
        context.assertIsSatisfied();
    }
}
