package com.pubg.analysis.service.impl;

import com.pubg.analysis.constants.PubgConstant;
import com.pubg.analysis.entity.log.PlayerPositionLog;
import com.pubg.analysis.repository.LogRepository;
import com.pubg.analysis.response.PositionResponse;
import com.pubg.analysis.service.IPubgService;
import com.pubg.analysis.utils.PubgUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author yangy
 * @date 2020/7/12 9:22
 */
@Service
@Slf4j
public class PubgServiceImpl implements IPubgService {

	@Autowired
	private LogRepository logRepository;

	@Override
	public PositionResponse getPubgLocation(String matchId) {

		log.info("获取LogPlayerPosition");
		List<String> type = Collections.singletonList("LogPlayerPosition");
		List<PlayerPositionLog> positionLogs = logRepository.getLog(PlayerPositionLog.class, matchId, type);

		TreeMap<Integer, List<PlayerPositionLog>> logs = new TreeMap<>();
		positionLogs
				.parallelStream()
				//计算位置
				.peek(e -> PubgUtil.calculateLocationRation(e.getCharacter().getLocation(), PubgConstant.Maps.SANHOK))
				//按时间分组
				.collect(Collectors.groupingByConcurrent(PlayerPositionLog::getElapsedTime))

				//每组内按用户去重
				.entrySet()
				.parallelStream()
				.peek(entry -> {
					List<PlayerPositionLog> records = entry.getValue()
							.stream()
							//按角色分组
							.collect(Collectors.groupingBy(v -> v.getCharacter().getAccountId()))
							.entrySet()
							.parallelStream()
							.peek(value -> {
								//只保留一个元素
								List<PlayerPositionLog> same = value.getValue();
								if (!same.isEmpty()) {
									value.setValue(Collections.singletonList(same.get(0)));
								}
							})
							.map(Map.Entry::getValue)
							.flatMap(List::stream)
							.collect(Collectors.toList());
					entry.setValue(records);

				})

				//添加到结果
				.forEachOrdered(e -> logs.put(e.getKey(), e.getValue()));


		log.debug("读取到LogPlayerPosition: {}", logs);

		PositionResponse response = new PositionResponse();
		response.setPositions(logs);
		if (logs.size() > 0) {
			response.setStart(logs.firstKey());
			response.setEnd(logs.lastKey());
		}
		return response;
	}
}
