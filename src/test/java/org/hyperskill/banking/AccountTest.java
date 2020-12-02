package org.hyperskill.banking;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class AccountTest {

    @Test
    public void compareOneAccounts() {
        Account acc1 = new Account();
        assertThat(acc1, is(acc1));
    }

    @Test
    public void compareTwoAccounts() {
        Account acc1 = new Account();
        Account acc2 = new Account();
        assertNotEquals(acc1, acc2);
    }

    @Test
    public void balanceIsZero() {
        Account acc1 = new Account();
        assertThat(acc1.getBalance(), is(0));
    }
}