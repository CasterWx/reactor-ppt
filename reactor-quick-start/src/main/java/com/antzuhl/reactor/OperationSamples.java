package com.antzuhl.reactor;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toList;

/**
 * @author AntzUhl
 * @date 2021/6/21 2:55 下午
 * @description 描述该文件做了什么
 **/
public class OperationSamples {

    private static void buffer() {
        Flux.range(1, 100).buffer(20).subscribe(System.out::println);
        Flux.interval(Duration.of(0, ChronoUnit.SECONDS),
                Duration.of(1, ChronoUnit.SECONDS))
                .buffer(Duration.of(5, ChronoUnit.SECONDS)).
                take(2).toStream().forEach(System.out::println);

        Flux.range(1, 200).buffer(20)
                .doOnNext(i -> {
                    System.out.println("doOnNext i:" + i);
                }).flatMapIterable(identity())
                .subscribe(i -> {
                    System.out.println("subscribe i:" + i);
                });
    }

    private static void window() {
        Flux.range(1, 200).window(20)
                .doOnNext(i -> {
                    System.out.println("[doOnNext] " + i);
                })
                .subscribe(i -> {
                    i.subscribe((x) -> {
                        System.out.println("subscribe " + x);
                    });
                });
    }

    private static void filter() {
        Flux.range(1, 20).filter(i -> i%2==0)
                .doOnNext(i -> {
                    System.out.println("[doOnNext] " + i);
                })
                .subscribe(i -> {
                    System.out.println("subscribe " + i);
                });
    }

    private static void take() {
        Flux.range(1, 10).take(2).subscribe(System.out::println);
        Flux.range(1, 10).takeLast(2).subscribe(System.out::println);
        Flux.range(1, 10).takeWhile(i -> i < 5).subscribe(System.out::println);
        Flux.range(1, 10).takeUntil(i -> i == 6).subscribe(System.out::println);
    }

    private static void zip() {
        Flux.just("I", "You")
                .zipWith(Flux.just("Win", "Lose"))
                .subscribe(System.out::println);
        Flux.just("I", "You")
                .zipWith(Flux.just("Win", "Lose"),
                        (s1, s2) -> String.format("%s!%s!", s1, s2))
                .subscribe(System.out::println);
    }

    private static void combineLatest() {
        Flux.combineLatest(
                Arrays::toString,
                Flux.interval(Duration.of(0, ChronoUnit.MILLIS),
                        Duration.of(100, ChronoUnit.MILLIS)).take(2),
                Flux.interval(Duration.of(50, ChronoUnit.MILLIS),
                        Duration.of(100, ChronoUnit.MILLIS)).take(2)
        ).toStream().forEach(System.out::println);
    }

    private static void merge() {
        Flux.merge(Flux.interval(
                Duration.of(0, ChronoUnit.MILLIS),
                Duration.of(100, ChronoUnit.MILLIS)).take(2),
                Flux.interval(
                        Duration.of(50, ChronoUnit.MILLIS),
                        Duration.of(100, ChronoUnit.MILLIS)).take(2))
                .toStream()
                .forEach(System.out::println);
        System.out.println("---");
        Flux.mergeSequential(Flux.interval(
                Duration.of(0, ChronoUnit.MILLIS),
                Duration.of(100, ChronoUnit.MILLIS)).take(2),
                Flux.interval(
                        Duration.of(50, ChronoUnit.MILLIS),
                        Duration.of(100, ChronoUnit.MILLIS)).take(2))
                .toStream()
                .forEach(System.out::println);
    }

    private static void flatMap() {
        Flux.just(1, 2)
                .flatMap(x -> Flux.interval(Duration.of(x * 10, ChronoUnit.MILLIS),
                        Duration.of(10, ChronoUnit.MILLIS)).take(x))
                .toStream()
                .forEach(System.out::println);
    }

    private static void reduce() {
        Flux.range(1, 50).doOnNext(i -> {
            System.out.println("doOnNext " + i);
        }).reduce((x, y) -> {
            System.out.println("x:" + x + ", y:" + y);
            return x+y;
        }).subscribe(System.out::println);

        System.out.println("-------");

        Flux.range(1, 50).doOnNext(i -> {
            System.out.println("doOnNext " + i);
        }).reduceWith(() -> 100, (x, y) -> {
            System.out.println("x:" + x + ", y:" + y);
            return x+y;
        }).subscribe(System.out::println);
    }

    private void restoringMissingLetter() {
        Mono<String> missing = Mono.just("s");
        Flux<String> allLetters = Flux
                .fromIterable(Arrays.asList("main", "split"))
                .flatMap(word -> Flux.fromArray(word.split("")))
                .concatWith(missing)
                .distinct()
                .sort()
                .zipWith(Flux.range(1, Integer.MAX_VALUE),
                        (string, count) -> String.format("%2d. %s", count, string));

        allLetters.subscribe(System.out::println);
    }

    private static void shortCircuit() throws InterruptedException {
        Flux<String> helloPauseWorld =
                Mono.just("Hello")
                        .concatWith(Mono.just("world")
                                .delaySubscriptionMillis(500));
        helloPauseWorld.subscribe(System.out::println);
    }

    private static void blocks() {
        Flux<String> helloPauseWorld =
                Mono.just("Hello")
                        .concatWith(Mono.just("world")
                                .delaySubscriptionMillis(500));

        helloPauseWorld.toStream()
                .forEach(System.out::println);
    }

    private static void firstEmitting() {
        Mono<String> a = Mono.just("oops I'm late")
                .delaySubscriptionMillis(450);
        Flux<String> b = Flux.just("let's get", "the party", "started")
                .delayMillis(400);

        Flux.firstEmitting(a, b)
                .toIterable()
                .forEach(item -> {
                    System.out.println(System.currentTimeMillis() + " ------- " + item);
                });
    }

    private static void findingMissingLetter() {
        Flux<String> manyLetters = Flux
                .fromIterable(Arrays.asList("main", "split"))
                .flatMap(word -> Flux.fromArray(word.split("")))
                .distinct()
                .sort()
                .zipWith(Flux.range(1, Integer.MAX_VALUE),
                        (string, count) -> String.format("%2d. %s", count, string));

        manyLetters.subscribe(System.out::println);
    }

    public static void main(String[] args) {
        reduce();
    }
}
