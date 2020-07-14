/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.pubg.analysis.repository;

import com.pubg.analysis.base.MongoBaseDao;
import com.pubg.analysis.entity.matchs.Match;
import com.pubg.analysis.entity.matchs.MatchPlayer;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

/**
 * @author sunpeikai
 * @version MatchRepository, v0.1 2020/7/14 17:29
 * @description
 */
@Repository
public class MatchRepository extends MongoBaseDao<Match>{

    @Override
    protected Class<Match> getEntityClass() {
        return Match.class;
    }

    public Match findByMatchId(String matchId){
        Query query = new Query();
        query.addCriteria(new Criteria().and("matchId").is(matchId));
        return findOne(query);
    }
}
