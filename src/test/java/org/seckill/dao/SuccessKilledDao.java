package org.seckill.dao;

import org.seckill.entity.SuccessKilled;

public interface SuccessKilledDao {

	//1. 插入购买明细，可过滤重复：
	int insertSuccessKilled(long seckillId,long userPhone);
	//2. 根据Id查询SuccessKilled并携带秒杀产品对象实体：
	SuccessKilled queryByIdWithSeckill(long seckillId);
}
