package com.pubg.analysis.entity.log;

import lombok.Data;

import java.io.Serializable;

/**
 * @author yangy
 * @date 2020/7/12 15:18
 */
@Data
public class Common implements Serializable {

	/**
	 * 好像是表示游戏阶段
	 */
	private Double isGame;
}
