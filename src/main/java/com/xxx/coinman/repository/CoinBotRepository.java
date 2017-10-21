package com.xxx.coinman.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.xxx.coinman.model.CoinBot;
import com.xxx.coinman.model.User;

@RepositoryRestResource(collectionResourceRel = "coinbots", path = "coinbots")
public interface CoinBotRepository extends JpaRepository<CoinBot, Long> {
    List<CoinBot> findByUser(User user);
    List<CoinBot> findByActive(Boolean isActive);
}
