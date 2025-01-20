import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static junit.framework.TestCase.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

public class BankingSystemTest {

    @Test
    void testAddTransaction() {
        BankingSystem bankingSystem = new BankingSystem();
        assertTrue(bankingSystem.addTransaction("AC001", "D", 100));
        assertFalse(bankingSystem.addTransaction("AC001", "W", 200));
        assertTrue(bankingSystem.addTransaction("AC001", "W", 50));
    }

    @Test
    void testInterestRules() {
        BankingSystem bankingSystem = new BankingSystem();
        bankingSystem.addInterestRule("RULE01", LocalDate.of(2023, 1, 1), 1.95);
        bankingSystem.addInterestRule("RULE02", LocalDate.of(2023, 5, 20), 1.90);
        assertEquals(2, bankingSystem.getInterestRules().size());
    }
}
