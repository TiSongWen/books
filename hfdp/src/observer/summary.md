# Chapter 2 : The Observer Pattern : Keeping your Objects in the know

## version_1 vs. version_2

version_1 是自己设计的 Observer Pattern
version_2 是用JDK中的Observer Patthern


## 观察者中的一对多

1. Subject --- Observer 绑定思路是 :

```
Observer o = new Observer();
subject.register(o);
```

对于Observer对象来说, 是并不知道自己和哪一个Subject相关联

2. Observable --- Observer

```
Observer o = new Observer(subject);

public Oberser(Subject subject) {
    this.subject = subject;
    subject.register(this);
}
```

双方都存在引用

## Code tips

1. 关于setChanged的作用
2. 注册与绑定观察者
3. 不要依赖观察者的绑定顺序


## 原则

1. 面向实现编程 --- 面向接口编程

2. 多用组合，少用继承
>Java不支持多继承的缺点, 也是JDK中 Observable 是一个类而导致的局限性

## JDK 使用

1. 通知观察者时，有两种形式
    * push
    * pull


# 额外篇 Tomcat中的观察者模式

Lifecycle --- LifecycleListener --- LifecycleEvent

Lifecycle(Subject)
 | -- StandardEngine
 | -- StandardHost
 | -- StandardContext
 | -- ContainerBase
LifecycleListener(定义了观察者想要做的事情, 对哪个动作感兴趣)
 | -- EngineConfig
 | -- HostConfig
 | -- ContextConfig
 | -- MapperListener
LifecycleSupport (组合的模式放在Lifecycle实现类中)
 |
LifecycleEvent(观察者要监听的事件)

> 对于Tomcat中的观察者模式而言, 只要有事件到来, 都会notify所有绑定的观察者, 但是观察者不一定监听了该事件(在观察者[ContextConfig]的lifecycleEvent方法中会判断传递过来的是什么事件)

**Lifecycle**
1. 定义了生命周期的相关事件(String START_EVENT = "start";)

2. 观察者的管理与通知方法

3. start() 与 stop()

**LifecycleSupport**
Lifecycle中应该实现的Subject方法抽离出来放在了LifecycleSupport(再利用组合的模式与Lifecycle实现类结合)


