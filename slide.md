title: Project Reactor Quick Start
speaker: AntzUhl

<slide class="bg-black-blue aligncenter"  image="https://projectreactor.io/assets/img/homepage-bg.png .dark">

## Project Reactor 响应式编程 {.text-shadow}

:::column {.ms}

---

By AntzUhl {.alignright}

::: {.text-intro}

<slide class="bg-white">

:::header

![](https://antzyun.oss-cn-beijing.aliyuncs.com/20210620151010.png)

:::

## Reactor Programming {.aligncenter}

---

在计算中，响应式编程或反应式编程是一种面向数据流和变化传播的声明式编程范式。这意味着可以在编程语言中很方便地表达静态或动态的数据流，而相关的计算模型会自动将变化的值通过数据流进行传播。


<slide class="bg-white">

## 为什么使用Project Reactor {.aligncenter}


<slide class="bg-white">



:::header

![](https://antzyun.oss-cn-beijing.aliyuncs.com/20210620151010.png)

:::

:::gallery-overlay

###### 传统方式 {.text-landing.aligncenter}

```java
List<List<String>> partition = Lists.partition(serviceNoList, BATCH_SIZE);
List<Map<String, Object>> result = Lists.newArrayList();
for (List<String> part : partition) {
	List<Map<String, Object>> maps = querySubRefundList(sql, part);
	result.addAll(maps);
}

```

---

###### Reactor方式 {.aligncenter}

```java
Flux.fromIterable(serviceNoList)
	.buffer(BATCH_SIZE)
	.map(subServiceNoList -> querySubRefundList(sql, subServiceNoList))
	.flatMapIterable(identity())
	.collectList()
	.block();
```

:::

<slide class="bg-white">


:::header

Reactor Core API 介绍

:::

### Flux 包含 0 到 N 个元素的异步序列

---

* `just()`：可以指定序列中包含的全部元素。创建出来的 Flux 序列在发布这些元素之后会自动结束。
* `fromArray()`，`fromIterable()`和 `fromStream()`：可以从一个数组、Iterable 对象或 Stream 对象中创建 Flux 对象。
* `empty()`：创建一个不包含任何元素，只发布结束消息的序列。
* `error(Throwable error)`：创建一个只包含错误消息的序列。
* `never()`：创建一个不包含任何消息通知的序列。
* `range(int start, int count)`：创建包含从 start 起始的 count 个数量的 Integer 对象的序列。
* `interval(Duration period)`和 `interval(Duration delay, Duration period)`：创建一个包含了从 0 开始递增的 Long 对象的序列。其中包含的元素按照指定的间隔来发布。除了间隔时间之外，还可以指定起始元素发布之前的延迟时间。
* `intervalMillis(long period)`和 `intervalMillis(long delay, long period)`：与 `interval()`方法的作用相同，只不过该方法通过毫秒数来指定时间间隔和延迟时间。



<slide class="bg-white">


:::header

Reactor Core API 介绍

:::

### Mono 包含 0 到 1 个元素的异步序列

---

* just()、empty()、error() 和 never()
* `fromCallable()`、`fromCompletionStage()`、`fromFuture()`、`fromRunnable()`和 `fromSupplier()`：分别从 Callable、CompletionStage、CompletableFuture、Runnable 和 Supplier 中创建 Mono。
* `delay(Duration duration)和 delayMillis(long duration)`：创建一个 Mono 序列，在指定的延迟时间之后，产生数字 0 作为唯一值。
* `ignoreElements(Publisher<T> source)`：创建一个 Mono 序列，忽略作为源的 Publisher 中的所有元素，只产生结束消息。
* `justOrEmpty(Optional<? extends T> data)`和 `justOrEmpty(T data)`：从一个 Optional 对象或可能为 null 的对象中创建 Mono。只有 Optional 对象中包含值或对象不为 null 时，Mono 序列才产生对应的元素。



<slide class="bg-white">


:::header

Reactor Core API 介绍

:::

### 操作符

---

* `buffer` 和 `bufferTimeout`：把当前流中的元素收集到集合中，并把集合对象作为流中的新元素
* `filter`：对流中包含的元素进行过滤，只留下满足 Predicate 指定条件的元素
* `window`：把当前流中的元素收集到另外的Flux序列中，返回值类型是`Flux<Flux>`
* `combineLatest`：把所有流中的最新产生的元素合并成一个新的元素，作为返回结果流中的元素
* `zipWith`：把当前流中的元素与另外一个流中的元素按照一对一的方式进行合并
* `take`：从当前流中提取元素
* `reduce` 和 `reduceWith`：对流中包含的所有元素进行累积操作
* `merge` 和 `mergeSequential`：把多个流合并成一个Flux序列
* `flatMap` 和 `flatMapSequential`：把流中的每个元素转换成一个流，再把所有流中的元素进行合并
* `concatMap(concatenate)`：把流中的每个元素转换成一个流，再把所有流进行合并



<slide class="bg-white">


:::header

![](https://antzyun.oss-cn-beijing.aliyuncs.com/20210620151010.png)

:::

### Talk is cheap, show me the code {.aligncenter}


<slide class="bg-white">

:::header

![](https://antzyun.oss-cn-beijing.aliyuncs.com/20210620151010.png)

:::

### 相关资源推荐 {.text-landing.aligncenter}

:::gallery-overlay

![](https://antzyun.oss-cn-beijing.aliyuncs.com/20210620150620.png)

## Reactor-Core GitHub地址

了解最新动态

---

![](https://antzyun.oss-cn-beijing.aliyuncs.com/20210620150649.png)

## Reactor 官⽹

可以查阅相关语法以及文档

---

![](https://antzyun.oss-cn-beijing.aliyuncs.com/20210620150751.png)

## Reactor-Core Docs

Reactor-Core官方文档

:::