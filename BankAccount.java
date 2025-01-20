import java.util.ArrayList;
import java.util.List;

public class BankAccount {
    private final String accountId;
    private double balance;
    private final List<Transaction> transactions;

    public BankAccount(String accountId) {
        this.accountId = accountId;
        this.balance = 0.0;
        this.transactions = new ArrayList<>();
    }

    public String getAccountId() {
        return accountId;
    }

    public double getBalance() {
        return balance;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public boolean addTransaction(Transaction transaction) {
        if (transaction.getType().equalsIgnoreCase("W") && balance < transaction.getAmount()) {
            return false;
        }
        if (transaction.getType().equalsIgnoreCase("W")) {
            balance -= transaction.getAmount();
        } else if (transaction.getType().equalsIgnoreCase("D")) {
            balance += transaction.getAmount();
        }
        transactions.add(transaction);
        return true;
    }

    public void applyInterest(double interestAmount) {
        balance += interestAmount;
        transactions.add(new Transaction("I", interestAmount, null));
    }
}
