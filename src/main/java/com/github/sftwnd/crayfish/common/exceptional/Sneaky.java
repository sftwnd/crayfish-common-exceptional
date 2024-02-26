package com.github.sftwnd.crayfish.common.exceptional;

import edu.umd.cs.findbugs.annotations.NonNull;

import java.util.concurrent.Callable;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.github.sftwnd.crayfish.common.exceptional.Exceptional.rethrow;
import static com.github.sftwnd.crayfish.common.exceptional.Exceptional.sneakyThrow;

/**
 *  Utility class that makes it easier to work with hiding thrown exceptions
 *  Used sonar warnings:
 *      java:S112   Generic exceptions should never be thrown
 */
public class Sneaky {

    /**
     * Transform Callable to Supplier with sneaky throw
     * @param call the Callable interface implementation
     * @return resulted Supplier
     * @param <V> type of supplier/callable result
     */
    public static <V> Supplier<V> supplier(@NonNull Callable<V> call) {
        return () -> {
            try {
                return call.call();
            } catch (Exception exception) {
                return rethrow(exception);
            }
        };
    }

    /**
     * Transform Functional to Function with sneaky throw
     * @param functional the Functional interface implementation
     * @return resulted Function
     * @param <P> type of function parameter
     * @param <R> type of function result
     */
    public static <P,R> Function<P,R> function(@NonNull Functional<P,R> functional) {
        return parameter -> {
            try {
                return functional.apply(parameter);
            } catch (Exception exception) {
                return rethrow(exception);
            }
        };
    }

    /**
     * Transform BiFunctional to BiFunction with sneaky throw
     * @param functional the Functional interface implementation
     * @return resulted BiFunction
     * @param <P> type of function first parameter
     * @param <Q> type of function second parameter
     * @param <R> type of function result
     */
    public static <P, Q, R> BiFunction<P,Q,R> bifunction(@NonNull BiFunctional<P, Q, R> functional) {
        return (p, q) -> {
            try {
                return functional.apply(p, q);
            } catch (Exception exception) {
                return rethrow(exception);
            }
        };
    }

    /**
     * Transform Processable to Runnable with sneaky throw
     * @param processor the Processable interface implementation
     * @return resulted Runnable
     */
    public static Runnable runnable(@NonNull Processable processor) {
        return () -> {
            try {
                processor.process();
            } catch (Exception exception) {
                sneakyThrow(exception);
            }
        };
    }

    /**
     * Transform Consumable to Consumer with sneaky throw
     * @param consumable the Consumable interface implementation
     * @return resulted Consumer
     * @param <P> type of function parameter
     */
    public static <P> Consumer<P> consumer(@NonNull Consumable<P> consumable) {
        return p -> {
            try {
                consumable.consume(p);
            } catch (Exception exception) {
                sneakyThrow(exception);
            }
        };
    }

    /**
     * Transform BiConsumable to BiConsumer with sneaky throw
     * @param consumable the Consumable interface implementation
     * @return resulted BiConsumer
     * @param <P> type of function first parameter
     * @param <Q> type of function second parameter
     */
    public static <P, Q> BiConsumer<P, Q> biconsumer(@NonNull BiConsumable<P, Q> consumable) {
        return (p, q) -> {
            try {
                consumable.consume(p, q);
            } catch (Exception exception) {
                sneakyThrow(exception);
            }
        };
    }

    /**
     * Runnable analogue of functional interface in which a method can throw a declared exception
     */
    @FunctionalInterface
    public interface Processable {
        /**
         * The general contract of the method <code>process</code> is that it may
         * take any action whatsoever.
         * @throws Exception able to throw Any exception
         */
        void process() throws Exception; //NOSONAR java:S112 Generic exceptions should never be thrown

    }

    /**
     * Consumer analogue of functional interface in which a method can throw a declared exception
     *
     * @param <P> parameter type
     */
    @FunctionalInterface
    public interface Consumable<P> {
        /**
         * Performs this operation on the given argument.
         *
         * @param p the input argument
         * @throws Exception able to throw Any exception
         */
        void consume(P p) throws Exception; //NOSONAR java:S112 Generic exceptions should never be thrown
    }

    /**
     * BiConsumer analogue of functional interface in which a method can throw a declared exception
     *
     * @param <P> first parameter type
     * @param <Q> second parameter type
     */
    @FunctionalInterface
    public interface BiConsumable<P,Q> {
        /**
         * Performs this operation on the given arguments.
         *
         * @param p the first input argument
         * @param q the second input argument
         * @throws Exception able to throw Any exception
         */
        void consume(P p, Q q) throws Exception; //NOSONAR java:S112 Generic exceptions should never be thrown
    }

    /**
     * Function analogue of functional interface in which a method can throw a declared exception
     *
     * @param <P> parameter type
     * @param <R> result type
     */
    @FunctionalInterface
    public interface Functional<P, R> {

        /**
         * Applies this function to the given argument.
         *
         * @param p the function argument
         * @return the function result
         * @throws Exception able to throw Any exception
         */
        R apply(P p) throws Exception; //NOSONAR java:S112 Generic exceptions should never be thrown

    }

    /**
     * BiFunction analogue of functional interface in which a method can throw a declared exception
     *
     * @param <P> first parameter type
     * @param <Q> second parameter type
     * @param <R> result type
     */
    @FunctionalInterface
    public interface BiFunctional<P, Q, R> {

        /**
         * Applies this function to the given arguments.
         *
         * @param p the first function argument
         * @param q the second function argument
         * @return the function result
         * @throws Exception able to throw Any exception
         */
        R apply(P p, Q q) throws Exception; //NOSONAR java:S112 Generic exceptions should never be thrown
    }

    private Sneaky() {
    }

}
