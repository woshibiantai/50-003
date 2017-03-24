import java.util.ArrayList;

public class Electorate {
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

class ElectionBoard {
    ArrayList<Electorate> electorates = new ArrayList<>();
    public ElectionBoard() {};

    public void add(Electorate electorate) {
        electorates.add(electorate);
    }

    public Electorate get(String name) {
        for (Electorate electorate: electorates) {
            if (electorate.getName() == name) {
                return electorate;
            }
        }
        System.out.println("No such electorate");
        return null;
    }

    public int size() {
        return electorates.size();
    }
}