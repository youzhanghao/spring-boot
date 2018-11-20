//package com.demo.springday;
//
//import com.demo.springday.util.HashRedisUtil;
//import net.bytebuddy.asm.Advice;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//
//public class ApplicationTests {
//
//
//	@Autowired
//	private HashRedisUtil hashRedisUtil;
//	@Test
//	public void contextLoads() {
//	}
//
//	@Test
//	public void testReids(){
//		try{
//
//
//
//			System.out.println(hashRedisUtil.set("keyTest","valueTest"));
//			System.out.println(hashRedisUtil.get("keyTest"));
//
//			System.out.println(hashRedisUtil.set("keyTest02","valueTest02"));
//			System.out.println(hashRedisUtil.get("keyTest02"));
//		}catch (Exception e){
//			e.printStackTrace();
//		}finally {
//
//		}
//	}
//
//}
