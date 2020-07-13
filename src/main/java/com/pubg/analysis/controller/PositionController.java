package com.pubg.analysis.controller;

import com.pubg.analysis.response.PositionResponse;
import com.pubg.analysis.service.IPubgService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 位置控制器
 *
 * @author yangy
 * @date 2020/7/13 10:12
 */
@Api(value = "pubg位置", tags = "pubg数据接口")
@RestController
@RequestMapping("/position")
public class PositionController {

	@Autowired
	private IPubgService pubgService;

	@ApiOperation(value = "获取位置信息", notes = "获取位置信息2")
	@GetMapping("/track/{matchId}")
	public PositionResponse getTrack(@PathVariable String matchId) {

		return pubgService.getPubgLocation(matchId);
	}
}
