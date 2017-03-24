import java.util.ArrayList;
import java.util.Scanner;

public class HomeworkQuestion3 {

    CandidateA candidateA;
    CandidateB candidateB;
    ArrayList<Candidate> candidatesList = new ArrayList<>();
    ArrayList<Electorate> electoratesList = new ArrayList<>();

    public static void main(String[] args) {
        HomeworkQuestion3 thisIsIt = new HomeworkQuestion3();
        thisIsIt.electionDay();
    }

    public void electionDay() {
        candidateA = new CandidateA();
        candidateB = new CandidateB();
        candidatesList.add(candidateA);
        candidatesList.add(candidateB);

        electoratesList.add(new Electorate("Electorate 1"));
        electoratesList.add(new Electorate("Electorate 2"));
        electoratesList.add(new Electorate("Electorate 3"));
        electoratesList.add(new Electorate("Electorate 4"));
        electoratesList.add(new Electorate("Electorate 5"));

        electoratesList.forEach(this::votingProcess);
        electionResults();
    }

    public void electionResults() {
        for (Candidate candidates : candidatesList) {
            if (candidates.getVoteCount() > electoratesList.size()/2) {
                System.out.println(candidates.getName() + " has won the election with " + candidates.getVoteCount() + " votes. Congrats!");
            }
        }
    }

    public void votingProcess(Electorate electorate) {
            System.out.println("Hello " + electorate.getName() + ". Who would you like to vote for? (A or B)");
            String selection = "";

            Scanner userInput = new Scanner(System.in);

            if (userInput.hasNextLine()) {
                selection = userInput.nextLine();
            }
            if (selection.equals("A")) {
                electorate.castVotes(candidateA);
            } else if (selection.equals("B")) {
                electorate.castVotes(candidateB);
            } else {
                System.out.println("Invalid output entered! Selected either A or B.");
                votingProcess(electorate);
            }
    }
}

class Candidate {
    public int voteCount;
    public String name = "";

    public Candidate() {
        voteCount = 0;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void vote() {
        voteCount++;
        System.out.println("Thank you for voting for " + name);
    }

    public int getVoteCount() {
        return voteCount;
    }
}

class CandidateA extends Candidate {
    public CandidateA() {
        this.setName("Candidate A");
    }
}

class CandidateB extends Candidate {
    CandidateB() {
        this.setName("Candidate B");
    }
}

class Electorate {
    private String name;

    public Electorate(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void castVotes(Candidate selectedCandidate) {
        selectedCandidate.vote();
        System.out.println(name + " has voted for " + selectedCandidate.getName());
        System.out.println(selectedCandidate.getName() + " now has " + selectedCandidate.getVoteCount() + " votes.");
        System.out.println();
    }

}