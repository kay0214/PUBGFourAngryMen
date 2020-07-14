package com.pubg.analysis.repository;

import com.pubg.analysis.base.MongoBaseDao;
import com.pubg.analysis.entity.matchs.MatchPlayer;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 日志仓库
 *
 * @author yangy
 * @date 2020/7/12 11:27
 */
@Repository
public class PlayerMatchRepository extends MongoBaseDao<MatchPlayer> {

	@Override
	protected Class<MatchPlayer> getEntityClass() {
		return MatchPlayer.class;
	}

	public List<MatchPlayer> findByIdOrName(String accountId, String playerName){
        return null;
    }
    public MatchPlayer findByMatchId(String matchId){
        Query query = new Query();
        query.addCriteria(new Criteria().and("matchId").is(matchId));
        return findOne(query);
    }
}
