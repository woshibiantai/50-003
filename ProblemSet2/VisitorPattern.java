import java.util.ArrayList;

public class VisitorPattern {
    public static void main(String[] args) {
        SUTD oneSUTD = new SUTD ();

        ArrayList<Employee> employee = oneSUTD.getEmployees();

        SUTDvisitor visitor = new SUTDvisitor();

        //auditing
        for (Employee employee1 : employee) {
            employee1.accept(visitor);
        }

    }
}

class SUTD {
    private ArrayList<Employee> employees;

    public SUTD() {
        employees = new ArrayList<Employee>();
        employees.add(new Professor("Sun Jun", 0));
        employees.add(new AdminStaff("Stacey", 5));
        employees.add(new Student("Allan", 3));
    }

    public ArrayList<Employee> getEmployees() {
        return employees;
    }
}

interface Employee {
    void accept(Visitor visitor);
}

class Professor implements Employee {
    private String name;
    private int no_of_publications;

    public Professor (String name, int no_of_publications) {
        this.name = name;
        this.no_of_publications = no_of_publications;
    }

    public String getName() {
        return name;
    }

    public int getNo_of_publications() {
        return no_of_publications;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}

class AdminStaff implements Employee {
    private String name;
    private float efficiency;

    public AdminStaff(String name, float efficiency) {
        this.name = name;
        this.efficiency = efficiency;
    }

    public String getName() {
        return name;
    }

    public float getEfficiency() {
        return efficiency;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}

class Student implements Employee {
    private String name;
    private float GPA;

    public Student(String name, float GPA) {
        this.name = name;
        this.GPA = GPA;
    }

    public String getName() {
        return name;
    }

    public float getGPA() {
        return GPA;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}

interface Visitor {
    void visit(Professor professor);
    void visit(AdminStaff adminStaff);
    void visit(Student student);
}

class SUTDvisitor implements Visitor {

    @Override
    public void visit(Professor professor) {
        System.out.println("Prof: " + professor.getName());
        System.out.println("No. of publications: " + professor.getNo_of_publications());
    }

    @Override
    public void visit(AdminStaff adminStaff) {
        System.out.println("Admin Staff: " + adminStaff.getName());
        System.out.println("Efficiency: " + adminStaff.getEfficiency());
    }

    @Override
    public void visit(Student student) {
        System.out.println("Student: " + student.getName());
        System.out.println("GPA: " + student.getGPA());
    }
}