import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Test;

import org.jmock.Expectations;

public class TestWithMock {       
    @Test
    public void testCalculatingMachine() {
        Mockery context = new JUnit4Mockery();

        final CalculatingMachine.Printer printer = context.mock(CalculatingMachine.Printer.class);
        final CalculatingMachine.Calculator calculator = context.mock(CalculatingMachine.Calculator.class);

        context.checking(new Expectations() {{
            oneOf(calculator).add(1, 2);
            will(returnValue(3));
            oneOf(printer).print("result is 3");            
        }});

        CalculatingMachine machine = new CalculatingMachine(printer, calculator);
        machine.processAdd(1, 2);

        context.assertIsSatisfied();
    }
}