package com.hypedmike.referee_manager.packages.match.controllers;

import com.hypedmike.referee_manager.packages.match.models.Match;
import com.hypedmike.referee_manager.packages.match.repositories.MatchRepository;
import com.hypedmike.referee_manager.packages.team.models.Team;
import com.hypedmike.referee_manager.packages.team.repositories.TeamRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/matches")
public class MatchController {
    private final MatchRepository matchRepository;
    private final TeamRepository teamRepository;

    public static class CreateMatchRequest {
        private Long homeTeam;
        private Long awayTeam;

        public Long getHomeTeam() {
            return homeTeam;
        }
        public void setHomeTeam(Long homeTeam) {
            this.homeTeam = homeTeam;
        }
        public Long getAwayTeam() {
            return awayTeam;
        }
        public void setAwayTeam(Long awayTeam) {
            this.awayTeam = awayTeam;
        }
    }

    public MatchController(MatchRepository matchRepository, TeamRepository teamRepository) {
        this.matchRepository = matchRepository;
        this.teamRepository = teamRepository;
    }

    @GetMapping
    public ResponseEntity<List<Match>> getMatches() {
        List<Match> matches = this.matchRepository.findAll();
        if (matches.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(matches);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Match> getMatchById(@PathVariable Long id) {
        Optional<Match> match = this.matchRepository.findById(id);
        if (match.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(match.get());
    }

    // Create a new match
    @PostMapping
    public ResponseEntity<?> createMatch(@RequestBody CreateMatchRequest request) {
        final Optional<Team> team1 = this.teamRepository.findById(request.awayTeam);
        if (team1.isEmpty()) {
            return ResponseEntity.status(404).body("Team with id " + request.awayTeam + " not found");
        }

        final Optional<Team> team2 = this.teamRepository.findById(request.homeTeam);
        if (team2.isEmpty()) {
            return ResponseEntity.status(404).body("Team with id " + request.homeTeam + " not found");
        }

        // create a new match
        Match match = new Match();
        match.setTeam1(team1.get());
        match.setTeam2(team2.get());
        match = this.matchRepository.save(match);
        return ResponseEntity.ok(match);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMatch(@PathVariable Long id) {
        if (!this.matchRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        this.matchRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
