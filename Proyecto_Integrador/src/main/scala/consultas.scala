import cats.*
import cats.effect.*
import cats.effect.unsafe.implicits.global
import doobie.*
import doobie.implicits.*

object consultas {
  val xa = Transactor.fromDriverManager[IO](
    driver = "com.mysql.cj.jdbc.Driver",
    url = "jdbc:mysql://localhost:3306/Proyecto_int",
    user = "root",
    password = "12345",
    logHandler = None
  )

  def golesXjugador()={
    sql"SELECT goals_player_id, count(goals_goal_id) from goals group by 1"
      .query[(String,Double)]
      .to[List]
      .transact(xa)
      .unsafeRunSync()
  }

  //Consulta para obtener el promedio de la capacidad de los estadios
  def promedioCapacidadEstadios()={
    sql"SELECT stadiums_stadium_name, AVG(stadiums_stadium_capacity) FROM stadiums group by 1"
      .query[(String,Double)]
      .to[List]
      .transact(xa)
      .unsafeRunSync()
  }

  //Consulta de numero de estadios que tiene un pais

  def numEstadioPais()={
    sql"""select distinct stadiums_city_name, count(stadiums_stadium_name) from stadiums group by 1"""
      .query[(String,Double)]
      .to[List]
      .transact(xa)
      .unsafeRunSync()
  }

  /*def estadiosNombres() = {
    sql"SELECT stadiums_stadium_name, stadiums_stadium_capacity FROM stadiums group by 1"
      .query[(String,Double)]
      .unique
      .transact(xa)
      .unsafeRunSync()
  }*/

  // Consulta para obtener el número promedio de equipos por torneo
  def promedioEquiposPorTorneo(): IO[Double] = {
    sql"SELECT AVG(tournaments_count_teams) FROM tournaments"
      .query[Double]
      .unique
      .transact(xa)
  }

  // Consulta para contar el número total de jugadores
  def contarTotalJugadores(): IO[Int] = {
    sql"SELECT COUNT(*) FROM players"
      .query[Int]
      .unique
      .transact(xa)
  }

  // Consulta para contar el número total de partidos
  def contarTotalPartidos(): IO[Int] = {
    sql"SELECT COUNT(*) FROM matches"
      .query[Int]
      .unique
      .transact(xa)
  }

  // Consulta para obtener el promedio de goles marcados por partido
  def promedioGolesPorPartido(): IO[Double] = {
    sql"SELECT AVG(matches_home_team_score + matches_away_team_score) FROM matches"
      .query[Double]
      .unique
      .transact(xa)
  }

  // Consulta para obtener el promedio de goles marcados por jugador
  def promedioGolesPorJugador(): IO[Double] = {
    sql"SELECT AVG(goals_player_id) FROM goals"
      .query[Double]
      .unique
      .transact(xa)
  }

  // Consulta para contar el número total de goles marcados en un torneo específico
  def contarGolesPorTorneo(tournamentId: String): IO[Int] = {
    sql"SELECT COUNT(*) FROM goals INNER JOIN matches ON goals.goals_match_id = matches.matches_match_id WHERE matches.matches_tournament_id = $tournamentId"
      .query[Int]
      .unique
      .transact(xa)
  }


}
