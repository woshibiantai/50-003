/**
 * Created by woshibiantai on 26/1/17.
 */
public class ComplexNumbers {

    double real, imaginary, r, teta;
    boolean polar;

    public ComplexNumbers() {};

    public ComplexNumbers(double x, double y, boolean p) {
        polar = p;
        if (polar) {
            r = x;
            teta = y;
        } else {
            real = x;
            imaginary = y;
        }
    }

    public String rectangularForm(double real, double imaginary) {
        return "(" + real + ") + (" + imaginary + ")i";
    }

    public String polarForm(double r, double t) {
        return r + "(cos(" + t + ") + isin(" + t + "))";
    }

    public String addition(ComplexNumbers A, ComplexNumbers B) {
        double x, y;
        if (A.polar) {
            x = (A.r * Math.cos(A.teta)) + (B.r * Math.cos(B.teta));
            y = (A.r * Math.sin(A.teta)) + (B.r * Math.sin(B.teta));
            double new_r = Math.sqrt((x*x) + (y*y));
            double new_teta = Math.atan2(x,y);
            return polarForm(new_r,new_teta);
        } else {
            x = A.real + B.real;
            y = A.imaginary + B.imaginary;
            return rectangularForm(x,y);
        }
    }

    public String subtraction(ComplexNumbers A, ComplexNumbers B) {
        double x, y;
        if (A.polar) {
            x = (A.r * Math.cos(A.teta)) - (B.r * Math.cos(B.teta));
            y = (A.r * Math.sin(A.teta)) - (B.r * Math.sin(B.teta));
            double new_r = Math.sqrt((x*x) + (y*y));
            double new_teta = Math.atan2(x,y);
            return polarForm(new_r,new_teta);
        } else {
            x = A.real - B.real;
            y = A.imaginary - B.imaginary;
            return rectangularForm(x,y);
        }
    }

    public String multiplication(ComplexNumbers A, ComplexNumbers B) {
        double x, y;
        if (A.polar) {
            x = A.r * B.r;
            y = (A.teta + B.teta) % (Math.PI * 2);
            return polarForm(x,y);
        } else {
            double A_r = Math.sqrt((A.real*A.real) + (A.imaginary*A.imaginary));
            double A_teta = Math.atan2(A.real,A.imaginary);
            double B_r = Math.sqrt((B.real*B.real) + (B.imaginary*B.imaginary));
            double B_teta = Math.atan2(B.real,B.imaginary);
            x = A_r * B_r;
            y = (A_teta + B_teta) % (Math.PI * 2);
            return rectangularForm(x,y);
        }
    }

    public String division(ComplexNumbers A, ComplexNumbers B) {
        double x, y;
        if (A.polar) {
            x = A.r / B.r;
            y = (A.teta - B.teta) % (Math.PI * 2);
            return polarForm(x,y);
        } else {
            double A_r = Math.sqrt((A.real*A.real) + (A.imaginary*A.imaginary));
            double A_teta = Math.atan2(A.real,A.imaginary);
            double B_r = Math.sqrt((B.real*B.real) + (B.imaginary*B.imaginary));
            double B_teta = Math.atan2(B.real,B.imaginary);
            x = A_r / B_r;
            y = (A_teta - B_teta) % (Math.PI * 2);
            return rectangularForm(x,y);
        }
    }


    public static void main(String[] args) {
        ComplexNumbers result = new ComplexNumbers();
        System.out.println(result.addition(new ComplexNumbers(3,Math.PI/2,true), new ComplexNumbers(1,Math.PI/4,true)));
        System.out.println(result.addition(new ComplexNumbers(3,5,false), new ComplexNumbers(6,7,false)));
        System.out.println(result.subtraction(new ComplexNumbers(3,Math.PI/2,true), new ComplexNumbers(1,Math.PI/4,true)));
        System.out.println(result.subtraction(new ComplexNumbers(3,5,false), new ComplexNumbers(6,7,false)));
        System.out.println(result.multiplication(new ComplexNumbers(3,Math.PI/2,true), new ComplexNumbers(1,Math.PI/4,true)));
        System.out.println(result.multiplication(new ComplexNumbers(3,5,false), new ComplexNumbers(6,7,false)));
        System.out.println(result.division(new ComplexNumbers(3,Math.PI/2,true), new ComplexNumbers(1,Math.PI/4,true)));
        System.out.println(result.division(new ComplexNumbers(3,5,false), new ComplexNumbers(6,7,false)));
    }
}
