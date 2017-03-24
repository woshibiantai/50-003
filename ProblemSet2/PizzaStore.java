import java.util.Scanner;

public class PizzaStore {

	public static void main(String[] args) {
		PizzaKitchen dominos = new PizzaKitchen();
		Pizza pizzaOrder = null;

		System.out.println("Welcome to Dominoes! What pizza would you like to order?");
		System.out.println("(cheese, pepperoni or greek)");

		Scanner userInput = new Scanner(System.in);

		if (userInput.hasNextLine()){

			String typeOfPizza = userInput.nextLine();

			pizzaOrder = dominos.pizzaProcess(typeOfPizza);

			if(pizzaOrder != null){
				pizzaOrder.done();
			} else System.out.print("Please enter cheese, pepperoni or greek next time");

		}


	}
}

class PizzaKitchen {
	Pizza pizza = null;

	public Pizza pizzaProcess(String order) {
		if (order.equals("cheese")) {
			pizza = new CheesePizza();
		}
		if (order.equals("greek")) {
			pizza = new GreekPizza();
		}
		if (order.equals("pepperoni")) {
			pizza = new PepperoniPizza();
		}

		pizza.prepare();
		pizza.bake();
		pizza.cut();
		pizza.box();

		return pizza;
	}
}

class Pizza {

	public void prepare() {
		System.out.println("Preparing...");
	}

	public void box() {
		System.out.println("Boxing...");
	}

	public void cut() {
		System.out.println("Cutting...");
	}

	public void bake() {
		System.out.println("Baking...");
	}

	public void done() {
	};
}

class CheesePizza extends Pizza {
	@Override
	public void done() {
		super.done();
		System.out.println("Cheese pizza is done!");
	}
}
class GreekPizza extends Pizza {
	@Override
	public void done() {
		super.done();
		System.out.println("Greek pizza is done!");
	}
}
class PepperoniPizza extends Pizza {
	@Override
	public void done() {
		super.done();
		System.out.println("Pepperoni pizza is done!");
	}
}

