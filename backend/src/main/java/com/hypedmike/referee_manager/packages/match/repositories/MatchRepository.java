package com.hypedmike.referee_manager.packages.match.repositories;

import com.hypedmike.referee_manager.packages.match.models.Match;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchRepository extends JpaRepository<Match, Long> {

}
