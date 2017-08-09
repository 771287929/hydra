hydra
=====

Hydra是java开发的分布式跟踪系统。可以接入各种基础组件，完成对业务系统的跟踪。已接入的基础组件是阿里开源的分布式服务框架Dubbo（http://code.alibabatech.com/wiki/display/dubbo/Home-zh）。

Hydra可以针对并发量和数据量的大小选择（需要手动配置），是否使用消息中间件，使用Hbase或是Mysql存储跟踪数据。

Hydra自身提供跟踪数据展现功能，基于angularJS和D3.js。

想了解更多信息，请猛击：http://jd-bdp.github.io/hydra/

提交者：

刘宇：yfliuyu@jd.com     项奎：xiangkui@jd.com     边迪:biandi@jd.com




##重构

对于hydra重新梳理代码结构，以应用于现有的框架系统

重合名 hydra-mysql为hydra-store-mysql
重命名 hydra-hbase 为hydra-store-hbase


修改相应的filter及存储逻辑使其适应restful服务的trace