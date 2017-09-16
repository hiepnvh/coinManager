package com.xxx.coinman.repository;

import com.xxx.coinman.model.CoinBot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "coinbots", path = "coinbot")
public interface CoinBotRepository extends JpaRepository<CoinBot, Long> {
//    User findByUsername(String username);
}
