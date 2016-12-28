package org.seckill.dao;

import java.util.Date;
import java.util.List;

import org.seckill.entity.Seckill;

public interface SeckillDao1 {

	//1.减库存，
	int reduceNumber(long seckillId,Date killTime);
	//2. 根据id查询秒杀对象 
	Seckill queryById(long seckillId);
	//3. 根据偏移量查询秒杀商品列表：
	List<Seckill> queryALL(int offset,int limit);
}
