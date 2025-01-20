import java.time.LocalDate;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        BankingSystem bankingSystem = new BankingSystem();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            printMainMenu();
            String choice = scanner.nextLine().trim().toLowerCase();
            switch (choice) {
                case "t":
                    handleTransactionInput(bankingSystem, scanner);
                    break;
                case "i":
                    handleInterestRuleInput(bankingSystem, scanner);
                    break;
                case "p":
                    handlePrintStatement(bankingSystem, scanner);
                    break;
                case "q":
                    System.out.println("Thank you for banking with AwesomeGIC Bank.");
                    System.out.println("Have a nice day!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
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
        System.out.println("(or enter blank to go back to main menu):");
        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) return;

            String[] parts = input.split("\\s+");
            if (parts.length != 4) {
                System.out.println("Invalid input. Try again.");
                continue;
            }

            String date = parts[0];
            String accountId = parts[1];
            String type = parts[2];
            double amount;
            try {
                amount = Double.parseDouble(parts[3]);
                if (amount <= 0) throw new NumberFormatException();
            } catch (NumberFormatException e) {
                System.out.println("Invalid amount. Try again.");
                continue;
            }

            if (bankingSystem.addTransaction(accountId, date, type, amount)) {
                System.out.println("Transaction added successfully.");
                printStatement(bankingSystem.getAccount(accountId));
            } else {
                System.out.println("Transaction failed. Insufficient balance or invalid type.");
            }
        }
    }

    private static void handleInterestRuleInput(BankingSystem bankingSystem, Scanner scanner) {
        System.out.println("\nPlease enter interest rules details in <Date YYYY-MM-DD> <RuleId> <Rate in %> format");
        System.out.println("(or enter blank to go back to main menu):");
        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) return;

            String[] parts = input.split("\\s+");
            if (parts.length != 3) {
                System.out.println("Invalid input. Try again.");
                continue;
            }

            String ruleId = parts[1];
            double rate;
            try {
                rate = Double.parseDouble(parts[2]);
                if (rate <= 0 || rate >= 100) throw new NumberFormatException();
            } catch (NumberFormatException e) {
                System.out.println("Invalid rate. Try again.");
                continue;
            }

            try {
                LocalDate date = LocalDate.parse(parts[0]);
                bankingSystem.addInterestRule(ruleId, date, rate);
                System.out.println("Interest rule added successfully.");
            } catch (Exception e) {
                System.out.println("Invalid date format. Please use YYYY-MM-DD.");
            }
        }
    }

    private static void handlePrintStatement(BankingSystem bankingSystem, Scanner scanner) {
        System.out.println("\nPlease enter account and month to generate the statement <Account> <Year><Month>");
        System.out.println("(or enter blank to go back to main menu):");
        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) return;

            String[] parts = input.split("\\s+");
            if (parts.length != 2) {
                System.out.println("Invalid input. Try again.");
                continue;
            }

            String accountId = parts[0];
            BankAccount account = bankingSystem.getAccount(accountId);

            if (account == null) {
                System.out.println("Account not found.");
                continue;
            }

            printStatement(account);
            System.out.println(); // Add a line break for better readability
            printMainMenu();
            return; // After printing, return to main menu
        }
    }

    private static void printStatement(BankAccount account) {
        System.out.println("\nAccount: " + account.getAccountId());
        System.out.println("| Date     | Txn Id      | Type | Amount |");
        for (Transaction txn : account.getTransactions()) {
            System.out.printf("| %-8s | %-10s | %-4s | %6.2f |\n",
                    txn.getDate(), txn.getTxnId(), txn.getType(), txn.getAmount());
        }
    }
}
