import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class BankingSystemTest {

    @Test
    void testAddTransaction() {
        BankingSystem bankingSystem = new BankingSystem();
        assertTrue(bankingSystem.addTransaction("AC001", "20230101", "D", 100));
        assertFalse(bankingSystem.addTransaction("AC001", "20230101", "W", 200));
        assertTrue(bankingSystem.addTransaction("AC001", "20230101", "W", 50));
    }

@Test
void testInterestRules() {
    BankingSystem bankingSystem = new BankingSystem();

    // Allowed: Different dates with the same RuleID and rate
    assertTrue(bankingSystem.addInterestRule(LocalDate.of(2023, 1, 1), "RULE01", 1.95));
    assertTrue(bankingSystem.addInterestRule(LocalDate.of(2023, 1, 2), "RULE01", 1.95));

    // Allowed: Different RuleID on the same date
    assertTrue(bankingSystem.addInterestRule(LocalDate.of(2023, 1, 1), "RULE02", 2.00));

    // Not allowed: Same RuleID on the same date with different rate
    assertFalse(bankingSystem.addInterestRule(LocalDate.of(2023, 1, 1), "RULE01", 2.00));

    // Not allowed: Same RuleID on the same date with same rate
    assertFalse(bankingSystem.addInterestRule(LocalDate.of(2023, 1, 1), "RULE01", 1.95));

    // Check that only 3 rules were successfully added
    assertEquals(3, bankingSystem.getInterestRules().size());
}
}
