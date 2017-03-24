import java.io.*;
import java.util.ArrayList;

public class HomeworkQuestion4 {
    ArrayList<File> fileArrayList = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        HomeworkQuestion4 homeworkQuestion4 = new HomeworkQuestion4();
        homeworkQuestion4.addFiles("week03/src/LoremIpsum.txt");
        homeworkQuestion4.addFiles("week03/src/LoremIpsumLonger.txt");
        homeworkQuestion4.countFiles();
    }

    public void addFiles(String filePath) {
        File file = new File(filePath);
        fileArrayList.add(file);
    }

    public int lineCounter(File file) throws IOException {
        int lines = 0;
        FileInputStream fileInputStream = new FileInputStream(file);

        try {
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            while (bufferedReader.readLine() != null) {
                lines++;
            } bufferedReader.close();

        } catch (IOException e) {
            System.out.println(e);
        }

        return lines;
    }

    public void countFiles() throws IOException {
        for (File files : fileArrayList) {
            System.out.println(files.getName() + " has " + lineCounter(files) + " lines.");
        }
    }

}
