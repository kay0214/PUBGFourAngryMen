package com.pubg.analysis.entity.log;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 日志基类
 *
 * @author yangy
 * @date 2020/7/12 9:58
 */
@Data
public class BaseLog {

	/**
	 * 时间
	 */
	private String _D;

	/**
	 * 事件类型
	 */
	private String _T;

	private Common common;
	private Character character;

	/**
	 * 好像是表示游戏阶段
	 */
	@Data
	class Common {

		private float isGame;
	}
}


