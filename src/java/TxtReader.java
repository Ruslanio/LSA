import java.io.*;
import java.util.ArrayList;

/**
 * Created by Ruslan on 26.03.2017.
 */
public class TxtReader {
    private static final String DOCS_SEPARATOR = "=-=";

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
        StringBuilder stringBuilder = new StringBuilder();
        int docCount = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains(DOCS_SEPARATOR)) {
                    output.add(docCount, stringBuilder.toString());
                    stringBuilder.delete(0, stringBuilder.length());
                    docCount++;
                } else {
                    if (!line.equals("")) {
                        stringBuilder.append(line).append(" ");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output;
    }
}
