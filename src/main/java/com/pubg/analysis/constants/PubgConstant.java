package com.pubg.analysis.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * pubg 常量
 *
 * @author yangy
 * @date 2020/7/12 0:48
 */
public class PubgConstant {

	/**
	 * 8x8地图边长
	 * 厘米
	 */
	private static final long MAP_SIDE_LENGTH_8X8_CM = 8 * 1000 * 100;

	/**
	 * 6x6地图边长
	 * 厘米
	 */
	private static final long MAP_SIDE_LENGTH_6X6_CM = 6 * 1000 * 100;

	/**
	 * 4x4地图边长
	 * 厘米
	 */
	private static final long MAP_SIDE_LENGTH_4X4_CM = 4 * 1000 * 100;

	/**
	 * 地图
	 */
	@Getter
	@AllArgsConstructor
	public enum Maps {

		/**
		 * 艾伦格
		 */
		ERANGEL(0, MAP_SIDE_LENGTH_8X8_CM, MAP_SIDE_LENGTH_8X8_CM),

		/**
		 * 米拉玛
		 */
		MIRAMAR(1, MAP_SIDE_LENGTH_8X8_CM, MAP_SIDE_LENGTH_8X8_CM),

		/**
		 * 萨诺
		 */
		SANHOK(2, MAP_SIDE_LENGTH_4X4_CM, MAP_SIDE_LENGTH_4X4_CM),

		/**
		 * 维寒迪
		 */
		VIKENDI(3, MAP_SIDE_LENGTH_6X6_CM, MAP_SIDE_LENGTH_6X6_CM);

		/**
		 * 地图编号
		 */
		private final int code;

		/**
		 * 宽
		 * 厘米
		 */
		private final long width;

		/**
		 * 高
		 * 厘米
		 */
		private final long height;

	}

}
