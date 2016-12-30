package org.seckill.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.seckill.dao.SeckillDao;
import org.seckill.dao.SuccessKilledDao;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.entity.SuccessKilled;
import org.seckill.enums.SeckillStateEnum;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillExpection;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;


@Service("seckillService")
public class SeckillServiceImpl implements SeckillService {
	private Logger logger=LoggerFactory.getLogger(this.getClass());
	@Autowired
	private SeckillDao seckillDao;
	@Autowired 
	private SuccessKilledDao successKilledDao;
	//md5盐值，用来混淆md5
	private final String slat="123*&*^%%^^(-9lsdjfsjflsyiyiiuiuiu";

	@Override
	public List<Seckill> getSeckillList() {
		return seckillDao.queryAll(0, 4);
	}

	@Override
	public Seckill getById(long seckillId) {
		return seckillDao.queryById(seckillId);
	}

	@Override
	public Exposer exportSeckillUrl(long seckillId) {
		Seckill seckill=seckillDao.queryById(seckillId);
		if(seckill==null){
			return new Exposer(false,seckillId);
		}
		Date startTime =seckill.getStartTime();
		Date endTime=seckill.getEndTime();
		//系统当前时间
		//Date nowTime=new Date();
		DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date nowTime=null;
		try {
			nowTime = dateFormat2.parse("2016-11-23 22:36:01");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if(nowTime.getTime()<startTime.getTime()||nowTime.getTime()>endTime.getTime()){
			return new Exposer(false, seckillId,nowTime.getTime(),startTime.getTime(),endTime.getTime());
		}
		//转化特定字符串的过程，不可逆
		String md5=getMD5(seckillId);
		return new Exposer(true, md5, seckillId);		
	}
	
	private String getMD5(long seckillId){
		String base=seckillId+"/"+slat;
		String md5=DigestUtils.md5DigestAsHex(base.getBytes());
		return md5;
	}

	@Override
	@Transactional
	 /**
     * 使用事务控制事务方法的有点：
     * 1：开发团队达成一致约定，明确标注事务方法的编程格式。
     * 2：保证事务方法的而执行时间尽可能短，不要穿插其他的网络操作，RPC/HTTP请求或者剥离到事务方法外部。
     * 3: 不是所有的方法都需要事务，如只有一条修改操作，只读操作不需要事务控制。
     */
	public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5) throws SeckillExpection, RepeatKillException,
			SeckillCloseException, ParseException {
		if(md5 == null || !md5.equals(getMD5(seckillId))){
            throw new SeckillExpection("seckill data rewrite");
        }
		//执行秒杀逻辑 + 记录购买行为
        //Date nowTime = new Date();
    	DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	Date nowTime = dateFormat2.parse("2016-11-23 22:36:01");
        try {
            int updateCount = seckillDao.reduceNumber(seckillId,nowTime);
            if(updateCount<=0){
                //没有更新到记录
                throw new SeckillCloseException("seckill is close");
            } else {
              //记录购买行为
                int insertCount = successKilledDao.insertSuccessKilled(seckillId,userPhone);
                //唯一：seckillId,userPhone
                if(insertCount<=0){
                    //重复秒杀
                    throw new RepeatKillException("seckill repeat");
                }else {
                    //秒杀成功
                    SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId,userPhone);
                    return new SeckillExecution(seckillId,SeckillStateEnum.SUCCESS,successKilled);
                }
            }
        } catch (SeckillCloseException e1){
            throw e1;
        } catch (RepeatKillException e2) {
            throw e2;
        }catch (Exception e) {
            logger.error(e.getMessage(),e);
            //所有编译期异常转化为运行期异常 (spring事务回滚只对运行期异常起作用)
            throw new SeckillExpection("seckill inner error:"+e.getMessage());
        }
    }
}


