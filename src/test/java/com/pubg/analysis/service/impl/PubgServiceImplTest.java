package com.pubg.analysis.service.impl;

import com.pubg.analysis.service.IPubgService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author yangy
 * @date 2020/7/12 15:07
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PubgServiceImplTest {

	@Autowired
	IPubgService pubgService;

	private final String matchId = "10d0690b-e837-4aab-8705-62d6a15fc799";

	@Test
	public void getPubgLocation() {
		pubgService.getPubgLocation(matchId);
	}
}