import java.time.LocalDate;

public class InterestRule {
    private final LocalDate date;
    private final String ruleId;
    private final double rate;

    public InterestRule(LocalDate date, String ruleId, double rate) {
        this.date = date;
        this.ruleId = ruleId;
        this.rate = rate;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getRuleId() {
        return ruleId;
    }

    public double getRate() {
        return rate;
    }
}

