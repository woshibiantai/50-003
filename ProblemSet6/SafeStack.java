import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Stack;

/**
 * Created by woshibiantai on 16/3/17.
 */

class StackTesting {
    public static void main(String[] args) {
        SafeStack1 safeStack1 = new SafeStack1(6);
        SafeStack safeStack = new SafeStack(6);
        ArrayList<SafeStackTest> modifiedStack = new ArrayList<>();
        ArrayList<SafeStackTest1> originalStack = new ArrayList<>();

        System.out.println("SafeStack1");
        long startTime1 = System.nanoTime();

        for (int i = 0; i < 5; i++) {
            originalStack.add(new SafeStackTest1(safeStack1));
        }

        for (int i = 0; i < 5; i++) {
            originalStack.get(i).start();
        }

        for (int i = 0; i < 5; i++) {
            try {
                originalStack.get(i).join();
            } catch (InterruptedException e) {
                System.out.println("Some threads interrupted");
            }
        }

        long endTime1 = System.nanoTime();
        System.out.println("Time taken: " + (endTime1-startTime1));

        System.out.println("SafeStack");
        long startTime = System.nanoTime();

        for (int i = 0; i < 5; i++) {
            modifiedStack.add(new SafeStackTest(safeStack));
        }

        for (int i = 0; i < 5; i++) {
            modifiedStack.get(i).start();
        }

        for (int i = 0; i < 5; i++) {
            try {
                modifiedStack.get(i).join();
            } catch (InterruptedException e) {
                System.out.println("Some threads interrupted");
            }
        }

        long endTime = System.nanoTime();
        System.out.println("Time taken: " + (endTime-startTime));
    }
}

public class SafeStack<E> extends Stack<E> {
    int size;

    public SafeStack(int size) {
        this.size = size;
    }

    public synchronized void pushIfNotFull(E e) {
        if (this.size() >= size) {
            System.out.println("Stack full");
        } else {
            push(e);
        }
    }

    public synchronized E popIfNotEmpty() {
        try {
            return pop();
        } catch (EmptyStackException e) {
            System.out.println("Stack empty");
            return null;
        }
    }
}

class SafeStack1<E> extends Stack<E> {
    int size;
    private final Stack<E> s;

    public SafeStack1(int size) {
        this.size = size;
        this.s = new Stack<E>();
    }

    public void pushIfNotFull(E e) {
        synchronized (s) {
            if (s.size() >= size) {
                System.out.println("Stack full");
            } else {
                s.push(e);
            }
        }
    }

    public E popIfNotEmpty() {
        synchronized (s) {
            try {
                return s.pop();
            } catch (EmptyStackException e) {
                System.out.println("Stack empty");
                return null;
            }
        }
    }
}

class SafeStackTest extends Thread {
    SafeStack s;

    public SafeStackTest(SafeStack s) {
        this.s = s;
    }

    public void run() {
        for (int i = 0; i < 5; i++) {
            s.pushIfNotFull(1);
        }
        for (int i = 0; i < 5; i++) {
            s.popIfNotEmpty();
        }
    }
}

class SafeStackTest1 extends Thread {
    SafeStack1 s;

    public SafeStackTest1(SafeStack1 s) {
        this.s = s;
    }

    public void run() {
        for (int i = 0; i < 5; i++) {
            s.pushIfNotFull(1);
        }
        for (int i = 0; i < 5; i++) {
            s.popIfNotEmpty();
        }
    }
}

