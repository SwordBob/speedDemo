package org.seckill.dao;

import static org.junit.Assert.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.Seckill;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
/**
 * 配置spring和junit整合，junit启动时加载springIOC容器
 *  spring-test，junit
 */
@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SeckillDaoTest {
	//注入Dao实现类依赖
	@Resource
	private SeckillDao seckillDao; 
	@Test
	public void testQueryById() throws Exception{
		long id=1;
		Seckill seckill=seckillDao.queryById(id);
		System.out.println(seckill.getName());
		System.out.println(seckill);
	}
	
	@Test
	public void testQueryAll() throws Exception{
		List<Seckill> seckills=seckillDao.queryAll(0,100);
		for (Seckill seckill:seckills){
			System.out.println(seckill);
		}
	}
	@Test
	public void testReduceNumber() throws Exception{
		//Date killTime=new Date();
		DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date killTime = dateFormat2.parse("2016-11-23 22:36:01");
		int updateCount=seckillDao.reduceNumber(1, killTime);
		System.out.println("updateCount="+updateCount);
	}


}
