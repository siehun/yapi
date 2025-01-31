# 深蓝yAPI



## 项目简介



yapi是一款高效、可靠和安全的接口开放平台，为广大用户提供高质量、可靠、安全的接口服务，帮助用户轻松实现各种功能和数据交互，提高工作效率和用户体验。具体的开发流程和平台的使用流程请参考**开发文档**和**用户手册**。

[yapi平台在线地址](http://8.134.203.203/)

[yapi平台开发文档](https://www.yuque.com/seihun/pcw6qh?# 《yApi平台开发文档》)

[yapi平台用户手册](https://www.yuque.com/seihun/xox1ac?# 《yApi平台用户手册》)

后端采用Spring Cloud SpringBoot 作为业务框架。通过Springcloud Gateway作为全局网关实现流量控制、负载均衡以及路由管理，使用Mybatis-plus作为持久层技术。使用Apache Dubbo做**高性能远程服务调用**。同时Nacos作为注册中心，完成服务注册与发现。

**主要开源组件与版本**

| **item**         | **version**    |
| ---------------- | -------------- |
| **JDK**          | **1.8**        |
| **SpringBoot**   | **2.7.0**      |
| **SpringCloud**  | **2021.0.7.0** |
| **Apache Dubbo** | **3.0.9**      |
| **MySQL**        | **5.8**        |
| **Redis**        | **6.2**        |
| **Mybatis-Plus** | **3.5.5.5**    |
| **Nacos**        | **2.1.0**      |

## 已提供功能

随机古诗词与随机笑话接口

###  示例接口信息

- 接口状态 ： 正常
- 请求方式 ：`GET`
- 返回格式 ：`JSON`

#### 请求地址

```java
http://locahost:8123/Internet/funny
```

#### 请求参数

| 参数名   | 必选 | 类型 | 描述     |
| -------- | ---- | ---- | -------- |
| username | 是   | 无   | 直接请求 |

####  响应参数

| 参数名称 | 类型   | 描述     |
| -------- | ------ | -------- |
| code     | int    | 响应码   |
| data     | string | 幽默笑话 |
| message  | string | 响应描述 |

#### 在线调试示例

![img](https://cdn.nlark.com/yuque/0/2025/png/50672796/1738023103645-1ce66b27-f97a-4463-b3a1-130a9fb33321.png)



#### 代码示例

```java
    @Resource
    private YApiClient yApiClient;
    public String invokefunny() {
        String funny = tempClient.getFunny("需要传的参数");    
        return funny;
    }
```

### 在线平台

### 平台页面

![img](https://cdn.nlark.com/yuque/0/2025/png/50672796/1738331660112-d7c2ab2a-70ab-4cf4-a347-415357529437.png)