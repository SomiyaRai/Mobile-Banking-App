import java.util.LinkedList;
import javax.swing.JFrame;
 
public class Main {
    public static void main(String[] args) {

        
        String file = "Accounts.csv";  // Path to the CSV file containing account data
        ReadAccounts readAccounts = new ReadAccounts(file);  // Create an instance of ReadAccounts with the file path

        // Retrieve lists of first names, last names, account numbers, and balances from the CSV file
        LinkedList<String> firstNames = readAccounts.getFirstNames();
        LinkedList<String> lastNames = readAccounts.getLastNames();
        LinkedList<Integer> accountList = readAccounts.getAccounts();
        LinkedList<Integer> balanceList = readAccounts.getBalances();

        // Create a LinkedList of Account objects from the retrieved data
        LinkedList<Account> accounts = new LinkedList<>();
        for (int i = 0; i < firstNames.size(); i++) {
            accounts.add(new Account(firstNames.get(i), lastNames.get(i), accountList.get(i), balanceList.get(i)));
        }

        // Create and set up the GUI
        GUI gui = new GUI(accounts);  // Pass the list of accounts to the GUI
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Ensure the application exits when the window is closed
        gui.setSize(400, 500);  // Set the size of the GUI window
        gui.setVisible(true);    // Make the GUI window visible
    }
}
