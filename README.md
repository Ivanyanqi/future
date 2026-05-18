# future

`future` 是一个基于 Spring / Spring Boot 的轻量级请求调度与转发框架示例项目，核心能力包括：

- 基于 `functionId` 做请求路由
- 支持拦截器链
- 支持 HTTP 请求适配
- 支持通过 Spring 容器自动装配 handler / interceptor / adapter
- 提供 `future-demo` 作为示例启动工程

## 仓库结构

- `future-core`：核心调度、请求/响应模型、适配器、处理器、拦截器相关实现
- `future-spring`：Spring 集成初始化器
- `future-demo`：Spring Boot 示例应用

## 环境要求

- JDK 8
- Maven 3.6+

## 快速构建

在项目根目录执行：

```bash
mvn clean install -DskipTests
```

如果只想编译校验：

```bash
mvn clean package -DskipTests
```

## 运行示例项目

`future-demo` 是可直接启动的示例模块：

```bash
mvn spring-boot:run -pl future-demo -am
```

启动后默认通过以下接口访问：

```text
GET /api/v1/{functionId}
```

例如：

```bash
curl "http://localhost:8080/api/v1/xxxx"
```

## 配置说明

`future-demo` 通过 `application.properties` 读取路由配置。配置格式以 `functionId` 为前缀，例如：

```properties
xxxx.type=HTTP
xxxx.function=https://example.com/api
xxxx.method=GET
xxxx.responseType=string
```

配置项说明：

- `type`：请求类型，当前代码中 `HTTP` 表示走 HTTP 转发
- `function`：目标地址；若为 HTTP 场景，也可写请求方法，如 `https://example.com/api#POST`
- `method`：请求方法，默认 `GET`
- `interceptors`：拦截器类名列表，逗号分隔
- `responseType`：响应类型，如 `file` 表示文件流

## 示例能力

- 普通 GET 转发
- POST JSON / 表单转发
- 文件下载转发
- 拦截器前置 / 后置处理

## 备注

如果你刚开始接触这个项目，建议先阅读 `QUICK_START.md`，再查看 `future-core` 和 `future-spring` 的实现。