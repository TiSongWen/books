# 适配器模式

## version1

给出了适配器最初的模型
 Target  : 适配的目标
 Adaptee : 被适配者
 Adapter : 适配器

其中 Adapter拥有被适配者的实例(组合在一起); 且 Adapter同时实现了 Target目标接口

对于适配器而言，同样是利用组合 + 委托的模式

不过其最大的特点是：Client想使用接口 Target。但是之前的系统（Adaptee）却未实现该接口，不想更改原有的系统
所以我们需要引入一个实现了Target接口的适配器，然后组合之前的系统(Adaptee)，把请求委托给之前Adaptee系统


## version2

适配器分成：类适配器 与 对象适配器

## Tomcat与适配器 之 CoyoteAdapter

当时的流程是：

Connector组件收到请求，并解析请求，然后把解析到的 Request 和 Response对象传送给 Container容器（一条责任链）


对于解析的请求： org.apache.coyote.Request 和 org.apache.coyote.Response 要转化为 org.apache.catalina.connector.Request

而对于Servlet标准而言呢， 我们已经有了

标准：ServletRequest --- HttpServletRequest
Tomcat ： org.apache.catalina.connector.Request 实现了 Servlet标准中的 HttpServletRequest接口，同时加了一些Tomcat容器特有的方法
同时还代理了org.apache.coyote.Request的方法. 但这些方法是不能暴露给 开发者的，所以这里又使用了一个外观的模式

Adapter接口中一共就两个方法：
* service
* event


## JDK Enumeration(列举器)
JDK1.1 Vector, Hashtable Stack Arrays

Vector v=new Vector();
for (Enumeration enum =v.elements(); enum.hasMoreElements();) {
    Object o = enum.nextElement();
    processObject(o);
}

JDK1.2 引进了 Iterator(迭代器)
> HashSet HashMap WeakHeahMap ArrayList TreeSet TreeMap LinkedList

List list=new ArrayList();
for(Iterator it=list.iterator();it.hasNext();) {
    System.out.println(it.next());
}

对于老版本的程序，新版本的JDK中 List接口已经没有 elements() , 只有 iterator() 所以为了避免出错，所以需要在 Enumeration 和 Iterator之间增加一个适配器,
将 Iterator转化为 Enumeration
Target: Enumeration
Adaptee: 未实现Enumeration的集合
Adapter: 将旧系统中存在的问题(Enumeration) 转化为 Iterator


## Java I/O 模型
> 看JDK I/O间的继承关系
         InputStream(Target) ： 想要的输入流，但是却不存在
         ByteArrayInputStream : 把 byte[] 适配为 InputStream
         FileInputStream ： 把FileDescriptor 适配为 InputStream


## Spring 与适配器 （未知）

> TODO AOP 切面编程实现

在 Spring 的 AOP 里通过使用的 Advice（通知）来增强被代理类的功能。
Spring 实现这一 AOP 功能的原理就使用代理模式（1、JDK 动态代理。2、CGLib 字节码生成技术代理。）对类进行方法级别的切面增强，
即，生成被代理类的代理类，并在代理类的方法前，设置拦截器，通过执行拦截器中的内容增强了代理方法的功能，实现的面向切面编程。


## 优点

1. 适配器也是一种包装模式，与装饰器拥有同样的包装功能。
2. 适配器属于补偿模式，比较用于后期系统的拓展和修改

## 缺点

使用过多的适配器，会造成整个系统非常凌乱，不易把握其构架。明明调用的是A接口，结果却传到了B接口上。
如果项目需要用到过多的适配器，那么重构也许是个不错的方案。

