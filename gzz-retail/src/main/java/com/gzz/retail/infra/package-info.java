package com.gzz.retail.infra;
/**
 * 主要负责提供底层的技术服务
 * infrastructure 基础实施层 最底层（与所有层进行交互）
 * 向其他层提供 通用的 技术能力（比如工具类，第三方库类支持，常用基本配置，数据访问底层实现）
 * 主要包含以下的内容：
 * 为应用层 传递消息（比如通知）
 * 为领域层 提供持久化机制（最底层的实现）
 * 为用户层 提供组件配置
 * 基础实施层还能够通过架构框架为支持四个层次间的交互模式
 *
 * config：配置信息
 * event：事件相关的，如DomainEvent、MQ、等
 * exception： 异常类。
 * repository：存放于数据库相关的，我这里用mybatis， 那么就会有mapper文件、mapper接口等。
 *
 * driver
 * eventbus
 * mq
 *
 * client: 存放夸服务接口。
 * Config： 主要存放配置相关代码。
 * common: 一般存放消息、数据库、缓存、文件、总线、网关、公用的常量、方法、枚举等。
 * Util: 主要存放平台、开发框架、第三方类库、通用算法等基础代码，你可以为不同的资源类别建立不同的子目录。
 */