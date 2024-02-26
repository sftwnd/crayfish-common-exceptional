package com.github.sftwnd.crayfish.common.exceptional;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static com.github.sftwnd.crayfish.common.exceptional.Exceptional.reInterrupt;
import static com.github.sftwnd.crayfish.common.exceptional.Exceptional.rethrow;
import static com.github.sftwnd.crayfish.common.exceptional.Exceptional.sneakyThrow;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ExceptionalTest {

    @Test
    void rethrowTest() {
        assertThrows(IOException.class, () -> rethrow(new IOException()), "rethrow(IOException) has to throw IOException");
    }

    @Test
    void sneakyThrowTest() {
        assertThrows(IOException.class, () -> sneakyThrow(new IOException()), "sneakyThrow(IOException) has to throw IOException");
    }

    @Test
    void reInterruptInterruptedExceptionTest() {
        assertThrows(InterruptedException.class, new Thread(() -> reInterrupt(new InterruptedException()))::run, "reInterrupt(InterruptedException) has to throw InterruptedException");
    }

    @Test
    void reInterruptOtherExceptionTest() {
        assertDoesNotThrow(() -> reInterrupt(new Exception()), "reInterrupt(Exception) has to run without Exception");
    }

    @Test
    void exceptionTextNullTextTest() {
        Throwable throwable = new LocalizedException(null, null);
        assertEquals(throwable.toString(), Exceptional.exceptionText(throwable), "If exception text and localizedText are null the result has to be equals exception.toString()");
    }

    @Test
    void exceptionTextNullSupplierTest() {
        Throwable throwable = new LocalizedException(null, null);
        assertEquals(throwable.toString(), Exceptional.exceptionText(throwable, () -> null), "If exception text, localizedText and onNull result are null the result has to be equals exception.toString()");
    }

    @Test
    void exceptionTextLocalizedTest() {
        String localized = "Localized...";
        assertEquals(localized, Exceptional.exceptionText(new LocalizedException("Something...", localized)), "Result has to be equals localized text");
    }

    @Test
    void exceptionTextTest() {
        String text = "Exception...";
        assertEquals(text, Exceptional.exceptionText(new LocalizedException(text, null)), "Result has to be equals message if localizedMessage is null");
    }

    @Test
    void exceptionTextSupplierTest() {
        String text = "Exception...";
        assertEquals(text, Exceptional.exceptionText(new LocalizedException(null, null), () -> text), "Result has to be equals onNull().get if localizedMessage and message are nulls");
    }

    private static class LocalizedException extends Exception {

        private static final long serialVersionUID = -1073826040201909263L;
        private final String localizedMessage;

        public LocalizedException(String message, String localizedMessage) {
            super(message);
            this.localizedMessage = localizedMessage;
        }

        @Override
        public String getLocalizedMessage() {
            return this.localizedMessage;
        }

    }

}