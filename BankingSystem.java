import java.time.LocalDate;
import java.util.*;

public class BankingSystem {
    private final Map<String, BankAccount> accounts;
    private final List<InterestRule> interestRules;
    private final Set<String> interestRuleSet = new HashSet<>();
    private final Map<String, Integer> transactionCounter;

    public BankingSystem() {
        accounts = new HashMap<>();
        interestRules = new ArrayList<>();
        transactionCounter = new HashMap<>();
    }

    public boolean addTransaction(String accountId, String date, String type, double amount) {
        accounts.putIfAbsent(accountId, new BankAccount(accountId));
        BankAccount account = accounts.get(accountId);

        String txnId = generateTransactionId(accountId, date);
        return account.addTransaction(new Transaction(date, type, amount, txnId));
    }

    private String generateTransactionId(String accountId, String date) {
        int counter = transactionCounter.getOrDefault(accountId, 0) + 1;
        transactionCounter.put(accountId, counter);
        return String.format("%s-%02d", date, counter);
    }

    public boolean addInterestRule(String ruleId, LocalDate date, double rate) {
        String key = ruleId + "-" + rate;
        if (interestRuleSet.contains(key)) {
            return false; // Duplicate entry
        }
        interestRuleSet.add(key);
        return true;
    }

    public BankAccount getAccount(String accountId) {
        return accounts.get(accountId);
    }

    public List<InterestRule> getInterestRules() {
        return interestRules;
    }
}
