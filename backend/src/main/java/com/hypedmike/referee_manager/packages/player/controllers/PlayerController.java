package com.hypedmike.referee_manager.packages.player.controllers;

import com.hypedmike.referee_manager.packages.player.models.Player;
import com.hypedmike.referee_manager.packages.player.repositories.PlayerRepository;
import com.hypedmike.referee_manager.packages.team.repositories.TeamRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/players")
public class PlayerController {
    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;

    public PlayerController(PlayerRepository playerRepository,
                            TeamRepository teamRepository) {
        this.playerRepository = playerRepository;
        this.teamRepository = teamRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Player> getPlayers(@PathVariable Long id) {
        return playerRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Player>> getPlayers(@RequestParam (required = false) String teamId) {
        if (teamId != null) {
            return playerRepository.findByTeamId(Long.parseLong(teamId))
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } else {
            return ResponseEntity.ok(playerRepository.findAll());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlayer(@RequestParam Long id) {
        if (playerRepository.existsById(id)) {
            playerRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
