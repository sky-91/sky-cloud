# 模块设计
## 模块定义
### boot
启动模块
(包含@Configuration)
### web
(@Controller、从请求上下文中获取用户信息)
### api
(对外接口以及feignApi调用接口)
### service
(实现事务控制，入参格式必填校验，返回结果需要用CommonResult<XXVO>包装，可以处理失败的情况返回错误消息，
分布式锁)
### dao
(原子类数据库增删改查接口，可以拦截增加缓存配置如redis，实现分页功能)
### common
(@Annotation,Enum,Utils,Constants)
### repository
(Mybatis mapper接口、mapper.xml、多数据库xml支持)
### domain
(定义实体类Data Transfer Objects, DTO)防止下层对象体直接暴露给外部服务，导致底层任何变化都有可能直接传递到外部
--==-->如果只有一个实体对象，那如果表加了一个字段（不想暴露），则直接对外接口也返回了这个字段
View Objects, VO用于对外接口展示

## 模块关系图

![img.png](img.png)
## 其他小功能点
##### 1.使用cglib实现bean的浅拷贝，达到性能最优
##### 2.dynamicNacos动态实时读取nacos上的复杂配置
##### 3.StringOptional扩展Optional判断空字符串
##### 4.使用maven的profile来实现打jar包，war包和指定的数据库驱动包
##### 5.拦截dao层方法，AOP实现对增（雪花ID，创建时间，创建者）、改（修改时间、修改者）的增强
##### 6.全局流水号
##### 7.使用OperatorRouter<Enum, Operator>实现根据枚举值调用不同的处理类
##### 8.pom文件中使用spring-xx-bom来管理spring的相关依赖，自动做到版本兼容
##### 9.使用自动代码生成器生成基于单表的全套代码
##### 10.加密配置文件中的关键信息

# 数据库设计
## 数据库连接池的选择
#### 1.Druid
互联网主流使用，功能全面，性能略低于HikariCP，见 https://blog.csdn.net/wr_java/article/details/129570169
_不需要配置validationQuery，如果不配置的情况下会走ping命令，性能更高_
#### 2.HikariCP（√）
springboot默认使用的连接池，集成文件小，专注连接速度快
connection-test-query可能每个数据库都不一样

## hikariCP连接池配置调优
#### maximumPoolSize:
作用：定义连接池中最大的连接数。<br>
调优建议：根据数据库服务器的硬件资源和应用程序的需求来设定。通常，这个值应该小于数据库服务器的最大连接数限制，但也要考虑并发用户数量和事务的复杂度。初始设置可以设为 CPU 核心数的两倍左右。
#### minimumIdle:
作用：定义连接池中最少的空闲连接数。<br>
调优建议：保持一定数量的空闲连接可以避免在高并发请求时频繁创建新连接。一般设置为 5 到 10 个，具体数值依据系统负载和连接创建的成本。
#### idleTimeout:
作用：定义一个连接在被回收前可以闲置的最大时间（毫秒）。<br>
调优建议：设置过短可能导致连接频繁创建和销毁，增加开销；过长则可能导致资源浪费。通常设置为 30000 到 600000 毫秒（5 到 10 分钟）。
#### connectionTimeout:
作用：定义等待从连接池获取连接的最长时间（毫秒）。<br>
调优建议：如果超过这个时间仍无法获取连接，则抛出异常。一般设置为 30000 毫秒（30 秒），但应根据应用程序的响应时间要求调整。
#### maxLifetime:
作用：定义一个连接在被销毁前的最大生命周期（毫秒）。<br>
调优建议：定期淘汰旧连接有助于避免潜在的数据库资源泄漏。通常设置为 1800000 毫秒（30 分钟）。
#### leakDetectionThreshold:
作用：定义连接未归还给连接池之前的时间阈值（毫秒），超过此阈值将记录警告。<br>
调优建议：有助于检测应用程序中可能存在的连接泄漏。通常设置为 60000 毫秒（1 分钟）。
#### dataSourceProperties:
作用：允许设置额外的数据库连接属性，如 useSSL、serverTimezone 等。<br>
调优建议：根据数据库的配置和应用程序的需求来设置。
#### metricRegistry:
作用：允许与外部监控系统集成，收集和报告连接池的指标。<br>
调优建议：启用监控可以更好地理解连接池的性能瓶颈，从而进行更精细的调优。
#### registerMbeans:
作用：启用 JMX 监控。<br>
调优建议：设置为 true 可以通过 JMX 查看连接池的状态和统计信息。

## hikariCP连接池初始化问题
HikariCP本身就是按需创建连接，并不会在项目启动时，直接创建<br>
导致的问题：第一次操作数据库会因为创建连接池的操作，导致额外的耗时。<br>
解决方案：<br>
1.如果有有引入quartz或者shardingSphere等框架，在启动的时候会去获取数据库连接，这个过程中会触发连接池的初始化动作<br>
2.或者使用如下注入可以在启动时创建连接池<br>
`@Bean
public ApplicationRunner runner(DataSource dataSource) {
return args -> {
log.info("dataSource: {}", dataSource);
Connection connection = dataSource.getConnection();
log.info("connection: {}", connection);
};
}`

## 关于时间字段的类型选择
#### 1.当只需要存储年份、日期、时间时，可以使用year、date、time，尽量使用少的空间

#### 2.datetime性能不错，方便可视化，固定时间，可以在不追求性能、方便可视化、不涉及时区的场景使用

#### 3.timestamp性能较差，存储时间戳，涉及时区转换（如果是系统时区高并发下性能更差），有时间范围限制，还需要为未来准备解决方案（感觉比较鸡肋）

#### 4.bigint性能最好，存储时间戳，不方便可视化，由自己自由转换时区，适合追求性能、国际化（时区转换）、不注重DB可视化的场景，还不用考虑时间范围，如果是短期不会超出2038年XX还可以使用空间更小的int整形
