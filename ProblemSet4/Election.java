import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Election {

    Candidate candidateA;
    Candidate candidateB;
    ArrayList<Candidate> candidatesList = new ArrayList<>();
//    ArrayList<Electorate> electoratesList = new ArrayList<>();
    ElectionBoard electoratesList = new ElectionBoard();

    public static void main(String[] args) {
        Candidate candidateA = new Candidate("Candidate A");
        Candidate candidateB = new Candidate("Candidate B");

        int attendance = 5;
        BufferedReader input = null;
        PrintWriter output = null;
        ServerSocket server = null;
        Socket client = null;
        ArrayList<Socket> clientList = new ArrayList<>(attendance);
        ElectionBoard electoratesList = new ElectionBoard();

        try {
            server = new ServerSocket(5555);
            for (int i = 0; i < attendance; i++) {
                client = server.accept();
                clientList.add(client);

                electoratesList.add(new Electorate("Electorate" + i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        int votes = 0;
        while (votes < 5) {
            try {
                for (Socket c: clientList) {
                    input = new BufferedReader(
                            new InputStreamReader(c.getInputStream()));

                    String inputLine = input.readLine();
                    if (inputLine.equals("A")) {
                        String electorName = input.readLine();
                        Electorate elector = electoratesList.get(electorName);
                        elector.castVotes(candidateA);

                        votes++;
                    } else if (inputLine.equals("B")) {
                        String electorName = input.readLine();
                        Electorate elector = electoratesList.get(electorName);
                        elector.castVotes(candidateB);

                        votes++;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Election thisIsIt = new Election();
        thisIsIt.electionDay();
    }

    public void electionDay() {
        candidateA = new Candidate("Candidate A");
        candidateB = new Candidate("Candidate B");
        candidatesList.add(candidateA);
        candidatesList.add(candidateB);

        electoratesList.add(new Electorate("Electorate 1"));
        electoratesList.add(new Electorate("Electorate 2"));
        electoratesList.add(new Electorate("Electorate 3"));
        electoratesList.add(new Electorate("Electorate 4"));
        electoratesList.add(new Electorate("Electorate 5"));

//        electoratesList.forEach(this::votingProcess);
        electionResults();
    }

    public void electionResults() {
        for (Candidate candidates : candidatesList) {
            if (candidates.getVoteCount() > electoratesList.size()/2) {
                System.out.println(candidates.getName() + " has won the election with " + candidates.getVoteCount() + " votes. Congrats!");
            }
        }
    }

//    public void votingProcess(Electorate electorate) {
//            System.out.println("Hello " + electorate.getName() + ". Who would you like to vote for? (A or B)");
//            String selection = "";
//
//            Scanner userInput = new Scanner(System.in);
//
//            if (userInput.hasNextLine()) {
//                selection = userInput.nextLine();
//            }
//            if (selection.equals("A")) {
//                electorate.castVotes(candidateA);
//            } else if (selection.equals("B")) {
//                electorate.castVotes(candidateB);
//            } else {
//                System.out.println("Invalid output entered! Selected either A or B.");
//                votingProcess(electorate);
//            }
//    }
}


class Candidate {
    public int voteCount;
    public String name = "";

    public Candidate(String name) {
        voteCount = 0;
        this.name = name;
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

