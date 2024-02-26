package com.github.sftwnd.crayfish.common.exceptional;

import edu.umd.cs.findbugs.annotations.NonNull;
import edu.umd.cs.findbugs.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Utility class that simplifies working with exceptions
 */
public class Exceptional {

    /**
     * A function with an expected result of a given type that always throws the passed exception
     * @param throwable throwable exception
     * @return never return value (always throws exception)
     * @param <R> result type
     * @param <T> throwable exception type
     * @throws T throwable exception
     */
    @SuppressWarnings("unchecked")
    public static <R, T extends Throwable> R rethrow(@NonNull Throwable throwable) throws T {
        throw (T) Objects.requireNonNull(throwable, "Exceptional::sneakyThrow - exception is null");
    }

    /**
     * Method always throws the passed exception
     * @param throwable throwable exception
     */
    public static void sneakyThrow(@NonNull Throwable throwable) {
        rethrow(throwable);
    }

    /**
     * Rethrow if throwable is instance of InterruptedException or ignore in any other case
     * @param throwable exception to process
     */
    public static void reInterrupt(@NonNull Throwable throwable) {
        if (throwable instanceof InterruptedException) {
            Thread.currentThread().interrupt();
            sneakyThrow(throwable);
        }
    }

    /**
     * Textual representation of Exception message text
     * @param throwable source exception
     * @return exception text representation
     */
    public static @NonNull String exceptionText(@NonNull Throwable throwable) {
        return exceptionText(throwable, null);
    }

    /**
     * Textual representation of Exception message text
     * @param throwable source exception
     * @param onNull Supplier to make text on null
     * @return exception text representation
     */
    public static @NonNull String exceptionText(@NonNull Throwable throwable, @Nullable Supplier<String> onNull) {
        return Optional.ofNullable(throwable.getLocalizedMessage())
                .filter(Predicate.not(String::isBlank))
                .orElseGet(() -> Optional.ofNullable(throwable.getMessage())
                        .filter(Predicate.not(String::isBlank))
                        .orElseGet(() -> Optional.ofNullable(onNull).map(Supplier::get).orElseGet(throwable::toString)));
    }

    private Exceptional() {
    }

}
