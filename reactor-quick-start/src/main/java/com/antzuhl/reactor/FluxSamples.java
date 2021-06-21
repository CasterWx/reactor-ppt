package com.antzuhl.reactor;

import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toList;

/**
 * @author AntzUhl
 * @date 2021/6/21 11:12 上午
 * @description Flux Demo
 **/
public class FluxSamples {

    private static final List<String> arr = Arrays.asList("flux", "mono", "reactor", "core");

    private static void justFlux() {
        Flux.just(arr).doOnNext((i) -> {
            System.out.println("[doOnNext] " + i);
        })
        .map(i -> i.stream().map(String::toUpperCase).collect(toList()))
        .flatMapIterable(identity())
                .collectList().subscribe(i -> System.out.println("[subscribe] " + i));
    }

    private static void fromIterableFlux() {
        Flux.fromIterable(arr).doOnNext((i) -> {
            System.out.println("[doOnNext] " + i);
        }).subscribe((i) -> {
            System.out.println("[subscribe] " + i);
        });
    }

    private static void emptyFlux() {
        Flux.empty().doOnNext(i -> {
            System.out.println("[doOnNext] " + i);
        }).doOnComplete(() -> {
            System.out.println("[DoOnComplete] ");
        }).subscribe(i -> {
            System.out.println("[subscribe] " + i);
        });
    }

    private static void errorFlux() {
        try {
            int []arr = new int[5];
            arr[10] = 2;
        } catch (Exception e) {
            Flux.error(e).subscribe(i -> {
                System.out.println("error subscribe");
            });
        }
    }

    private static void neverFlux() {
        Flux.never().doOnNext(i -> {
            System.out.println("doOnNext " + i);
        }).doOnComplete(() -> {
            System.out.println("doOnComplete");
        }).subscribe((i) -> {
            System.out.println("subscribe " + i);
        });
    }

    private static void rangeFlux() {
        Flux.range(5, 10).doOnNext(i -> {
            System.out.println("doOnNext " + i);
        }).doOnComplete(() -> {
            System.out.println("doOnComplete");
        }).subscribe((i) -> {
            System.out.println("subscribe " + i);
        });
    }

    private static void intervalFlux() {
        Flux.interval(Duration.ofSeconds(4), Duration.ofSeconds(2)).doOnNext(i -> {
            System.out.println("doOnNext " + i);
        }).doOnComplete(() -> {
            System.out.println("doOnComplete " + new Date());
        }).subscribe((i) -> {
            System.out.println("subscribe " + i + ", date: " + new Date());
        });
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void generateFlux() {
        Flux.generate(() -> 0, (id, sink) -> {
            id ++;
            sink.next(id);
            if (id > 10) {
                sink.complete();
            }
            return id;
        }).doOnNext(i -> {
            System.out.println("doOnNext " + i);
        }).doOnComplete(() -> {
            System.out.println("doOnComplete");
        }).subscribe((i) -> {
            System.out.println("subscribe " + i);
        });
    }

    private static void createFlux() {
        Flux.create((sink) -> {
            for (int i = 0; i < 10; i++) {
                sink.next(i);
            }
            sink.complete();
        }).doOnNext(i -> {
            System.out.println("doOnNext " + i);
        }).doOnComplete(() -> {
            System.out.println("doOnComplete");
        }).subscribe((i) -> {
            System.out.println("subscribe " + i);
        });
    }

    public static void main(String[] args) {
        createFlux();
    }
}
