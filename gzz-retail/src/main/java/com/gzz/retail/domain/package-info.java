package com.gzz.retail.domain;
/**
 * 领域层
 * domain 领域层
 * 领域层主要负责表达业务概念，业务状态信息和业务规则。
 * Domain层是整个系统的核心层，几乎全部的业务逻辑会在该层实现。
 * <p>
 * 主要包含以下的内容：
 * <p>
 * 领域服务（Domain Services）：一些行为无法归类到实体对象或值对象，本质是一些操作，而非事物。
 * 聚合/ 聚合根（Aggregates,Aggregate Roots）：聚合是指一组具有内聚关系的相关对象的集合，每个聚合都有一个ROOT和Boundary
 * <p>
 * <p>
 * 类型 	      英文 	         描述
 * 值对象 	      value object 	 无唯一标识的简单对象
 * 实体 	      entity 	     充血的领域模型，有唯一标识的对象
 * 聚合（聚合根）  aggregate 	 实体的聚合，拥有聚合根，可为某一个实体
 * 领域服务 	      service 	     无法归类到某个具体领域模型的行为
 * 领域事件 	      event 	     不常用
 * 仓储 	      repository 	 持久化相关，与基础设施层关联
 * 工厂 	      factory 	     负责复杂对象创建
 * 模块 	      module 	     子模块引入，可以理解为子域划分
 * <p>
 * <p>
 * #########################################################################################################
 * domain层包含entity（实体）、valueobject(值对象)、domain event（领域事件）和repository（仓储）
 */