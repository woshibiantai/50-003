import org.junit.Before;
import org.junit.Test;

public class BiSectionTest {
	private BiSectionExample bi;
	
	@Before 
	public void runBeforeEachTest()
	{
		bi = new BiSectionExample();
	}
	
	@Test
	public void test4MethodCoverage () {
        double result = bi.root(0.5, 100.3, 0.1);
		assert (result >= 100);
        System.out.print(result);
		//question: should we assert the returned value is the exact value we expect?
	}
	
	@Test 
	public void test4LoopCoverage1 () {//loop once
        double result = bi.root(0,100,80);
        assert (result > 50);
        System.out.print(result);
	}

	@Test
    public void test4StatementCoverage1() {
        double result = bi.root(10,0,8);
        assert (result > 5);
        System.out.print(result);
    }

    @Test
    public void test4StatementCoverage2() {
        double result = bi.root(0,10,8);
        assert (result > 5);
        System.out.print(result);
    }

    @Test
    public void test4StatementCoverage3() {
        double result = bi.root(1,2,0);
        assert (result > 1);
        System.out.print(result);
    }
}
