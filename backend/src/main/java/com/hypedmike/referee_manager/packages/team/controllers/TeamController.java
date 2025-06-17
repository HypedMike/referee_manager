package com.hypedmike.referee_manager.packages.team.controllers;

import com.hypedmike.referee_manager.packages.player.models.Player;
import com.hypedmike.referee_manager.packages.player.repositories.PlayerRepository;
import com.hypedmike.referee_manager.packages.team.models.Team;
import com.hypedmike.referee_manager.packages.team.repositories.TeamRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teams")
public class TeamController {
    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;

    public TeamController(TeamRepository teamRepository,
                          PlayerRepository playerRepository) {
        this.teamRepository = teamRepository;
        this.playerRepository = playerRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Team> getPlayers(@PathVariable Long id) {
        return teamRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Team>> getTeams() {
        List<Team> teams = teamRepository.findAll();
        if (teams.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(teams);
    }

    @PostMapping
    public ResponseEntity<Team> createTeam(@RequestBody Team team) {
        Team savedTeam = teamRepository.save(team);
        return ResponseEntity.ok(savedTeam);
    }

    @PostMapping("/{id}/player")
    public ResponseEntity<Player> createPlayer(@RequestBody Player player, @PathVariable Long id) {
        return teamRepository.findById(id)
                .map(team -> {
                    player.setTeam(team);
                    Player savedPlayer = playerRepository.save(player);
                    return ResponseEntity.ok(savedPlayer);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTeam(@PathVariable Long id) {
        if (teamRepository.existsById(id)) {
            // check if there are players in the team
            List<Player> players = playerRepository.findByTeamId(id).orElse(List.of());
            if (!players.isEmpty()) {
                return ResponseEntity.status(400).body("Cannot delete team with players assigned to it.");
            }

            teamRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
