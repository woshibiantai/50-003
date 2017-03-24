import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import java.util.*;

import static org.testng.AssertJUnit.assertEquals;

@RunWith(Parameterized.class)

public class QuickSortTest {
    public int[] inputArr, outputArr;

    public QuickSortTest (int[] inputArr, int[] outputArr) {
        this.inputArr = inputArr;
        this.outputArr = outputArr;
    }

    @Parameters
    public static Collection<Object[]> parameters() {
        return Arrays.asList(new int [][][] {{{3,6,7,2,9,4},{2,3,4,6,7,9}},
                {{1,65,23,8,4,2},{1,2,4,8,23,65}}});
    }

    @Test public void quickSortTest() {
        QuickSort quickSort = new QuickSort();
        quickSort.sort(inputArr);
        System.out.println(Arrays.toString(quickSort.getArray()));
        assertEquals(Arrays.toString(outputArr), Arrays.toString(quickSort.getArray()));
    }
}
