public class Transaction {
    private final String date;
    private final String type;
    private final double amount;
    private final String txnId;

    public Transaction(String date, String type, double amount, String txnId) {
        this.date = date;
        this.type = type;
        this.amount = amount;
        this.txnId = txnId;
    }

    public String getDate() {
        return date;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public String getTxnId() {
        return txnId;
    }
}
