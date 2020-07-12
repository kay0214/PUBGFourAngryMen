package com.pubg.analysis.repository;

import com.pubg.analysis.entity.Character;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author yangy
 * @date 2020/7/11 22:33
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CharacterRepositoryTest {

	@Autowired
	private CharacterRepository characterRepository;

	private final String matchId = "10d0690b-e837-4aab-8705-62d6a15fc799";

	@Test
	public void getCharacters() {

		List<Character> result = characterRepository.getCharacters(matchId);
		System.out.println(result);
		assertTrue(result != null && result.size() > 0);
	}
}