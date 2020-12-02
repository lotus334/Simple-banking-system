package org.hyperskill.banking;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class StorageTest {

    @Test
    public void successfulLogInto() {
        Storage storage = new Storage();
        Account acc1 = new Account();
        Account acc2 = new Account();
        Account acc3 = new Account();
        storage.add(acc1);
        storage.add(acc2);
        storage.add(acc3);
        var result = storage.logIntoAccount(acc2.getNumber(), acc2.getPin());
        assertThat(result.get(), is(acc2));
    }

    @Test
    public void failedLogInto() {
        Storage storage = new Storage();
        Account acc1 = new Account();
        Account acc2 = new Account();
        Account acc3 = new Account();
        storage.add(acc1);
        storage.add(acc2);
        storage.add(acc3);
        var result = storage.logIntoAccount(acc2.getNumber(), acc1.getPin());
        assertTrue(result.isEmpty());
    }
}