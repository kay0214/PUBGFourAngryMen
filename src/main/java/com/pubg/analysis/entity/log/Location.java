package com.pubg.analysis.entity.log;

import lombok.Data;

/**
 * @author yangy
 * @date 2020/7/10 9:20
 */
@Data
public class Location {

	/**
	 * x : 123399.4375
	 * y : 121450.4765625
	 * z : 1358.9000244140625
	 */

	private Double x;
	private Double y;
	private Double z;

	private Double xRatio;
	private Double yRatio;

}
