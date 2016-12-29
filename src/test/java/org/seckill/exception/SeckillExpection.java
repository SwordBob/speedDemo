package org.seckill.exception;
/**
 * 秒杀相关业务异常
 * 
 */
public class SeckillExpection extends RuntimeException {
	private static final long serialVersionUID = 1L;
	public SeckillExpection(String message){
		super(message);
	}
	public SeckillExpection(String message, Throwable cause) {
        super(message, cause);
    }
}
