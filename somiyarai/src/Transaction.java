class Transaction {
    // Method to transfer a specified amount from one account to another
    public void transfer(Account from, Account to, int amount) {
        from.withdraw(amount);  // Withdraws the amount from the 'from' account
        to.deposit(amount);     // Deposits the amount into the 'to' account
    }
}
