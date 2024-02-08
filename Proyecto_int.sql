CREATE DATABASE IF NOT EXISTS Proyecto;

USE Proyecto;

CREATE TABLE IF NOT EXISTS AlineacionesXTorneo (
  ID INT AUTO_INCREMENT PRIMARY KEY,
  squads_player_id VARCHAR(255),
  squads_tournament_id VARCHAR(255),
  squads_team_id VARCHAR(255),
  squads_shirt_number INT,
  squads_position_name VARCHAR(255),
  players_family_name VARCHAR(255),
  players_given_name VARCHAR(255),
  players_birth_date VARCHAR(255),
  players_female INT,
  players_goal_keeper INT,
  players_defender INT,
  players_midfielder INT,
  players_forward INT
);

DROP TABLE IF EXISTS PartidosYGoles;

CREATE TABLE PartidosYGoles (
  ID int,
  matches_tournament_id varchar(255),
  matches_match_id varchar(255),
  matches_away_team_id varchar(255),
  matches_home_team_id varchar(255),
  matches_stadium_id varchar(255),
  matches_match_date varchar(255),
  matches_match_time varchar(255),
  matches_stage_name varchar(255),
  matches_home_team_score int,
  matches_away_team_score int,
  matches_extra_time int,
  matches_penalty_shootout int,
  matches_home_team_score_penalties int,
  matches_away_team_score_penalties int,
  matches_result varchar(255),
  tournaments_tournament_name varchar(255),
  tournaments_year int,
  tournaments_host_country varchar(255),
  tournaments_winner varchar(255),
  tournaments_count_teams int,
  stadiums_stadium_name varchar(255),
  stadiums_city_name varchar(255),
  stadiums_country_name varchar(255),
  stadiums_stadium_capacity int,
  home_team_name varchar(255),
  home_mens_team int,
  home_womens_team int,
  home_region_name varchar(255),
  away_team_name varchar(255),
  away_mens_team int,
  away_womens_team int,
  away_region_name varchar(255),
  goals_goal_id varchar(255),
  goals_team_id varchar(255),
  goals_player_id varchar(255),
  goals_player_team_id varchar(255),
  goals_minute_label varchar(255),
  goals_minute_regulation int,
  goals_minute_stoppage int,
  goals_match_period varchar(255),
  goals_own_goal int,
  goals_penalty int
);
 