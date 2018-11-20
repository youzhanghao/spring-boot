//package com.demo.springday.laboratory;
//
//import com.demo.springday.redis.RedisConnector;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import redis.clients.jedis.Jedis;
//
//import java.util.Set;
//
//
///**
// * @Author youzhanghao [m13732916591_1@163.com]
// * @Date 2018/6/11 下午2:17
// */
//public class JedisX {
//
//    private final static Logger logger = LoggerFactory.getLogger(JedisX.class);
//
//    private final static String REDIS_TEST = "REDIS_TEST";
//
//    @Autowired
//    private static RedisConnector redisConnector;
//
//    public void testStartJedis(){
//        Jedis jedis = redisConnector.getConnection();
//        testStoreJedis(jedis);
//    }
//
//    public static void main(String[] args){
//        Jedis jedis = null;
////         连接本地redis服务
//        try{
//            jedis = new Jedis("localhost",6379);
//            logger.info(">>>服务正在运行：{} <<<", jedis.ping());
//        }catch (Exception e){
//            logger.error(">>>redis连接异常<<<",e);
//        }
//        testStoreJedis(jedis);
//    }
//
//    private static void testStoreJedis(Jedis jedis){
//        try{
//            // 存值
//            for (int i = 0; i < 10; i++) {
//                assert jedis != null;
//                jedis.set(REDIS_TEST + i,String.valueOf(i));
//            }
//            // 获取keys
//            Set<String> keys = jedis.keys(REDIS_TEST+"*");
//            logger.info(">>>redis中的{}keys:{}<<<",REDIS_TEST,keys);
//            // 删除keys中的数据
//            Long res = jedis.del("REDIS_TEST7", "REDIS_TEST6");
//            logger.info(">>>redis中的keys:{} 删除:{}个<<<",keys,res);
//            //  redis中的keys:[REDIS_TEST7, REDIS_TEST6, REDIS_TEST9, REDIS_TEST8, REDIS_TEST1, REDIS_TEST0, REDIS_TEST3,
//            // REDIS_TEST2, REDIS_TEST5, REDIS_TEST4] 删除:2个<<<
//            keys.forEach(jedis::del);
//            logger.info(">>>redis中的keys:{}<<<",jedis.keys(REDIS_TEST+"*"));
//        }catch (Exception e){
//            logger.error(">>>redis异常<<<",e);
//        }
//    }
//}
