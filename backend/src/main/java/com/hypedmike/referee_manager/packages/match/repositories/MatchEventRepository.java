package com.hypedmike.referee_manager.packages.match.repositories;

import com.hypedmike.referee_manager.packages.match.models.MatchEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MatchEventRepository extends JpaRepository<MatchEvent, Long> {
    Optional<List<MatchEvent>> findByMatchId(Long matchId);
}
