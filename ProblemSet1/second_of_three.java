import java.util.Arrays;

/**
 * Created by woshibiantai on 26/1/17.
 */
public class second_of_three {

    public int second_of_three(int x, int y, int z) {
        int[] sorted = {x,y,z};
        Arrays.sort(sorted);
        return sorted[1];
    }

    public int second_of_four(int w, int x, int y, int z) {
        int[] sorted = {w,x,y,z};
        Arrays.sort(sorted);
        return sorted[2];
    }

    public static void main(String[] args) {
        second_of_three test = new second_of_three();

        System.out.println(test.second_of_three(6,3,9));
        System.out.println(test.second_of_four(6,3,9,50));
    }
}

