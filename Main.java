import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        BankingSystem bankingSystem = new BankingSystem();
        Scanner scanner = new Scanner(System.in);
        boolean showMainMenu = true;


        while (true) {
            if (showMainMenu) {
                printMainMenu();
            }
            String choice = scanner.nextLine().trim().toLowerCase();
            switch (choice) {
                case "t":
                    handleTransactionInput(bankingSystem, scanner);
                    showMainMenu = false;
                    break;
                case "i":
                    handleInterestRuleInput(bankingSystem, scanner);
                    showMainMenu = false;
                    break;
                case "p":
                    handlePrintStatement(bankingSystem, scanner);
                    showMainMenu = false;
                    break;
                case "q":
                    System.out.println("Thank you for banking with AwesomeGIC Bank.");
                    System.out.println("Have a nice day!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    showMainMenu = true;
            }
        }
    }

    private static void printMainMenu() {
        System.out.println("\nWelcome to AwesomeGIC Bank! What would you like to do?");
        System.out.println("[T] Input transactions");
        System.out.println("[I] Define interest rules");
        System.out.println("[P] Print statement");
        System.out.println("[Q] Quit");
        System.out.print("> ");
    }

    private static void handleTransactionInput(BankingSystem bankingSystem, Scanner scanner) {
        System.out.println("\nPlease enter transaction details in <Date YYYYMMdd> <Account> <Type D or W> <Amount> format");
        System.out.println("(or enter blank to go back to the main menu):");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("\nIs there anything else you'd like to do?");
                printOptionMenu();
                return;
            }

            String[] parts = input.split("\\s+");
            if (parts.length != 4) {
                System.out.println("Invalid input. Try again.");
                System.out.println("\nPlease enter transaction details in <Date YYYYMMdd> <Account> <Type D or W> <Amount> format");
                System.out.println("(or enter blank to go back to the main menu):");
                continue;
            }

//            String date = parts[0];
            String dateStr = parts[0];
            String accountId = parts[1];
            String type = parts[2].toUpperCase();
            double amount;

            // Validate date format
            LocalDate date;
            try {
                date = LocalDate.parse(dateStr, dateFormatter);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use YYYYMMdd.");
                System.out.println("\nPlease enter transaction details in <Date YYYYMMdd> <Account> <Type D or W> <Amount> format");
                System.out.println("(or enter blank to go back to the main menu):");
                continue;
            }

            // Validate transaction type
            if (!type.equals("D") && !type.equals("W")) {
                System.out.println("Invalid transaction type. Use D for deposit or W for withdrawal.");
                System.out.println("\nPlease enter transaction details in <Date YYYYMMdd> <Account> <Type D or W> <Amount> format");
                System.out.println("(or enter blank to go back to the main menu):");
                continue;
            }

            // Validate amount
            try {
                amount = Double.parseDouble(parts[3]);
                if (amount <= 0) throw new NumberFormatException();
            } catch (NumberFormatException e) {
                System.out.println("Invalid amount. Try again.");
                System.out.println("\nPlease enter transaction details in <Date YYYYMMdd> <Account> <Type D or W> <Amount> format");
                System.out.println("(or enter blank to go back to the main menu):");
                continue;
            }

            // Process transaction
            if (bankingSystem.addTransaction(accountId, dateStr, type, amount)) {
                System.out.println("Transaction added successfully.");

                // **Fix: Print the statement for the correct transaction month**
                YearMonth transactionMonth = YearMonth.from(date);
                printStatement(bankingSystem.getAccount(accountId), transactionMonth, false);

//                YearMonth currentMonth = YearMonth.now();
////                printStatement(bankingSystem.getAccount(accountId), currentMonth);
//                printStatement(bankingSystem.getAccount(accountId), currentMonth, false);

//                printStatement(bankingSystem.getAccount(accountId));
                System.out.println("\nIs there anything else you'd like to do?");
                printOptionMenu();
                return;
            } else {
                System.out.println("Transaction failed. Insufficient balance or invalid input.");
                System.out.println("\nPlease enter transaction details in <Date YYYYMMdd> <Account> <Type D or W> <Amount> format");
                System.out.println("(or enter blank to go back to the main menu):");
            }
        }
    }

    private static void printOptionMenu() {
        System.out.println("[T] Input transactions");
        System.out.println("[I] Define interest rules");
        System.out.println("[P] Print statement");
        System.out.println("[Q] Quit");
        System.out.print("> ");
    }

    private static void handleInterestRuleInput(BankingSystem bankingSystem, Scanner scanner) {
        System.out.println("\nPlease enter interest rule details in <Date YYYYMMdd> <RuleId> <Rate in %> format");
        System.out.println("(or enter blank to go back to the main menu):");

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("\nIs there anything else you'd like to do?");
                printOptionMenu();
                return;
            }

            String[] parts = input.split("\\s+");
            if (parts.length != 3) {
                System.out.println("Invalid input. Try again.");
                System.out.println("\nPlease enter interest rule details in <Date YYYYMMdd> <RuleId> <Rate in %> format");
                System.out.println("(or enter blank to go back to the main menu):");
                continue;
            }

            String dateStr = parts[0];
            String ruleId = parts[1];
            double rate;

            LocalDate date;
            try {
                date = LocalDate.parse(dateStr, dateFormatter);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use YYYYMMdd.");
                System.out.println("\nPlease enter interest rule details in <Date YYYYMMdd> <RuleId> <Rate in %> format");
                System.out.println("(or enter blank to go back to the main menu):");
                continue;
            }

            try {
                rate = Double.parseDouble(parts[2]);
                if (rate <= 0 || rate >= 100) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid rate. Please enter a valid percentage (0 < rate < 100).");
                System.out.println("\nPlease enter interest rule details in <Date YYYYMMdd> <RuleId> <Rate in %> format");
                System.out.println("(or enter blank to go back to the main menu):");
                continue;
            }

            // Process the valid interest rule
            if (bankingSystem.addInterestRule(date, ruleId, rate)) {
                System.out.println("Interest rule added successfully.");
                printInterestRules(bankingSystem.getInterestRules());
            } else {
                System.out.println("Duplicate interest rule found. RuleId with the same rate already exists.");
                printInterestRules(bankingSystem.getInterestRules());
            }

            System.out.println("\nIs there anything else you'd like to do?");
            printOptionMenu();
            return;
        }
    }


    private static void printInterestRules(List<InterestRule> interestRules) {
        System.out.println("\nInterest rules:");
        System.out.println("| Date     | RuleId | Rate (%) |");
        for (InterestRule rule : interestRules) {
            System.out.printf("| %-8s | %-6s | %6.2f |\n",
                    rule.getDate().format(DateTimeFormatter.ofPattern("yyyyMMdd")),
                    rule.getRuleId(),
                    rule.getRate());
        }
    }
    private static void handlePrintStatement(BankingSystem bankingSystem, Scanner scanner) {
        System.out.println("\nPlease enter account and month to generate the statement <Account> <Year><Month>");
        System.out.println("(or enter blank to go back to the main menu):");

        DateTimeFormatter yearMonthFormatter = DateTimeFormatter.ofPattern("yyyyMM");

        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println("\nIs there anything else you'd like to do?");
                printOptionMenu();
                return;
            }

            String[] parts = input.split("\\s+");
            if (parts.length != 2) {
                System.out.println("Invalid input. Try again.");
                System.out.println("\nPlease enter account and month to generate the statement <Account> <Year><Month>");
                System.out.println("(or enter blank to go back to the main menu):");
                continue;
            }

            String accountId = parts[0];
            String yearMonthStr = parts[1];

            // Validate YearMonth format
            if (yearMonthStr.length() != 6) {
                System.out.println("Invalid date format. Please use YYYYMM.");
                System.out.println("\nPlease enter account and month to generate the statement <Account> <Year><Month>");
                System.out.println("(or enter blank to go back to the main menu):");
                continue;
            }

            try {
                // Parse to ensure it's a valid YYYYMM format
                YearMonth yearMonth = YearMonth.parse(yearMonthStr, yearMonthFormatter);

                // Validate if account exists
                BankAccount account = bankingSystem.getAccount(accountId);
                if (account == null) {
                    System.out.println("Account not found.");
                    break;  // Instead of continuing, break out of the loop to return to the options menu
                }

                // Print statement
//                printStatement(account, yearMonth);
                printStatement(account, yearMonth, true);

                System.out.println("\nIs there anything else you'd like to do?");
                printOptionMenu();
                return;

            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use YYYYMM.");
                System.out.println("\nPlease enter account and month to generate the statement <Account> <Year><Month>");
                System.out.println("(or enter blank to go back to the main menu):");
            }
        }
        // Ensure the option menu is displayed when the account is not found
        System.out.println("\nIs there anything else you'd like to do?");
        printOptionMenu();
    }


    private static void printStatement(BankAccount account, YearMonth yearMonth, boolean showBalance) {
        System.out.println("\nAccount: " + account.getAccountId());
        if (showBalance) {
            System.out.println("| Date     | Txn Id      | Type | Amount | Balance |");
        } else {
            System.out.println("| Date     | Txn Id      | Type | Amount |");
        }

        double balance = 0;
        boolean hasTransactions = false;
        for (Transaction txn : account.getTransactions()) {
            LocalDate txnDate = LocalDate.parse(txn.getDate(), DateTimeFormatter.ofPattern("yyyyMMdd"));
            if (YearMonth.from(txnDate).equals(yearMonth)) {
                hasTransactions = true;
                if (showBalance) {
                    balance = txn.getType().equals("D") ? balance + txn.getAmount() : balance - txn.getAmount();
                    System.out.printf("| %-8s | %-10s | %-4s | %6.2f | %7.2f |\n",
                            txn.getDate(), txn.getTxnId(), txn.getType(), txn.getAmount(), balance);
                } else {
                    System.out.printf("| %-8s | %-10s | %-4s | %6.2f |\n",
                            txn.getDate(), txn.getTxnId(), txn.getType(), txn.getAmount());
                }
            }
        }

        if (!hasTransactions) {
            System.out.println("No transactions found for the given month.");
        }
    }


}
