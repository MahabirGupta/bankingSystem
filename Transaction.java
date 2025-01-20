import java.time.LocalDate;

public class Transaction {
    private final String txnId;
    private final String type;
    private final double amount;
    private final LocalDate date;

    public Transaction(String type, double amount, String txnId) {
        this.txnId = txnId;
        this.type = type;
        this.amount = amount;
        this.date = LocalDate.now();
    }

    public String getTxnId() {
        return txnId;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDate getDate() {
        return date;
    }
}
