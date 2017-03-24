public class MyProgram {
    public int programMethod(int input) {
        if (input > 0 && input < 10000) {
            return 1;
        } else if (input < 0 && input >-10000) {
            return -1;
        } else {
            return 0;
        }
    }
}
