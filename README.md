hydra
=====

Hydra是java开发的分布式跟踪系统。可以接入各种基础组件，完成对业务系统的跟踪。已接入的基础组件是阿里开源的分布式服务框架Dubbo（http://code.alibabatech.com/wiki/display/dubbo/Home-zh）。

Hydra可以针对并发量和数据量的大小选择（需要手动配置），是否使用消息中间件，使用Hbase或是Mysql存储跟踪数据。

Hydra自身提供跟踪数据展现功能，基于angularJS和D3.js。

想了解更多信息，请猛击：http://jd-bdp.github.io/hydra/

提交者：

刘宇：yfliuyu@jd.com     项奎：xiangkui@jd.com     边迪:biandi@jd.com




## 重构

### 已完成
对于hydra重新梳理代码结构，以应用于现有的框架系统: 

1. 重命名 hydra-mysql为hydra-store-mysql;
1. 重命名 hydra-hbase 为hydra-store-hbase;
1. 增加HydraFilter2以适应dubbox restful服务的trace记录;
	
	 dubbox rest服务，供前端html5使用，对于本次trace是没有客户端发送(cs)、客户端接收(crs)；而只有服务端接收(cr)、服务端发送(ss)。对于原始的框架，认为这是一个不完整的trace，所以不会进行trace。所以进行修改相应的逻辑，以增加这部分的trace记录。

2. 修改Annotation模型增加相应的业务参数trace;
1. 修改hydra-store-mysql中的InsertServiceImpl的存储逻辑使其适应restful服务的trace;
		
	对于本次改造使用mysql数据库作为trace log的直接存储，而没有使用消息队列。
	
2. 修改hydra-web中关于trace展示的判断;
3.  而对于hydra-manager-db中实现，只保证的单机的并发性，而对于集群并没有提供保证(对于这部分功能解决方案即 自旋+单数据库乐观锁来解决并发的问题,服务的数据量目前来说单库的存储量是可以容纳的)，考虑到这个服务并发性并不很高，就不重构了;


### 使用方式

#### 应用
1. 引入相应的hydra-client jar
			
		<dependency>
			<groupId>com.jd.bdp</groupId>
			<artifactId>hydra-client</artifactId>
			<version>1.0-SNAPSHOT</version>
		</dependency>

1. 将引入jar包中的spring 配置文件 

		<import resource="classpath*:META-INF/spring/hydra-config.xml"/>
	
#### hydra-collector
		更改mysql的地址，以及相应的注册中心地址
		命令 sh collector-mysql.sh start

#### hydar-manager

	更改mysql的地址，以及相应的注册中心地址
	启动命令  sh manager.sh start

#### hydar-web

	更改mysql的地址，将war部署到tomcat容器运行;
   
### 后期计划

1. 将metaq更换掉，或者新增其他的消息队列，因公司不使用它;
2. hydra-store-hbase代码的梳理与重构;
3. 增强hydra-web的功能;









