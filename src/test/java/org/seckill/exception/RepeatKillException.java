package org.seckill.exception;
/**
 * 重复秒杀异常(运行期异常)
 * 
 */
public class RepeatKillException extends SeckillExpection {
	 private static final long serialVersionUID = 1L;

	public RepeatKillException(String message) {
	        super(message);
	    }

	    public RepeatKillException(String message, Throwable cause) {
	        super(message, cause);
	    }
	}
