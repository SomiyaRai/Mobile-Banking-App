public class Account extends Customer {
    private int balance;         // Stores the account balance
    private int accountNumber;   // Stores the account number
    private String firstName;    // Stores the first name (overrides the inherited firstName)
    private String lastName;     // Stores the last name (overrides the inherited lastName)

    // Constructor to initialize the account with first name, last name, account number, and balance
    public Account(String firstName, String lastName, int accountNum, int balance) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.accountNumber = accountNum;
        this.balance = balance;
    }

    // Getter method for the first name
    @Override
    public String getFirstName() {
        return firstName;
    }

    // Getter method for the last name
    @Override
    public String getLastName() {
        return lastName;
    }

    // Getter method for the account number
    public int getAccountNum() {
        return accountNumber;
    }

    // Getter method for the account balance
    public int getBalance() {
        return balance;
    }

    // Method to deposit an amount into the account
    public void deposit(int amount) {
        balance += amount;
    }

    // Method to withdraw an amount from the account
    public void withdraw(int amount) {
        balance -= amount;
    }
}