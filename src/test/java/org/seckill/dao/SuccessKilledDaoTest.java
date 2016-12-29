package org.seckill.dao;

import static org.junit.Assert.*;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.SuccessKilled;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SuccessKilledDaoTest {
	@Resource
	private SuccessKilledDao successKilledDao;

	@Test
	public void testInsertSuccessKilled() {
		long id =2000L;
        long phone = 18867103574L;
        int insertCount = successKilledDao.insertSuccessKilled(id,phone);
        System.out.println("insertCount="+insertCount);
	}

	@Test
	public void testQueryByIdWithSeckill() {
		long id =2000L;
        long phone = 18867103574L;
        SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(id, phone);
        System.out.println(successKilled);
        System.out.println(successKilled.getSeckill().getSeckillId());
	}

}
