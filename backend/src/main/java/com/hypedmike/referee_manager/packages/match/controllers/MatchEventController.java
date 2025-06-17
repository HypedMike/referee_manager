package com.hypedmike.referee_manager.packages.match.controllers;

import com.hypedmike.referee_manager.packages.match.models.Match;
import com.hypedmike.referee_manager.packages.match.models.MatchEvent;
import com.hypedmike.referee_manager.packages.match.models.MatchEventType;
import com.hypedmike.referee_manager.packages.match.repositories.MatchEventRepository;
import com.hypedmike.referee_manager.packages.match.repositories.MatchRepository;
import com.hypedmike.referee_manager.packages.player.models.Player;
import com.hypedmike.referee_manager.packages.player.repositories.PlayerRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/matches/{id}/events")
public class MatchEventController {
    final private MatchEventRepository matchEventRepository;
    final private MatchRepository matchRepository;
    final private PlayerRepository playerRepository;

    public MatchEventController(MatchEventRepository matchEventRepository, MatchRepository matchRepository, PlayerRepository playerRepository) {
        this.matchEventRepository = matchEventRepository;
        this.matchRepository = matchRepository;
        this.playerRepository = playerRepository;
    }

    @GetMapping
    public ResponseEntity<?> getMatchEventsByMatchId(@PathVariable Long id) {
        // check if match exists
        if (!matchRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        // fetch events for the match
        Optional<List<MatchEvent>> events = matchEventRepository.findByMatchId(id);

        if (events.isEmpty() || events.get().isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(events);
    }

    @PostMapping
    public ResponseEntity<?> createMatchEvent(@PathVariable Long id, @RequestBody MatchEvent matchEvent) {
        // check if match exists
        Optional<Match> match = matchRepository.findById(id);

        if (match.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // set match for the event
        matchEvent.setMatch(match.get());

        // validate player
        if (matchEvent.getPlayer().getId() != null) {
            Optional<Player> player = this.playerRepository.findById(matchEvent.getPlayer().getId());
            if (player.isEmpty()) {
                return ResponseEntity.badRequest().body("Player not found");
            }
        }

        // validate event type
        if (matchEvent.getType() == null || !MatchEventType.isValid(matchEvent.getType())) {
            return ResponseEntity.badRequest().body(null);
        }

        MatchEvent savedEvent = matchEventRepository.save(matchEvent);
        return ResponseEntity.ok(savedEvent);
    }
}
