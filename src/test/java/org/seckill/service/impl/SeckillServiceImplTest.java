package org.seckill.service.impl;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillExpection;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
/**
 * 测试类
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
        "classpath:spring/spring-dao.xml",
        "classpath:spring/spring-service.xml"})
public class SeckillServiceImplTest {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
    private SeckillService seckillService;
	@Test
	public void testGetSeckillList() throws Exception {
        List<Seckill> list = seckillService.getSeckillList();
        logger.info("list={}",list);
    }

	@Test
	public void testGetById() throws Exception {
		long id=2000L;
		Seckill seckill=seckillService.getById(id);
		logger.info("seckill={}",seckill);
	}

	//测试代码完整逻辑，注重可重复操作
	@Test
	public void testSeckillLogic() throws Exception{
		long id=2000L;
		Exposer exposer=seckillService.exportSeckillUrl(id);
		if(exposer.isExposed()){
			logger.info("exposer={}",exposer);
			long phone=18272625367L;
			String md5=exposer.getMd5();
			try{
				SeckillExecution seckillExecution=seckillService.executeSeckill(id, phone, md5);
				logger.info("result={}	",seckillExecution);
			}catch (Exception e) {
				// TODO: handle exception
				logger.error(e.getMessage());
			}
		}
	}
	
	@Test
	public void testExportSeckillUrl() {
		//Exposer [exposed=true, md5=0a7448f10f595aa7bcc12f6b15d4908b, seckillId=2000, now=0, start=0, end=0]
		long id=2000;
		Exposer exposer=seckillService.exportSeckillUrl(id);
		logger.info("exposer={}",exposer);
	}
	/**
	 * @throws ParseException 
	 * @throws SeckillExpection 
	 * @throws SeckillCloseException 
	 * @throws RepeatKillException 
	 */
	@Test
	public void testExecuteSeckill() throws RepeatKillException, SeckillCloseException, SeckillExpection, ParseException {
		long id=2000;
		long phone=13502171124L;
		String md5="0a7448f10f595aa7bcc12f6b15d4908b";
		SeckillExecution execution=seckillService.executeSeckill(id, phone, md5);
		logger.info("result={}",execution);
	}

}
