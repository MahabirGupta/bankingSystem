import java.util.ArrayList;
import java.util.List;

public class BankAccount {
    private final String accountId;
    private final List<Transaction> transactions;

    public BankAccount(String accountId) {
        this.accountId = accountId;
        this.transactions = new ArrayList<>();
    }

    public boolean addTransaction(Transaction transaction) {
        if (transaction.getType().equalsIgnoreCase("W")) {
            double currentBalance = getBalance();
            if (currentBalance < transaction.getAmount()) {
                return false; // Insufficient balance
            }
        }
        transactions.add(transaction);
        return true;
    }



    public String getAccountId() {
        return accountId;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public double getBalance() {
        return transactions.stream()
                .mapToDouble(t -> t.getType().equalsIgnoreCase("D") ? t.getAmount() : -t.getAmount())
                .sum();
    }
}
