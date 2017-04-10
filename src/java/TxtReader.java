import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Ruslan on 26.03.2017.
 */
public class TxtReader {
    public void saveToFile(Iterable<String> stringArray, String pathName) {
        File file = new File(pathName);
        try {
            FileWriter fileWriter = new FileWriter(file);

            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            for (String s : stringArray) {
                bufferedWriter.write(s);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }

            bufferedWriter.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }



    public ArrayList<String> loadFile(String pathName) {
        File f = new File(pathName);
        System.out.println(f.canRead());
        ArrayList<String> output = new ArrayList<String>();
        try {
            Scanner scanner = new Scanner(f, "CP1251");

            while (scanner.hasNext()) {
                output.add(scanner.next());
            }

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }


        return output;
    }
}
