# 装饰者与他的四个角色

## decorator vs. adapter

adapter: 是将一个接口转化为另一个接口(两个接口是不相关的)
decorator: 不改变接口, 只是在原有的接口上加上新的责任和功能

## Java I/O 与装饰者

### Java库的两个对称性

1. 输入输出对称
2. byte-char对称：即InputStream 与 Reader; OutputStream 与 Writer

### 两个设计模式

1. 装饰者模式
2. 适配器模式

### 装饰者模式的角色分配

1. Component :          InputStream
2. Concrete Component : ByteArrayInputStream FileInputStream StringBufferInputStream
3. Decorator :          FilterInputStream
4. Concrete Decorator : DataInputStream BufferInputStream

Reader
1. Reader
2. StringReader PipedReader CharArrayReader FileReader
3. BufferedReader    FilterReader
4. LineNumberReader  PushbackReader

