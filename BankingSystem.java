import java.time.LocalDate;
import java.util.*;

public class BankingSystem {
    private final Map<String, BankAccount> accounts;
    private final List<InterestRule> interestRules;
    private final Set<String> interestRuleSet = new HashSet<>();
    private final Map<String, Integer> transactionCounter;

    private final Map<String, Double> ruleIdToRate; // RuleID -> Rate mapping

    public BankingSystem() {
        accounts = new HashMap<>();
        interestRules = new ArrayList<>();
        transactionCounter = new HashMap<>();
        ruleIdToRate = new HashMap<>();
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

    public boolean addInterestRule(LocalDate date, String ruleId, double rate) {
        // If the RuleID already exists but has a different rate, reject it.
        if (ruleIdToRate.containsKey(ruleId) && ruleIdToRate.get(ruleId) != rate) {
            return false;
        }

        // If the same RuleID exists with the same rate on the same date, reject it.
        for (InterestRule rule : interestRules) {
            if (rule.getDate().equals(date) && rule.getRuleId().equals(ruleId)) {
                return false;
            }
        }

        // Store the RuleID and its rate if not already stored
        ruleIdToRate.put(ruleId, rate);
        interestRules.add(new InterestRule(date, ruleId, rate));
        return true;
    }

    public BankAccount getAccount(String accountId) {
        return accounts.get(accountId);
    }

    public List<InterestRule> getInterestRules() {
        return interestRules;
    }
}
