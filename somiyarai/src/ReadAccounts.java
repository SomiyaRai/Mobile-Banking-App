import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

public class ReadAccounts {
    private String url;

    // Constructor that sets the URL of the file to be read
    public ReadAccounts(String URL) {
        this.url = URL;
    }

    // Private method that reads a specific column from the CSV file and returns a list of strings
    private LinkedList<String> readColumn(int columnIndex) {
        LinkedList<String> list = new LinkedList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(url))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");  // Splits the line by commas
                if (values.length > columnIndex) {
                    list.add(values[columnIndex].trim());  // Adds the trimmed value to the list
                }
            }
        } catch (IOException e) {
            e.printStackTrace();  // Prints the stack trace if an IOException occurs
        }
        return list;
    }

    // Public method that returns a list of first names from the first column of the CSV
    public LinkedList<String> getFirstNames() {
        return readColumn(0);
    }

    // Public method that returns a list of last names from the second column of the CSV
    public LinkedList<String> getLastNames() {
        return readColumn(1);
    }

    // Public method that returns a list of account numbers from the third column of the CSV, converted to integers
    public LinkedList<Integer> getAccounts() {
        LinkedList<String> stringList = readColumn(2);
        LinkedList<Integer> intList = new LinkedList<>();
        for (String s : stringList) {
            intList.add(Integer.parseInt(s));  // Converts each string to an integer and adds it to the list
        }
        return intList;
    }

    // Public method that returns a list of balances from the fourth column of the CSV, converted to integers
    public LinkedList<Integer> getBalances() {
        LinkedList<String> stringList = readColumn(3);
        LinkedList<Integer> intList = new LinkedList<>();
        for (String s : stringList) {
            intList.add(Integer.parseInt(s));  // Converts each string to an integer and adds it to the list
        }
        return intList;
    }
}
