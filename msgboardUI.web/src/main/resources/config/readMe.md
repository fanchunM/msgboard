所修改的配置文件：1、db.springdata.mongodb.applicationContext.xml
		   2、dubbo.properties
		   3、mongo.properties
		   4、secure.redis.applicationContext.xml
		   5、suexing.properties

1、db.springdata.mongodb.applicationContext.xml：
	公司本地地址： <mongo:mongo-client id="mongoclient" host="${mongo.host}" port="${mongo.port}">
	测试及正式地址：<mongo:mongo-client id="mongoclient" host="${mongo.host}" port="${mongo.port}"  credentials="${mongo.username}:${mongo.password}@${mongo.dbname}">
	
2、dubbo.properties：
	公司本地地址：zookeeper.connect=dev1.dev.mine.com:2181
	测试地址：zookeeper.connect=10.10.11.130:2181
	正式地址：zookeeper.connect=10.10.10.183:2181
	
3、mongo.properties：
	公司本地地址mongodb： mongo.host=db3.dev.mine.com
					mongo.dbname=webmongodemo
	公司测试及正式mongdb： mongo.dbname=msgboardmongo
				    mongo.username=msgboard
					mongo.password=msgboard
	测试地址mogodbIp：mongo.host=10.10.11.130
	正式地址mogodbIp：mongo.host=10.10.10.183

4、secure.redis.applicationContext.xml：
	公司本地地址：<bean class="org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory" p:host-name="dev1.dev.mine.com" p:port="6379" />
	测试地址：<bean class="org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory" p:host-name="10.10.11.130" p:port="6379" />
	正式地址：<bean class="org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory" p:host-name="10.10.10.183" p:port="6379" />
	
5、suexing.properties：
	公司本地地址：mobileHomePage=http://localhost/msgboard/mobile_home
	测试地址：mobileHomePage=http://10.10.10.141:8998/msgboard/mobile_home
	正式地址：mobileHomePage=http://bbs.sz-mtr.com/mobile_home	