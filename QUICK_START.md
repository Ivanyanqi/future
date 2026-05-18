# 快速启动

本文档用于帮助你在本地快速构建、运行并理解 `future` 项目。

## 1. 准备环境

请先确认本机已安装：

```bash
java -version
mvn -version
```

推荐版本：

- Java 8
- Maven 3.6+

## 2. 获取代码

```bash
git clone git@github.com:Ivanyanqi/future.git
cd future
git checkout master
```

## 3. 项目结构

```text
future
├── future-core      # 核心调度、请求模型、响应模型、handler、adapter、interceptor
├── future-spring    # Spring 容器集成
├── future-demo      # Spring Boot 示例工程
└── pom.xml          # Maven 聚合工程
```

## 4. 构建项目

在仓库根目录执行：

```bash
mvn clean install -DskipTests
```

如果只需要编译打包：

```bash
mvn clean package -DskipTests
```

## 5. 启动 demo

```bash
mvn spring-boot:run -pl future-demo -am
```

启动成功后，服务默认监听：

```text
http://localhost:8080
```

## 6. 配置转发函数

`future-demo` 中通过 `FutureSpringInitializer` 指定读取 `application.properties`：

```java
futureSpringInitializer.setConfigPath("application.properties");
```

你可以在 `future-demo/src/main/resources/application.properties` 中添加类似配置：

```properties
xxxx.type=HTTP
xxxx.function=https://example.com/api
xxxx.method=GET
xxxx.responseType=string
```

字段含义：

| 字段 | 说明 |
| --- | --- |
| `type` | 请求类型，HTTP 场景下配置为 `HTTP` |
| `function` | 实际请求目标地址 |
| `method` | 请求方法，默认 `GET` |
| `interceptors` | 拦截器类名列表，多个用逗号分隔 |
| `responseType` | 响应类型，`file` 会按文件流处理 |

## 7. 访问接口

`future-demo` 暴露统一入口：

```text
GET /api/v1/{functionId}
```

示例：

```bash
curl "http://localhost:8080/api/v1/xxxx"
```

请求进入后大致流程：

1. Controller 根据路径变量创建 `HttpFutureRequest`
2. `FutureDispatcher` 根据 `functionId` 查找 `HandlerExecuteChain`
3. 执行匹配的前置拦截器
4. 通过 `HandlerAdapter` 调用具体 handler
5. 执行后置拦截器
6. 返回 `FutureResponse` 中的响应内容

## 8. 添加拦截器

实现 `FutureInterceptor`，并交给 Spring 管理：

```java
@Component
@Interceptor("xxxx")
public class DemoInterceptor implements FutureInterceptor {
    @Override
    public boolean preHandle(FutureRequest request, FutureResponse response, Object handler) {
        return true;
    }

    @Override
    public void postHandle(FutureRequest request, FutureResponse response, Object handler) {
    }
}
```

拦截器可以通过两种方式绑定：

- 使用 `@Interceptor("functionId")`
- 在配置中通过 `interceptors` 指定拦截器类名

## 9. 常见问题

### 找不到 functionId

请检查 `application.properties` 中是否存在对应前缀的配置，例如：

```properties
xxxx.type=HTTP
xxxx.function=https://example.com/api
```

### Maven 构建失败

优先确认：

- JDK 是否为 8 或兼容版本
- Maven 是否可访问中央仓库
- 是否在仓库根目录执行命令

### demo 启动后访问无返回

请检查目标 `function` 地址是否可访问，以及 method / content-type 是否匹配。