package com.xxx.coinman.repository;

import com.xxx.coinman.model.CoinBot;
import com.xxx.coinman.model.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "coinbots", path = "coinbots")
public interface CoinBotRepository extends JpaRepository<CoinBot, Long> {
    List<CoinBot> findByUser(User user);
}
