public class BiSectionExample {
	public double root(double d, double e, double f) throws IllegalArgumentException {
		double middle;
		if (d >= e) {
			System.out.println("1");
			throw new IllegalArgumentException ("low must be lower than high");
		}

		System.out.println(d + " " + e + " " + f);
		while(e-d > f) {
			System.out.println("2");
//			System.out.println("once");
		    middle = (e + d) / 2;
            System.out.println("Middle: " + middle);
            if (middle < e) {
				System.out.println("3");
			   d = middle;			   
		   }
		   else {
				System.out.println("4");
			   e = middle;
		   }
	    }

		return (e + d) / 2;
	}
}
