package com.pubg.analysis.service.impl;

import com.pubg.analysis.service.IPubgApiService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author yangy
 * @date 2020/7/10 16:42
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PubgApiServiceImplTest {

	private static final String matchId1 = "10d0690b-e837-4aab-8705-62d6a15fc799";

	@Autowired
	private IPubgApiService pubgApiService;

	private List<String> names;

	@Before
	public void before() {

		names = new ArrayList<>();
		names.add("NiaoBlush");
	}

	@Test
	public void getMatchesByPlayerNames() {

		List<String> result = pubgApiService.getMatchesByPlayerNames(names);
		Assert.assertTrue(result != null && result.size() > 0);
	}

	@Test
	public void getMatchTelemetryUrl() {

		String url = pubgApiService.getMatchTelemetryUrl(matchId1);
		Assert.assertTrue(url != null && url.length() > 0);
	}
}