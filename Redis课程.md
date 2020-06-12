# Redis课程

## Redis概述

NoSQL是什么？ Redis MongoDB

基于内存的Key value数据库，主要用于高速缓存，互联网业务的天之骄子

Redis使用单线程模型，为什么？

I/O多路复用（事件驱动） 服务瓶颈不是CPU，一般是磁盘IO，内存IO的速度一般情况下足够应付

## Redis安装与启动配置

Redis曾经短暂的提供过Windows的试用版本到3.0版本（玩具），主版本更新都是Linux版本（原因：多路复用技术最好的底层库是Linux内核的epoll函数）

### 压缩包安装和启动

```conf
# 引入其他文件
include /path/to/local.conf
# 后台启动
daemonize no
# 后台启动pid文件
pidfile /var/run/redis.pid
# 服务端口
port 6379
# IP绑定，注释掉则对所有IP开放
bind 127.0.0.1
# 设置密码，注释掉则为空
requirepass foobared
# 连接空闲关闭时间，0为不关闭
timeout 0
# TCP连接的心跳包发送间隔，0为不发送
tcp-keepalive 0
# 日志等级 debug verbice notice warning
loglevel notice
# 日志文件
logfile ""
# 数据库数目
databases 16

# 快照模型

# 900 秒内如果至少有 1 个 key 的值变化，则保存
# 300 秒内如果至少有 10 个 key 的值变化，则保存
# 60 秒内如果至少有 10000 个 key 的值变化，则保存

save 900 1

save 300 10

save 60 10000
# 快照保存文件
dbfilename dump.rdb
# 工作目录，日志文件和快照文件都存在这里
dir ./
```

启动：`redis-server xxx.conf`

### Redis-cli命令行工具

连接Redis：`redis-cli -p port -h host -a password`

关闭操作：`shutdown`

## Redis数据类型和常用命令

5种基本数据类型，最基础的是String

| 类型           | 储存方式                                                     | 说明                                                 |
| -------------- | ------------------------------------------------------------ | ---------------------------------------------------- |
| STRING字符串   | Redis的最基本数据类型，可以存储数字，最大存储512MB，二进制安全可以存储任意数据 | 可以对字符串进行操作，例如增加，子串等；数值运算操作 |
| LIST列表       | 链表结构，每个节点包含一个字符串                             | Redis有丰富的链表命令，可以双向操作和裁剪            |
| SET集合        | 无序保存数据，每个元素是一个字符串                           | 集合之间可以交、并、差                               |
| HASH哈希散列表 | Map结构，Set的带Key版本                                      |                                                      |
| ZSET有序集合   | 有序保存数据，根据分值大小排序                               |                                                      |

### String

- set key value 增、改
- get key 查
- del key 删
- strlen key 字符串长度
- getset key value 修改并返回旧值
- getrange key start end 字符串截取
- append key value 字符串附加操作
- incr key 若字符串为整数，则加一
- incrby key number 若字符串为整数，则加上给定整数
- decr key 若字符串为整数，则减一
- decrby key number 若字符串为整数，则减去给定整数
- incrbyfloat key number 若字符串为数字，则加上给定浮点数

### LIST

- lpush key v1 v2 ,... 把元素加到链表的最左边
- rpush key v1 v2 ...  把元素加到链表的最右边
- lindex key index 读取下标为index的元素
- llen key 求链表的长度
- lpop key 从左侧弹出一个元素
- rpop key 从右侧弹出一个元素
- linsert key before|after pivot value 在值为pivot的前|后插入一个元素value
- lpushx key value 左侧插入元素value，若key值的list不存在则失败
- rpush key value 右侧插入元素value，若key值的list不存在则失败
- lrange key start end 取key的list的下标从start到end的所有值
- lrem key count value 将list中从左侧开始值为value的count个元素删除，count为0则全删
- lset key index value 设里诶包下标为index的元素值为value（改）
- ltrim key start stop 从左侧截取list，只保留从start到end区间的部分

### SET

- sadd key v1 v2 ... 增
- scard key 元素个数
- sdiff key1 key2 两个set的差集，若只有一个key则返回set全部元素
- sdiffstore newkey key1 key2 讲key1 key2的差集结果保存在newkey的set中
- sinter key1 key2 求交集
- sinterstore newkey key1 key2 将交集储存为newkey的set
- sismember key value 判断value是否为set的成员
- smembers key 返回所有成员
- smove src des member 将成员member从src集合迁移到des集合
- spop key 随机弹出一个元素
- srandmember key count 随机弹出一个或多个元素，通过count指定个数，默认为1
- srem key v1 v2,... 移除元素
- sunion key1 key2 并集
- sunionstore newkey key1 key2 并集存为newkey
- 
### HASH

- hset key field value 增，改
- hmset key f1 v1 f2 v2 多增多改
- hsetnx key field value 只增不改
- hdel key f1,f2,... 删除指定的field字段
- hexists key field 是否存在field字段，返回1 0
- hgetall key 获取所有的field和value
- hkeys key 返回所有的field
- hvals key 获取所有value
- hlen key 获取hash长度
- hmget key f1 f2 ... 获取指定field字段的值
### 储存时间设定

- EXPlRE <key> <ttl> 命令用于将键key 的生存时间设置为ttl 秒。
- PEXPIRE <key> <ttl> 命令用于将键key 的生存时间设置为ttl 毫秒。
-  EXPIREAT <key> < timestamp> 命令用于将键key 的过期时间设置为timestamp所指定的秒数时间戳。
- PEXPIREAT <key> < timestamp > 命令用于将键key 的过期时间设置为timestamp所指定的毫秒数时间戳。
- PERSIST <key>  去除其过期时间
- TTL <key> 查看key的超时时间

## Java操作Redis

Jedis是Java端对Redis的操作驱动包

Jedis对象代表与Redis的连接

JedisPoolConfig连接池设置

JedisPool连接池对象

## Spring整合Redis

Spring官方对Redis的整合Spring Data Redis

设置了连接工厂，封装了对Redis的连接，可以使用Jedis、Jredis、Lettuce、Srp四种Java驱动包

JedisConnectionFactory对象用于配置Jedis驱动的Redis连接

Spring对Redis的操作做了大量封装，构建出RedisTemplate对象对Redis进行操作

因为Redis中存储的基本数据结构为字符串，Spring提供对象序列化器，并提供多种序列化实现

- GenericJackson2JsonRedisSerializer 使用Json2进行JSON序列化

- Jackson2JsonRedisSerializer 使用Jackson2进行JSON序列化

- JdkSerializationRedisSerializer 使用JDK的序列化进行对象序列化
- OxmSerializer 使用Spring O/X进行XML序列化
- StringRedisSerializer 使用字符串序列化
- GenericToStringSerializer 通用的字符串序列化转换

对于Redis的储存需要key和value两部分，因此需要提供keySerializer和valueSerializer两部分，一般key使用字符串序列化，value使用对象序列化，往往使用json序列化，生成的字符串较轻量级



## SpringBoot整合Redis

```xml
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-redis</artifactId>
        </dependency> 
```

```conf
1
# Redis数据库索引（默认为0）  
2
spring.redis.database=0  
3
# Redis服务器地址  
4
spring.redis.host=192.168.0.24  
5
# Redis服务器连接端口  
6
spring.redis.port=6379  
7
# Redis服务器连接密码（默认为空）  
8
spring.redis.password=  
9
# 连接池最大连接数（使用负值表示没有限制）  
10
spring.redis.pool.max-active=200  
11
# 连接池最大阻塞等待时间（使用负值表示没有限制）  
12
spring.redis.pool.max-wait=-1  
13
# 连接池中的最大空闲连接  
14
spring.redis.pool.max-idle=10 
15
# 连接池中的最小空闲连接  
16
spring.redis.pool.min-idle=0  
17
# 连接超时时间（毫秒）  
18
spring.redis.timeout=1000 
```

springboot默认的RedisTemplate的key泛型为Object，而且没有配置我们想要的序列化器，因此建议手动配置RestTemplate的Bean

```java
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
        template.setConnectionFactory(factory);
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        // key采用String的序列化方式
        template.setKeySerializer(stringRedisSerializer);
        // hash的key也采用String的序列化方式
        template.setHashKeySerializer(stringRedisSerializer);
        // value序列化方式采用jackson
        template.setValueSerializer(jackson2JsonRedisSerializer);
        // hash的value序列化方式采用jackson
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }
```



## Spring注解开启数据库Redis缓存

数据库读写依赖磁盘IO，若将数据热缓存到内存中进行读写性能将大大提升

> 互联网业务的瓶颈往往在磁盘IO，而涉及磁盘IO最多的就是数据库读写，因此数据库的IO往往是业务的最终瓶颈

Spring对数据库的操作有缓存机制，利用该机制并将Redis作为缓存器，即可完成Redis对于数据库的缓存

开启缓存需要设置注解`@EnableCaching`并配置CacheManager对象并设定Key生成器对象，因此配置Redis 缓存设置类：

```java
@Configuration
@EnableCaching
public class RedisConfiguration extends CachingConfigurerSupport {
    /**
     * 自定义生成key的规则
     */
    @Override
    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object o, Method method, Object... objects) {
                //格式化缓存key字符串
                StringBuilder sb = new StringBuilder();
                //追加类名
                sb.append(o.getClass().getName());
                //追加方法名
                sb.append(method.getName());
                //遍历参数并且追加
                for (Object obj : objects) {
                    sb.append(obj.toString());
                }
                System.out.println("调用Redis缓存Key : " + sb.toString());
                return sb.toString();
            }
        };
    }
    
    /**
     * 采用RedisCacheManager作为缓存管理器
     * @param connectionFactory
     */
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        RedisCacheManager redisCacheManager = RedisCacheManager.create(connectionFactory);
        return  redisCacheManager;
    }

}
```

对于数据库缓存Spring提供一套完整的注解来完成：

### Spring的缓存注解

- @Cacheable 进入方法前先去缓存查询是否存在
- @CachePut 将方法的返回值存入缓存
- @CacheEvict 移除缓存
- @Caching 用于其他缓存注解

注解属性：

- value 指定方法返回结果保存到的缓存名称

- cacheNames 与value相同

- cacheManager 用于指定缓存管理器

- condition 使用SpringEL返回布尔值，若为false则放弃缓存

- key SpringEL用于计算生产的key值

- keyGenerator 调用的key生成器的名称

- unless 使用SpringEL返回布尔值，若为false则放弃将返回值缓存

简单的SpringEL：

- #xxx xxx为参数名，调用传入的参数
- #result 调用返回值对象

@CacheConfig类注解，可以将该类中的缓存注解的公共部分放在类注解上，包含

- CacheManager
- keyGenerator