package org.seckill.entity;

import java.util.Date;

public class Seckill1 {

	 private long seckllId;

	    private String name;

	    private int number;

	    private Date startTime;

	    private Date endTime;

	    private Date createTime;

		public long getSeckllId() {
			return seckllId;
		}

		public void setSeckllId(long seckllId) {
			this.seckllId = seckllId;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getNumber() {
			return number;
		}

		public void setNumber(int number) {
			this.number = number;
		}

		public Date getStartTime() {
			return startTime;
		}

		public void setStartTime(Date startTime) {
			this.startTime = startTime;
		}

		public Date getEndTime() {
			return endTime;
		}

		public void setEndTime(Date endTime) {
			this.endTime = endTime;
		}

		public Date getCreateTime() {
			return createTime;
		}

		public void setCreateTime(Date createTime) {
			this.createTime = createTime;
		}

		@Override
		public String toString() {
			return "Seckill [seckllId=" + seckllId + ", name=" + name + ", number=" + number + ", startTime=" + startTime + ", endTime=" + endTime
					+ ", createTime=" + createTime + "]";
		}
	    
	    // 省去 get和set方法
}
