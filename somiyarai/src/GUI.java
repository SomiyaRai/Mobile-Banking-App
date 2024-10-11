import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.LinkedList;

public class GUI extends JFrame {
	private Transaction transferObject; // Object for handling transfers
	private LinkedList<Account> globalAccounts; // List of all accounts
	private JTextField accDeposit, depositInput, accWithdraw, withdrawInput, acc2Transfer, transferAmount; // Text fields for transactions
	private JTextField loginNameField, loginAccNumField, transferRecipientName; // Text fields for login and transfer
    private JLabel showAllData, userNameLabel, accountNumberLabel, balanceLabel; // Labels for displaying account info
	private JButton showAllButton, depositButton, withdrawButton, transferButton, loginButton; // Buttons for main actions
	private JButton confirmTransferButton, confirmWithdrawButton, confirmDepositButton, exitButton, backButton; // Buttons for confirmations and navigation
	private JButton transferShowAllButton, withdrawShowAllButton, depositShowAllButton; // Buttons to show all accounts in different panels
	private JButton transferBackButton, withdrawBackButton, depositBackButton; // Back buttons for different panels
	private JButton transferExitButton, withdrawExitButton, depositExitButton; // Exit buttons for different panels
    private JLabel transferShowAllData, withdrawShowAllData, depositShowAllData; // Labels to show all accounts in different panels
    private StringBuilder sbAllData; // StringBuilder, used to store all account data
    private JPanel mainPanel, loginPanel, transferPanel, withdrawPanel, depositPanel; // These are the different panels used in GUI
    private JPanel currentPanel, previousPanel; // Keeps track of the current and previous panels (back button)
    private JTextField fromAccTransfer;  


    public GUI(LinkedList<Account> accounts) {
        super("Banking System"); // Sets the window title
        this.globalAccounts = accounts; // Initializes the accounts list
        this.transferObject = new Transaction(); // Creates the Transaction object
        this.sbAllData = new StringBuilder(); // Initializes the StringBuilder
        for (Account acc : globalAccounts) {
            sbAllData.append("Account: ").append(acc.getAccountNum()).append(", Name: ").append(acc.getFirstName()).append(" ").append(acc.getLastName())
            .append(", Balance: ").append(acc.getBalance()).append("\n");
        }

        // Creates the main panel with null layout
        mainPanel = new JPanel(null);
        mainPanel.setPreferredSize(new Dimension(400, 600));
        mainPanel.setBackground(Color.decode("#EDF2FA"));

        // Profile header
        JLabel profileHeader = new JLabel("My Accounts", SwingConstants.CENTER);
        profileHeader.setFont(new Font("Yu Gothic", Font.BOLD, 20));
        profileHeader.setForeground(Color.BLACK);
        profileHeader.setBounds(100, 40, 200, 30);
        mainPanel.add(profileHeader);

        // User name
        userNameLabel = new JLabel("", SwingConstants.CENTER);
        userNameLabel.setFont(new Font("Yu Gothic", Font.BOLD, 20));
        userNameLabel.setForeground(Color.BLACK);
        userNameLabel.setBounds(100, 60, 200, 30);
        mainPanel.add(userNameLabel);

        // Account number
        accountNumberLabel = new JLabel("", SwingConstants.CENTER);
        accountNumberLabel.setFont(new Font("Yu Gothic", Font.BOLD, 16));
        accountNumberLabel.setForeground(Color.BLACK);
        accountNumberLabel.setBounds(100, 100, 200, 30);
        mainPanel.add(accountNumberLabel);

        // Balance
        balanceLabel = new JLabel("", SwingConstants.CENTER);
        balanceLabel.setFont(new Font("Yu Gothic", Font.BOLD, 16));
        balanceLabel.setForeground(Color.BLACK);
        balanceLabel.setBounds(100, 140, 200, 30);
        mainPanel.add(balanceLabel);

        // Instruction label
        JLabel instructionLabel = new JLabel("<html>Would you like to transfer, withdraw<br>or deposit money?</html>", SwingConstants.CENTER);
        instructionLabel.setFont(new Font("Yu Gothic", Font.BOLD, 16));
        instructionLabel.setForeground(Color.BLACK);
        instructionLabel.setBounds(30, 140, 340, 50);
        mainPanel.add(instructionLabel);

        // Transfer button
        transferButton = new JButton("Transfer");
        transferButton.setBounds(30, 230, 120, 40);
        transferButton.setFont(new Font("Yu Gothic", Font.BOLD, 16));
        transferButton.setBackground(Color.decode("#C2E7FF"));
        mainPanel.add(transferButton);

        // Withdraw button
        withdrawButton = new JButton("Withdraw");
        withdrawButton.setBounds(220, 230, 120, 40);
        withdrawButton.setFont(new Font("Yu Gothic", Font.BOLD, 16));
        withdrawButton.setBackground(Color.decode("#C2E7FF"));
        mainPanel.add(withdrawButton);

        // Deposit button
        depositButton = new JButton("Deposit");
        depositButton.setBounds(120, 290, 120, 40);
        depositButton.setFont(new Font("Yu Gothic", Font.BOLD, 16));
        depositButton.setBackground(Color.decode("#C2E7FF"));
        mainPanel.add(depositButton);

        // Account information display
        JPanel displayPanel = new JPanel(new BorderLayout());
        displayPanel.setBounds(0, 386, 400, 100);
        displayPanel.setBackground(Color.decode("#EDF2FA"));
        showAllData = new JLabel("");
        showAllData.setVerticalAlignment(JLabel.TOP);
        showAllData.setFont(new Font("Yu Gothic", Font.BOLD, 14));
        showAllData.setBorder(BorderFactory.createTitledBorder("Account Information"));
        displayPanel.add(new JScrollPane(showAllData), BorderLayout.CENTER);
        mainPanel.add(displayPanel);

        // Show all accounts button
        showAllButton = new JButton("Show All Accounts");
        showAllButton.setBounds(100, 490, 200, 30);  // Adjusted position
        showAllButton.setFont(new Font("Yu Gothic", Font.BOLD, 16));
        showAllButton.setBackground(Color.decode("#C2E7FF"));
        mainPanel.add(showAllButton);

        // Adds back and exit buttons to main panel
        backButton = new JButton("Back");
        backButton.setBounds(30, 530, 140, 30);
        backButton.setFont(new Font("Yu Gothic", Font.BOLD, 16));
        backButton.setBackground(Color.decode("#C2E7FF"));
        mainPanel.add(backButton);

        exitButton = new JButton("Exit");
        exitButton.setBounds(230, 530, 140, 30);
        exitButton.setFont(new Font("Yu Gothic", Font.BOLD, 16));
        exitButton.setBackground(Color.decode("#C2E7FF"));
        mainPanel.add(exitButton);

        // Creates the login panel with null layout
        loginPanel = new JPanel(null);
        loginPanel.setPreferredSize(new Dimension(400, 600));
        loginPanel.setBackground(Color.decode("#EDF2FA"));

        // Header panel
        JLabel greetingLabel = new JLabel("Welcome", SwingConstants.CENTER);
        greetingLabel.setFont(new Font("Yu Gothic", Font.BOLD, 20));
        greetingLabel.setForeground(Color.BLACK);
        greetingLabel.setBounds(100, 20, 200, 30);
        loginPanel.add(greetingLabel);

        // Form panel with login form
        JPanel formPanel = new JPanel(null);
        formPanel.setBackground(new Color(255, 255, 255));
        formPanel.setBounds(0, 60, 400, 400);
        formPanel.setBackground(Color.decode("#EDF2FA"));

        // User name field
        JLabel userNameLabel = new JLabel("User name:");
        userNameLabel.setBounds(30, 30, 300, 25);
        userNameLabel.setFont(new Font("Yu Gothic", Font.BOLD, 20));
        formPanel.add(userNameLabel);

        loginNameField = new JTextField();
        loginNameField.setBounds(30, 60, 300, 40);
        loginNameField.setFont(new Font("Yu Gothic", Font.BOLD, 16));
        formPanel.add(loginNameField);

        // Account Number field
        JLabel accNumLabel = new JLabel("Account Number:");
        accNumLabel.setBounds(30, 120, 300, 25);
        accNumLabel.setFont(new Font("Yu Gothic", Font.BOLD, 20));
        formPanel.add(accNumLabel);

        loginAccNumField = new JTextField();
        loginAccNumField.setBounds(30, 150, 300, 40);
        loginAccNumField.setFont(new Font("Yu Gothic", Font.BOLD, 16));
        formPanel.add(loginAccNumField);

        // Remember Me checkbox
        JCheckBox rememberMeCheckBox = new JCheckBox("Remember Me");
        rememberMeCheckBox.setBounds(30, 210, 300, 25);
        rememberMeCheckBox.setBackground(Color.decode("#EDF2FA"));
        rememberMeCheckBox.setFont(new Font("Yu Gothic", Font.BOLD, 20));
        formPanel.add(rememberMeCheckBox);

        // Login button
        loginButton = new JButton("Login");
        loginButton.setBounds(30, 250, 300, 50);
        loginButton.setFont(new Font("Yu Gothic", Font.BOLD, 20));
        loginButton.setBackground(Color.decode("#C2E7FF"));
        loginButton.setForeground(new Color(0, 0, 0));
        formPanel.add(loginButton);

        loginPanel.add(formPanel);

        // Creates transfer panel
        transferPanel = new JPanel(null);
        transferPanel.setPreferredSize(new Dimension(400, 600));
        transferPanel.setBackground(Color.decode("#EDF2FA"));

        // Title label
        JLabel transferLabel = new JLabel("Transfer Money", SwingConstants.CENTER);
        transferLabel.setFont(new Font("Yu Gothic", Font.BOLD, 20));
        transferLabel.setBounds(100, 20, 200, 30);
        transferPanel.add(transferLabel);

        // Label for sender's account number
        JLabel senderAccLabel = new JLabel("Sender Account Number:");
        senderAccLabel.setBounds(30, 70, 300, 25);  // Adjusted position
        senderAccLabel.setFont(new Font("Yu Gothic", Font.BOLD, 16));
        transferPanel.add(senderAccLabel);

        // Input field for sender's account number
        fromAccTransfer = new JTextField(20);
        fromAccTransfer.setBounds(30, 100, 300, 40);  // Adjusted position and size
        fromAccTransfer.setFont(new Font("Yu Gothic", Font.PLAIN, 16));
        transferPanel.add(fromAccTransfer);

        // Label for recipient's account number
        JLabel acc2TransferLabel = new JLabel("Recipient Account Number:");
        acc2TransferLabel.setBounds(30, 160, 300, 25);  // Adjusted position
        acc2TransferLabel.setFont(new Font("Yu Gothic", Font.BOLD, 16));
        transferPanel.add(acc2TransferLabel);

        // Input field for recipient's account number
        acc2Transfer = new JTextField(20);
        acc2Transfer.setBounds(30, 190, 300, 40);  // Adjusted position and size
        acc2Transfer.setFont(new Font("Yu Gothic", Font.PLAIN, 16));
        transferPanel.add(acc2Transfer);

        // Label for transfer amount
        JLabel transferAmountLabel = new JLabel("Transfer Amount:");
        transferAmountLabel.setBounds(30, 250, 300, 25);  // Adjusted position
        transferAmountLabel.setFont(new Font("Yu Gothic", Font.BOLD, 16));
        transferPanel.add(transferAmountLabel);

        // Input field for transfer amount
        transferAmount = new JTextField(20);
        transferAmount.setBounds(30, 280, 300, 40);  // Adjusted position and size
        transferAmount.setFont(new Font("Yu Gothic", Font.PLAIN, 16));
        transferPanel.add(transferAmount);

        confirmTransferButton = new JButton("Confirm Transfer");
        confirmTransferButton.setBounds(100, 320, 200, 40);
        confirmTransferButton.setFont(new Font("Yu Gothic", Font.BOLD, 16));
        confirmTransferButton.setBackground(Color.decode("#C2E7FF"));
        transferPanel.add(confirmTransferButton);

        // Account information display for transfer panel
        JPanel transferDisplayPanel = new JPanel(new BorderLayout());
        transferDisplayPanel.setBounds(0, 386, 400, 100);
        transferDisplayPanel.setBackground(Color.decode("#EDF2FA"));
        transferShowAllData = new JLabel("");
        transferShowAllData.setVerticalAlignment(JLabel.TOP);
        transferShowAllData.setFont(new Font("Yu Gothic", Font.BOLD, 14));
        transferShowAllData.setBorder(BorderFactory.createTitledBorder("Account Information"));
        transferDisplayPanel.add(new JScrollPane(transferShowAllData), BorderLayout.CENTER);
        transferPanel.add(transferDisplayPanel);

        // Show all accounts button for transfer panel
        transferShowAllButton = new JButton("Show All Accounts");
        transferShowAllButton.setBounds(100, 490, 200, 30);
        transferShowAllButton.setFont(new Font("Yu Gothic", Font.BOLD, 16));
        transferShowAllButton.setBackground(Color.decode("#C2E7FF"));
        transferPanel.add(transferShowAllButton);

        // Add back and exit buttons to transfer panel
        transferBackButton = new JButton("Back");
        transferBackButton.setBounds(30, 530, 140, 30);
        transferBackButton.setFont(new Font("Yu Gothic", Font.BOLD, 16));
        transferBackButton.setBackground(Color.decode("#C2E7FF"));
        transferPanel.add(transferBackButton);

        transferExitButton = new JButton("Exit");
        transferExitButton.setBounds(230, 530, 140, 30);
        transferExitButton.setFont(new Font("Yu Gothic", Font.BOLD, 16));
        transferExitButton.setBackground(Color.decode("#C2E7FF"));
        transferPanel.add(transferExitButton);

        // Creates withdraw panel
        withdrawPanel = new JPanel(null);
        withdrawPanel.setPreferredSize(new Dimension(400, 600));
        withdrawPanel.setBackground(Color.decode("#EDF2FA"));

        JLabel withdrawLabel = new JLabel("Withdraw Money", SwingConstants.CENTER);
        withdrawLabel.setFont(new Font("Yu Gothic", Font.BOLD, 20));
        withdrawLabel.setBounds(100, 20, 200, 30);
        withdrawPanel.add(withdrawLabel);

        JLabel accWithdrawLabel = new JLabel("Account Number:");
        accWithdrawLabel.setBounds(30, 70, 300, 25);
        accWithdrawLabel.setFont(new Font("Yu Gothic", Font.BOLD, 16));
        withdrawPanel.add(accWithdrawLabel);

        accWithdraw = new JTextField();
        accWithdraw.setBounds(30, 100, 300, 40);
        accWithdraw.setFont(new Font("Yu Gothic", Font.BOLD, 16));
        withdrawPanel.add(accWithdraw);

        JLabel withdrawAmountLabel = new JLabel("Amount:");
        withdrawAmountLabel.setBounds(30, 150, 300, 25);
        withdrawAmountLabel.setFont(new Font("Yu Gothic", Font.BOLD, 16));
        withdrawPanel.add(withdrawAmountLabel);

        withdrawInput = new JTextField();
        withdrawInput.setBounds(30, 180, 300, 40);
        withdrawInput.setFont(new Font("Yu Gothic", Font.BOLD, 16));
        withdrawPanel.add(withdrawInput);

        confirmWithdrawButton = new JButton("Confirm Withdraw");
        confirmWithdrawButton.setBounds(100, 240, 200, 40);
        confirmWithdrawButton.setFont(new Font("Yu Gothic", Font.BOLD, 16));
        confirmWithdrawButton.setBackground(Color.decode("#C2E7FF"));
        withdrawPanel.add(confirmWithdrawButton);

        // Account information display for withdraw panel
        JPanel withdrawDisplayPanel = new JPanel(new BorderLayout());
        withdrawDisplayPanel.setBounds(0, 386, 400, 100);
        withdrawDisplayPanel.setBackground(Color.decode("#EDF2FA"));
        withdrawShowAllData = new JLabel("");
        withdrawShowAllData.setVerticalAlignment(JLabel.TOP);
        withdrawShowAllData.setFont(new Font("Yu Gothic", Font.BOLD, 14));
        withdrawShowAllData.setBorder(BorderFactory.createTitledBorder("Account Information"));
        withdrawDisplayPanel.add(new JScrollPane(withdrawShowAllData), BorderLayout.CENTER);
        withdrawPanel.add(withdrawDisplayPanel);

        // Show all accounts button for withdraw panel
        withdrawShowAllButton = new JButton("Show All Accounts");
        withdrawShowAllButton.setBounds(100, 490, 200, 30);
        withdrawShowAllButton.setFont(new Font("Yu Gothic", Font.BOLD, 16));
        withdrawShowAllButton.setBackground(Color.decode("#C2E7FF"));
        withdrawPanel.add(withdrawShowAllButton);

        // Add back and exit buttons to withdraw panel
        withdrawBackButton = new JButton("Back");
        withdrawBackButton.setBounds(30, 530, 140, 30);
        withdrawBackButton.setFont(new Font("Yu Gothic", Font.BOLD, 16));
        withdrawBackButton.setBackground(Color.decode("#C2E7FF"));
        withdrawPanel.add(withdrawBackButton);

        withdrawExitButton = new JButton("Exit");
        withdrawExitButton.setBounds(230, 530, 140, 30);
        withdrawExitButton.setFont(new Font("Yu Gothic", Font.BOLD, 16));
        withdrawExitButton.setBackground(Color.decode("#C2E7FF"));
        withdrawPanel.add(withdrawExitButton);

        // Creates deposit panel
        depositPanel = new JPanel(null);
        depositPanel.setPreferredSize(new Dimension(400, 600));
        depositPanel.setBackground(Color.decode("#EDF2FA"));

        JLabel depositLabel = new JLabel("Deposit Money", SwingConstants.CENTER);
        depositLabel.setFont(new Font("Yu Gothic", Font.BOLD, 20));
        depositLabel.setBounds(100, 20, 200, 30);
        depositPanel.add(depositLabel);

        JLabel accDepositLabel = new JLabel("Account Number:");
        accDepositLabel.setBounds(30, 70, 300, 25);
        accDepositLabel.setFont(new Font("Yu Gothic", Font.BOLD, 16));
        depositPanel.add(accDepositLabel);

        accDeposit = new JTextField();
        accDeposit.setBounds(30, 100, 300, 40);
        accDeposit.setFont(new Font("Yu Gothic", Font.BOLD, 16));
        depositPanel.add(accDeposit);

        JLabel depositAmountLabel = new JLabel("Amount:");
        depositAmountLabel.setBounds(30, 150, 300, 25);
        depositAmountLabel.setFont(new Font("Yu Gothic", Font.BOLD, 16));
        depositPanel.add(depositAmountLabel);

        depositInput = new JTextField();
        depositInput.setBounds(30, 180, 300, 40);
        depositInput.setFont(new Font("Yu Gothic", Font.BOLD, 16));
        depositPanel.add(depositInput);

        confirmDepositButton = new JButton("Confirm Deposit");
        confirmDepositButton.setBounds(100, 240, 200, 40);
        confirmDepositButton.setFont(new Font("Yu Gothic", Font.BOLD, 16));
        confirmDepositButton.setBackground(Color.decode("#C2E7FF"));
        depositPanel.add(confirmDepositButton);

        // Account information display for deposit panel
        JPanel depositDisplayPanel = new JPanel(new BorderLayout());
        depositDisplayPanel.setBounds(0, 386, 400, 100);
        depositDisplayPanel.setBackground(Color.decode("#EDF2FA"));
        depositShowAllData = new JLabel("");
        depositShowAllData.setVerticalAlignment(JLabel.TOP);
        depositShowAllData.setFont(new Font("Yu Gothic", Font.BOLD, 14));
        depositShowAllData.setBorder(BorderFactory.createTitledBorder("Account Information"));
        depositDisplayPanel.add(new JScrollPane(depositShowAllData), BorderLayout.CENTER);
        depositPanel.add(depositDisplayPanel);

        // Show all accounts button for deposit panel
        depositShowAllButton = new JButton("Show All Accounts");
        depositShowAllButton.setBounds(100, 490, 200, 30);
        depositShowAllButton.setFont(new Font("Yu Gothic", Font.BOLD, 16));
        depositShowAllButton.setBackground(Color.decode("#C2E7FF"));
        depositPanel.add(depositShowAllButton);

        // Add back and exit buttons to deposit panel
        depositBackButton = new JButton("Back");
        depositBackButton.setBounds(30, 530, 140, 30);
        depositBackButton.setFont(new Font("Yu Gothic", Font.BOLD, 16));
        depositBackButton.setBackground(Color.decode("#C2E7FF"));
        depositPanel.add(depositBackButton);

        depositExitButton = new JButton("Exit");
        depositExitButton.setBounds(230, 530, 140, 30);
        depositExitButton.setFont(new Font("Yu Gothic", Font.BOLD, 16));
        depositExitButton.setBackground(Color.decode("#C2E7FF"));
        depositPanel.add(depositExitButton);

        add(loginPanel);
        HandlerClass handler = new HandlerClass();
        showAllButton.addActionListener(handler);
        depositButton.addActionListener(handler);
        withdrawButton.addActionListener(handler);
        transferButton.addActionListener(handler);
        loginButton.addActionListener(handler);
        confirmTransferButton.addActionListener(handler);
        confirmWithdrawButton.addActionListener(handler);
        confirmDepositButton.addActionListener(handler);
        backButton.addActionListener(handler);
        exitButton.addActionListener(handler);
        transferShowAllButton.addActionListener(handler);
        withdrawShowAllButton.addActionListener(handler);
        depositShowAllButton.addActionListener(handler);
        transferBackButton.addActionListener(handler);
        withdrawBackButton.addActionListener(handler);
        depositBackButton.addActionListener(handler);
        transferExitButton.addActionListener(handler);
        withdrawExitButton.addActionListener(handler);
        depositExitButton.addActionListener(handler);

        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(false);

        // Show splash screen
        showSplashScreen();
    }

    private void showSplashScreen() {
        JWindow splash = new JWindow();
        JPanel content = new JPanel(new BorderLayout());
        content.setBackground(Color.decode("#EDF2FA"));
        JLabel label = new JLabel("Welcome to the Banking System", JLabel.CENTER);
        label.setFont(new Font("Yu Gothic", Font.BOLD, 24));
        content.add(label, BorderLayout.CENTER);
        splash.getContentPane().add(content);
        splash.setSize(400, 600);
        splash.setLocationRelativeTo(null);
        splash.setVisible(true);

        // Close splash screen after 2 seconds
        new Timer(2000, e -> {
            splash.setVisible(false);
            splash.dispose();
            setLocation(splash.getLocation());
            setVisible(true);
        }).start();
    }

    private class HandlerClass implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == loginButton) { // Login button action
                String name = loginNameField.getText();
                int accNum;
                try {
                    accNum = Integer.parseInt(loginAccNumField.getText());  // Parses the account number
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid account number. Please enter numbers only.");
                    return;
                }
                boolean validLogin = false;
                if ((name.equals("Hiran Patel") && accNum == 11) || // Static login validation
                    (name.equals("Jeffrey Ting") && accNum == 22) ||
                    (name.equals("Paul Wilson") && accNum == 33) ||
                    (name.equals("Adam Worrallo") && accNum == 44)) {
                    validLogin = true;
                }
                for (Account acc : globalAccounts) { // Dynamic login validation
                    if (acc.getFirstName().equals(name) && acc.getAccountNum() == accNum) {
                        validLogin = true;
                        userNameLabel.setText(acc.getFirstName() + " " + acc.getLastName());
                        accountNumberLabel.setText("Account Number: " + acc.getAccountNum());
                        balanceLabel.setText("Balance: " + acc.getBalance());
                        break;
                    }
                }
                if (validLogin) {
                    // Switches to the main panel after successful login
                    previousPanel = loginPanel;
                    currentPanel = mainPanel;
                    remove(loginPanel);
                    add(mainPanel);
                    revalidate();
                    repaint();
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid name or account number.");
                }
            }
            // Show all data button action
            else if (e.getSource() == showAllButton) {
                showAllData.setText("<html>" + sbAllData.toString().replace("\n", "<br/>") + "</html>");
            }
            // Transfer show all data button action
            else if (e.getSource() == transferShowAllButton) {
                transferShowAllData.setText("<html>" + sbAllData.toString().replace("\n", "<br/>") + "</html>");
            }
            // Withdraw show all data button action
            else if (e.getSource() == withdrawShowAllButton) {
                withdrawShowAllData.setText("<html>" + sbAllData.toString().replace("\n", "<br/>") + "</html>");
            }
            // Deposit show all data button action
            else if (e.getSource() == depositShowAllButton) {
                depositShowAllData.setText("<html>" + sbAllData.toString().replace("\n", "<br/>") + "</html>");
            }
            // Deposit button action
            else if (e.getSource() == depositButton) {
                previousPanel = mainPanel;
                currentPanel = depositPanel;
                remove(mainPanel);
                add(depositPanel);
                revalidate();
                repaint();
            }
            // Withdraw button action
            else if (e.getSource() == withdrawButton) {
                previousPanel = mainPanel;
                currentPanel = withdrawPanel;
                remove(mainPanel);
                add(withdrawPanel);
                revalidate();
                repaint();
            }
            // Transfer button action
            else if (e.getSource() == transferButton) {
                previousPanel = mainPanel;
                currentPanel = transferPanel;
                remove(mainPanel);
                add(transferPanel);
                revalidate();
                repaint();
            }
            // Confirm deposit button action
            else if (e.getSource() == confirmDepositButton) {
                try {
                    int accNum = Integer.parseInt(accDeposit.getText());  // Parse account number for deposit
                    int amount = Integer.parseInt(depositInput.getText());  // Parse deposit amount
                    
                    for (Account acc : globalAccounts) {
                        System.out.println(acc.getAccountNum());
                        System.out.println(accNum);
                        if (acc.getAccountNum() == accNum) {
                            

                            acc.deposit(amount);  // Perform deposit
                            JOptionPane.showMessageDialog(null, "Deposit successful. New balance: " + acc.getBalance());
                            updateSbAllData();  // Update displayed account data
                            updateCSV();  // Save changes to CSV file
                            return;
                        }
                    }
                    JOptionPane.showMessageDialog(null, "Account not found.");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid input. Please enter numbers only.");
                }
            }
            // Confirm withdraw button action
            else if (e.getSource() == confirmWithdrawButton) {
                try {
                    int accNum = Integer.parseInt(accWithdraw.getText());  // Parse account number for withdrawal
                    int amount = Integer.parseInt(withdrawInput.getText());  // Parse withdrawal amount
                    for (Account acc : globalAccounts) {
                        if (acc.getAccountNum() == accNum) {
                            if (acc.getBalance() >= amount) {  // Check for sufficient funds
                                acc.withdraw(amount);  // Perform withdrawal
                                JOptionPane.showMessageDialog(null, "Withdrawal successful. New balance: " + acc.getBalance());
                                updateSbAllData();  // Update displayed account data
                                updateCSV();  // Save changes to CSV file
                            } else {
                                JOptionPane.showMessageDialog(null, "Insufficient funds.");
                            }
                            return;
                        }
                    }
                    JOptionPane.showMessageDialog(null, "Account not found.");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid input. Please enter numbers only.");
                }
            }
            // Confirm transfer button action
            else if (e.getSource() == confirmTransferButton) {
                try {
                    int fromAccNum = Integer.parseInt(fromAccTransfer.getText());  // sender account with acc num
                    int toAccNum = Integer.parseInt(acc2Transfer.getText());       // Parse recipient account number
                    int amount = Integer.parseInt(transferAmount.getText());       // Parse transfer amount

                    Account fromAcc = null;
                    Account toAcc = null;

                    // Find the sender and recipient accounts
                    for (Account acc : globalAccounts) {
                        if (acc.getAccountNum() == fromAccNum) fromAcc = acc;   
                        if (acc.getAccountNum() == toAccNum) toAcc = acc;       
                    }

                    if (fromAcc != null && toAcc != null) {
                        // Check for sufficient funds in the sender's account
                        if (fromAcc.getBalance() >= amount) {
                            transferObject.transfer(fromAcc, toAcc, amount);  // Perform transfer
                            JOptionPane.showMessageDialog(null, "Transfer successful.");
                            updateSbAllData();  // Update displayed account data
                            updateCSV();         // Save changes to CSV file
                        } else {
                            JOptionPane.showMessageDialog(null, "Insufficient funds for transfer.");
                        }
                    } else {
                        // Display error if sender or recipient account is not found
                        JOptionPane.showMessageDialog(null, "Sender or recipient account not found.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid input. Please enter valid numbers.");
                }
            }



            // Back button actions
            else if (e.getSource() == backButton || e.getSource() == transferBackButton ||
                     e.getSource() == withdrawBackButton || e.getSource() == depositBackButton) {
                remove(currentPanel);
                add(previousPanel);
                JPanel temp = currentPanel;
                currentPanel = previousPanel;
                previousPanel = temp;
                revalidate();
                repaint();
            }
            // Exit button actions
            else if (e.getSource() == exitButton || e.getSource() == transferExitButton ||
                     e.getSource() == withdrawExitButton || e.getSource() == depositExitButton) {
                System.exit(0);  // Exit the application
            }
        }
    }

    // Updates GUI components with all account data
    private void updateSbAllData() {
        sbAllData.setLength(0);  // Clear existing data in StringBuilder
        for (Account acc : globalAccounts) {
            sbAllData.append(", Name: ").append(acc.getFirstName()).append(" ")
                     .append(acc.getLastName()).append(", Balance: ").append(acc.getBalance())
                     .append("\n");
        }
        // Update text areas to display account data
        showAllData.setText("<html>" + sbAllData.toString().replace("\n", "<br/>") + "</html>");
        transferShowAllData.setText("<html>" + sbAllData.toString().replace("\n", "<br/>") + "</html>");
        withdrawShowAllData.setText("<html>" + sbAllData.toString().replace("\n", "<br/>") + "</html>");
        depositShowAllData.setText("<html>" + sbAllData.toString().replace("\n", "<br/>") + "</html>");
    }

    // Updates the CSV file with current account data
    private void updateCSV() {
        try (PrintWriter writer = new PrintWriter(new File("account.csv"))) {
            for (Account acc : globalAccounts) {
                writer.println(acc.getFirstName() + "," + acc.getLastName() + "," +
                               acc.getAccountNum() + "," + acc.getBalance());
            }
        } catch (IOException e) {
            e.printStackTrace();  // Handle any IO exceptions that occur
        }
    }

    // Main method to launch the GUI application
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GUI(loadAccounts("Accounts.csv")));  // Initialize GUI on the Event Dispatch Thread
    }

    // Loads account data from a CSV file and returns a list of Account objects
    private static LinkedList<Account> loadAccounts(String filename) {
        LinkedList<Account> accounts = new LinkedList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                accounts.add(new Account(data[0], data[1], Integer.parseInt(data[2]), Integer.parseInt(data[3])));
            }
        } catch (IOException e) {
            e.printStackTrace();  // Handle any IO exceptions that occur
        }
        return accounts;
    }
}