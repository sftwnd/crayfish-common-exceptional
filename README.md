[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=sftwnd_crayfish_common_exceptional&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=sftwnd_crayfish_common_exceptional) [![Coverage](https://sonarcloud.io/api/project_badges/measure?project=sftwnd_crayfish_common_exceptional&metric=coverage)](https://sonarcloud.io/summary/new_code?id=sftwnd_crayfish_common_exceptional) [![License](https://img.shields.io/github/license/sftwnd/crayfish-common-exceptional)](https://github.com/sftwnd/crayfish-common-exceptional/blob/master/LICENSE)
# Crayfish :: Common :: Exceptional

Utilities to simplify work with Exceptional information

## Exceptional

Utility class that simplifies working with exceptions

### rethrow

A function with an expected result of a given type that always throws the passed exception

### sneakyThrow

Method always throws the passed exception

### reInterrupt

Rethrow if throwable is instance of InterruptedException or ignore in any other case

### cause

Getting a significant cause from a Runtime Exception

### exceptionText

Textual representation of Exception message text

## Sneaky

Utility class that makes it easier to work with hiding thrown exceptions

### supplier

Transform Callable to Supplier with sneaky throw

```java
 public static <V> Supplier<V> supplier(@NonNull Callable<V> call);
```

### function

Transform Functional to Function with sneaky throw

```java
 public static <P,R> Function<P,R> function(@NonNull Functional<P,R> functional);
```

### bifunction

Transform BiFunctional to BiFunction with sneaky throw

```java
 public static <P, Q, R> BiFunction<P,Q,R> bifunction(@NonNull BiFunctional<P, Q, R> functional);
```

### runnable

Transform Processable to Runnable with sneaky throw

```java
 public static Runnable runnable(@NonNull Processable processor);
```

### consumer

Transform Consumable to Consumer with sneaky throw

```java
 public static <P> Consumer<P> consumer(@NonNull Consumable<P> consumable);
```

### biconsumer

Transform BiConsumable to BiConsumer with sneaky throw

```java
 public static <P, Q> BiConsumer<P, Q> biconsumer(@NonNull BiConsumable<P, Q> consumable);
```

---
Copyright © 2017-2024 Andrey D. Shindarev. All rights reserved.
