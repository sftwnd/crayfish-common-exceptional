package com.github.sftwnd.crayfish.common.exceptional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import static com.github.sftwnd.crayfish.common.exceptional.Sneaky.biconsumer;
import static com.github.sftwnd.crayfish.common.exceptional.Sneaky.bifunction;
import static com.github.sftwnd.crayfish.common.exceptional.Sneaky.consumer;
import static com.github.sftwnd.crayfish.common.exceptional.Sneaky.function;
import static com.github.sftwnd.crayfish.common.exceptional.Sneaky.runnable;
import static com.github.sftwnd.crayfish.common.exceptional.Sneaky.supplier;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SneakyTest {

    @Test
    void supplierTest() throws Exception {
        assertDoesNotThrow(() -> supplier(this::suppliable), "Sneaky.supplier hasn't got to throw exception");
        Integer result = suppliable();
        assertEquals(result, supplier(this::suppliable).get(), "Sneaky.supplier(supplier) result has to be equals supplier result");
    }

    @Test
    void supplierExceptionTest() {
        exceptional();
        assertThrows(IllegalArgumentException.class, supplier(this::suppliable)::get, "Sneaky.supplier has to throw right Exception");
    }

    @Test
    void functionTest() throws Exception {
        assertDoesNotThrow(() -> function(this::functional), "Sneaky.function hasn't got to throw exception");
        Integer result = functional(parameter1);
        assertEquals(result, function(this::functional).apply(parameter1), "Sneaky.function(supplier).apply(parameter) result has to be equals functional.apply(parameter) result");
    }

    @Test
    void functionExceptionTest() {
        exceptional();
        var function = function(this::functional);
        assertThrows(IllegalArgumentException.class, () -> function.apply(null), "Sneaky.function has to throw right Exception");
    }

    @Test
    void bifunctionTest() throws Exception {
        var bifunction = bifunction(this::biFunctional);
        Integer result = biFunctional(parameter1, parameter2);
        assertEquals(result, bifunction.apply(parameter1, parameter2),
                "Sneaky.bifunction(biFunctional).apply(parameter1, parameter2) result has to be equals biFunctional.apply(parameter1, parameter2) result");
    }

    @Test
    void bifunctionExceptionTest() {
        exceptional();
        var bifunction = bifunction(this::biFunctional);
        assertThrows(IllegalArgumentException.class, () -> bifunction.apply(null, null),
                "Sneaky.bifunction has to throw right Exception");
    }

    @Test
    void runnableTest() {
        assertDoesNotThrow(() -> runnable(this::callable), "Sneaky.runnable hasn't got to throw exception");
        this.atomicValue.set(-1);
        assertDoesNotThrow(() -> runnable(this::callable).run(), "Sneaky.runnable.run hasn't got to throw exception");
        assertEquals(value(parameter1), this.atomicValue.get(), "Sneaky.runnable.run has to produce right result");
    }

    @Test
    void runnableExceptionTest() {
        exceptional();
        assertThrows(IllegalArgumentException.class, runnable(this::callable)::run, "Sneaky.runnable has to throw right Exception");
    }

    @Test
    void consumerTest() {
        assertDoesNotThrow(() -> consumer(this::consumable), "Sneaky.consumer hasn't got to throw exception");
        this.atomicValue.set(-1);
        assertDoesNotThrow(() -> consumer(this::consumable).accept(this.parameter1), "Sneaky.consumer.accept(parameter1) hasn't got to throw exception");
        assertEquals(value(parameter1), this.atomicValue.get(), "Sneaky.consumer.accept(parameter1) has to produce right result");
    }

    @Test
    void consumerExceptionTest() {
        exceptional();
        var consumer = consumer(this::consumable);
        assertThrows(IllegalArgumentException.class, () -> consumer.accept(null), "Sneaky.consumer has to throw right Exception");
    }

    @Test
    void biconsumerTest() {
        assertDoesNotThrow(() -> biconsumer(this::biConsumable), "Sneaky.biconsumer hasn't got to throw exception");
        this.atomicValue.set(-1);
        assertDoesNotThrow(() -> biconsumer(this::biConsumable).accept(this.parameter1, this.parameter2), "Sneaky.biconsumer.accept(parameter1, parameter1) hasn't got to throw exception");
        assertEquals(value(parameter1, parameter2), this.atomicValue.get(), "Sneaky.biconsumer.accept(parameter1, parameter1) has to produce right result");
    }

    @Test
    void biconsumerExceptionTest() {
        exceptional();
        var biconsumer = biconsumer(this::biConsumable);
        assertThrows(IllegalArgumentException.class, () -> biconsumer.accept(null, null), "Sneaky.biconsumer has to throw right Exception");
    }

    private Integer parameter1;
    private Integer parameter2;
    private AtomicInteger atomicValue;
    private Exception exception;

    @BeforeEach
    void startUp() {
        parameter1 = new Random().nextInt(8) + 1;
        parameter2 = 1 + ((parameter1 + 7 ) % 9);
        atomicValue = new AtomicInteger();
        exception = null;
    }

    private void exceptional() {
        this.exception = new IllegalArgumentException();
    }
    @AfterEach
    void tearDown() {
        atomicValue = null;
    }

    Integer suppliable() throws Exception {
        if (this.exception != null) {
            throw this.exception;
        } else {
            return value(parameter1, 0);
        }
    }

    Integer functional(Integer parameter) throws Exception {
        if (this.exception != null) {
            throw this.exception;
        } else {
            return value(parameter, 0);
        }
    }

    Integer biFunctional(Integer parameter1, Integer parameter2) throws Exception {
        if (this.exception != null) {
            throw this.exception;
        } else {
            return value(parameter1, parameter2);
        }
    }

    void callable() throws Exception {
        if (this.exception != null) {
            throw this.exception;
        } else {
            atomicValue.set(value(parameter1, 0));
        }
    }

    void consumable(Integer parameter) throws Exception {
        if (this.exception != null) {
            throw this.exception;
        } else {
            atomicValue.set(value(parameter, 0));
        }
    }

    void biConsumable(Integer parameter1, Integer parameter2) throws Exception {
        if (this.exception != null) {
            throw this.exception;
        } else {
            atomicValue.set(value(parameter1, parameter2));
        }
    }

    private Integer value(Integer... parameters) {
        int result = 0;
        for (Integer parameter : parameters) {
            result += 10 * parameter;
        }
        return result * 10;
    }

}