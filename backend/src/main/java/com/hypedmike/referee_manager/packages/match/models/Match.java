package com.hypedmike.referee_manager.packages.match.models;

import com.hypedmike.referee_manager.packages.team.models.Team;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private LocalDateTime date;

    private Long referee_id;

    @ManyToOne
    @JoinColumn(name = "team1_id", nullable = false)
    private Team team1;
    @ManyToOne
    @JoinColumn(name = "team2_id", nullable = false)
    private Team team2;

    private LocalDateTime at;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Long getReferee_id() {
        return referee_id;
    }

    public void setReferee_id(Long referee_id) {
        this.referee_id = referee_id;
    }

    public Team getTeam1() {
        return team1;
    }
    public void setTeam1(Team team1) {
        this.team1 = team1;
    }
    public Team getTeam2() {
        return team2;
    }
    public void setTeam2(Team team2) {
        this.team2 = team2;
    }

    public LocalDateTime getAt() {
        return at;
    }

    public void setAt(LocalDateTime at) {
        this.at = at;
    }
}