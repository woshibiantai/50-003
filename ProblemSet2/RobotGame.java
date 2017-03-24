import java.util.Scanner;

class Robot {
	String name;
    public IBehaviour behaviour;

	public Robot (String name)
	{
		this.name = name;
	}

	public void behave ()
	{
        if (behaviour.moveCommand() == 1) {
            System.out.println(name + " is an aggressive robot :(");
        } else if (behaviour.moveCommand() == 2) {
            System.out.println(name + " is a defensive robot :O");
        } else if (behaviour.moveCommand() == 3) {
            System.out.println(name + " seems like a normal robot...");
        } else {
            System.out.println("Unidentified robot!");
        }
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void setBehavior() {
        Scanner userInput = new Scanner(System.in);
        String behaviourOption = "";

        System.out.println("What behaviour do you want " + name + " to have?");
        System.out.println("(aggressive/defensive/normal)");

        if (userInput.hasNextLine()){
            behaviourOption = userInput.nextLine();
        }

        if (behaviourOption.equals("aggressive")){
            behaviour = new Aggressive();
        } else if (behaviourOption.equals("defensive")){
            behaviour = new Defensive();
        } else if (behaviourOption.equals("normal")){
            behaviour = new Normal();
        } else {
            System.out.println(behaviourOption + " is not an available behaviour :(");
            setBehavior();
        }
	}
}

interface IBehaviour {
	public int moveCommand();
}

class Aggressive implements IBehaviour {
    public int moveCommand() {
        return 1;
    }
}

class Defensive implements IBehaviour {
    public int moveCommand() {
        return 2;
    }
}

class Normal implements IBehaviour {
    public int moveCommand() {
        return 3;
    }
}

public class RobotGame {

	public static void main(String[] args) {

		Robot r1 = new Robot("Big Robot");
		Robot r2 = new Robot("George v.2.1");
		Robot r3 = new Robot("R2");

		r1.setBehavior();
		r2.setBehavior();
		r3.setBehavior();
		
		r1.behave();
		r2.behave();
		r3.behave();

		//change the behaviors of each robot.
		r1.setBehavior();
		r2.setBehavior();
		r3.setBehavior();
		
		r1.behave();
		r2.behave();
		r3.behave();
	}
}