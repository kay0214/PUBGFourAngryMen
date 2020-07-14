package com.pubg.analysis.entity.log;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yangy
 * @date 2020/7/12 10:24
 */
@NoArgsConstructor
@Data
public class Vehicle {

	/**
	 * vehicleType : WheeledVehicle
	 * vehicleId : BP_TukTukTuk_A_01_C
	 * vehicleUniqueId : 288234
	 * seatIndex : 0
	 * healthPercent : 100
	 * feulPercent : 12.007209777832031
	 * altitudeAbs : 0
	 * altitudeRel : 0
	 * velocity : 2077.751220703125
	 * isWheelsInAir : false
	 * isInWaterVolume : false
	 * isEngineOn : true
	 */

	private String vehicleType;
	private String vehicleId;
	private Integer vehicleUniqueId;
	private Integer seatIndex;
	private Integer healthPercent;
	private double feulPercent;
	private Integer altitudeAbs;
	private Integer altitudeRel;
	private double velocity;
	private boolean isWheelsInAir;
	private boolean isInWaterVolume;
	private boolean isEngineOn;
}
