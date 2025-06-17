package com.hypedmike.referee_manager.packages.player.repositories;

import com.hypedmike.referee_manager.packages.player.models.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PlayerRepository extends JpaRepository<Player, Long> {
    Optional<List<Player>> findByTeamId(Long teamId);
}
