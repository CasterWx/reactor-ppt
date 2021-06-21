package com.antzuhl.reactor;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * @author AntzUhl
 * @date 2021/6/18 10:36 上午
 * @description 描述该文件做了什么
 **/
public class MonoSamples {

    private static void fromCallableMono() throws InterruptedException {
        Mono.fromCallable(() -> {
            System.out.println("begin callable");
            return "Hello";
        })
                .subscribeOn(Schedulers.elastic())
                .doOnNext((i) -> System.out.println("doOnNext " + i + ", thread :" + Thread.currentThread().getName()))
                .delaySubscriptionMillis(1000)
                .subscribe(System.out::println);
        Thread.sleep(10000);
    }

    private static void fromFutureMono() throws InterruptedException {
        CompletableFuture<String> stringCompletableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("begin");
            return "hello";
        });
        Mono.fromFuture(stringCompletableFuture)
                .subscribeOn(Schedulers.elastic())
                .doOnNext((i) -> System.out.println("doOnNext " + i + ", thread :" + Thread.currentThread().getName()))
                .delaySubscriptionMillis(1000)
                .subscribe(System.out::println);
        Thread.sleep(10000);
    }

    private static void delayMono() throws InterruptedException {
        Mono.delay(Duration.ofSeconds(1)).subscribe(System.out::println);
        Thread.sleep(3000);
    }

    private static void ignoreElementsMono() {
        Mono.ignoreElements((i) -> {
        })
                .doOnNext((i) -> System.out.println("doOnNext " + i))
                .subscribe(System.out::println);
    }

    private static void justOrEmptyMono() {
        Optional<Integer> optional = Optional.empty();
        Mono.justOrEmpty(optional)
                .doOnNext((i) -> System.out.println("doOnNext " + i))
                .subscribe(System.out::println);
    }

    public static void main(String... args) throws Exception {
        justOrEmptyMono();
    }


}