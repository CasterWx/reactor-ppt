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


:::header

![](https://antzyun.oss-cn-beijing.aliyuncs.com/20210620151010.png)

:::

### Talk is cheap, show me the code {.aligncenter}


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