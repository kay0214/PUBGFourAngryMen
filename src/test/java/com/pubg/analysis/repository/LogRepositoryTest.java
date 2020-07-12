package com.pubg.analysis.repository;

import com.pubg.analysis.entity.log.PlayerPositionLog;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author yangy
 * @date 2020/7/12 13:32
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class LogRepositoryTest {

	@Autowired
	private LogRepository logRepository;

	private final String matchId = "10d0690b-e837-4aab-8705-62d6a15fc799";

	@Test
	public void getLog() {

		List<String> types = Arrays.asList("LogPlayerPosition");
		List<PlayerPositionLog> logs = logRepository.getLog(PlayerPositionLog.class, matchId, null);
		int size1 = logs.size();
		Assert.assertTrue(logs.size() > 0);

		logs = logRepository.getLog(PlayerPositionLog.class, matchId, types);
		int size2 = logs.size();
		Assert.assertTrue(logs.size() > 0);

		Assert.assertTrue(size1 > size2);
	}
}