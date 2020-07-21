package com.pubg.analysis.entity.log;

import com.pubg.analysis.utils.ScaleUtil;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author yangy
 * @date 2020/7/12 15:18
 */
@Data
public class Common implements Serializable {

	/**
	 * 好像是表示游戏阶段
	 */
	private BigDecimal isGame;

    public void setIsGame(BigDecimal isGame) {
        this.isGame = ScaleUtil.threeScale(isGame);
    }
}
