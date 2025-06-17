package com.hypedmike.referee_manager.packages.team.repositories;

import com.hypedmike.referee_manager.packages.team.models.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
