package com.pubg.analysis.repository;

import com.pubg.analysis.base.MongoBaseDao;
import com.pubg.analysis.entity.matchs.MatchPlayer;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 日志仓库
 *
 * @author yangy
 * @date 2020/7/12 11:27
 */
@Repository
public class MatchPlayerRepository extends MongoBaseDao<MatchPlayer> {

	@Override
	protected Class<MatchPlayer> getEntityClass() {
		return MatchPlayer.class;
	}

	public List<MatchPlayer> findByMatchId(String matchId){
        Query query = new Query();
        query.addCriteria(new Criteria().and("matchId").is(matchId));
        return find(query);
    }

    public List<MatchPlayer> findByMatchIdAndTeamId(String matchId, String teamId){
        Query query = new Query();
        query.addCriteria(new Criteria().and("matchId").is(matchId).and("teamId").is(teamId));
        return find(query);
    }

    public MatchPlayer findByMatchIdAndAccountId(String matchId, String accountId){
        Query query = new Query();
        query.addCriteria(new Criteria().and("matchId").is(matchId).and("accountId").is(accountId));
        return findOne(query);
    }

    public MatchPlayer findByMatchIdAndPlayerName(String matchId, String playerName){
        Query query = new Query();
        query.addCriteria(new Criteria().and("matchId").is(matchId).and("playerName").is(playerName));
        return findOne(query);
    }
}
