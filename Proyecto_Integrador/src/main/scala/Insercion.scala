import com.github.tototoshi.csv.*
import java.io.File

import doobie._
import doobie.implicits._

import cats._
import cats.effect._
import cats.implicits._

import cats.effect.unsafe.implicits.global

implicit object CustomFormat extends DefaultCSVFormat {
  override val delimiter: Char = ';'
}

object Insercion {
  @main
  //LECTURA ALINEACIONESXTORNEO
  def exportFunc() =
    val path2DataFile = "C:\\Users\\chamb\\OneDrive\\Escritorio\\PRACTICUM\\PROYECTO_INTEGRADOR\\dsAlineacionesXTorneo-2.csv"
    val reader = CSVReader.open(new File(path2DataFile))
    val contentFile: List[Map[String, String]] =
      reader.allWithHeaders()

    reader.close()

  ////////////////////////////////////////////////////////////////////


  //LECTURA PARTIDOSYGOLES

    val path2DataFile2 = "C:\\Users\\chamb\\OneDrive\\Escritorio\\PRACTICUM\\PROYECTO_INTEGRADOR\\dsPartidosYGoles.csv"
    val reader2 = CSVReader.open(new File(path2DataFile2))
    val contentFile2: List[Map[String, String]] =
      reader2.allWithHeaders()

    reader2.close()


  /////////////////////////////////////////////////////////////////////

    val path2DataFilep = "C:\\Users\\chamb\\OneDrive\\Escritorio\\PRACTICUM\\PROYECTO_INTEGRADOR\\NORMALIZADO\\Players.csv"
    val readerp = CSVReader.open(new File(path2DataFile2))
    val contentFilep: List[Map[String, String]] =
      readerp.allWithHeaders()

    readerp.close()


  /////////////////////////////////////////////////////////////////////

    val path2DataFiles = "C:\\Users\\chamb\\OneDrive\\Escritorio\\PRACTICUM\\PROYECTO_INTEGRADOR\\NORMALIZADO\\Stadiums.csv"
    val readers = CSVReader.open(new File(path2DataFile2))
    val contentFiles: List[Map[String, String]] =
      readers.allWithHeaders()

    readers.close()


  /////////////////////////////////////////////////////////////////////

    val path2DataFileh = "C:\\Users\\chamb\\OneDrive\\Escritorio\\PRACTICUM\\PROYECTO_INTEGRADOR\\NORMALIZADO\\Home_Teams.csv"
    val readerh = CSVReader.open(new File(path2DataFile2))
    val contentFileh: List[Map[String, String]] =
      readerh.allWithHeaders()

    readerh.close()


  /////////////////////////////////////////////////////////////////////

    val path2DataFileaw = "C:\\Users\\chamb\\OneDrive\\Escritorio\\PRACTICUM\\PROYECTO_INTEGRADOR\\NORMALIZADO\\Away_Teams.csv"
    val readeraw = CSVReader.open(new File(path2DataFile2))
    val contentFileaw: List[Map[String, String]] =
      readeraw.allWithHeaders()

    readeraw.close()


  /////////////////////////////////////////////////////////////////////

    val path2DataFilet = "C:\\Users\\chamb\\OneDrive\\Escritorio\\PRACTICUM\\PROYECTO_INTEGRADOR\\NORMALIZADO\\Tournaments.csv"
    val readert = CSVReader.open(new File(path2DataFile2))
    val contentFilet: List[Map[String, String]] =
      readert.allWithHeaders()

    readert.close()


  /////////////////////////////////////////////////////////////////////

    val path2DataFilesq = "C:\\Users\\chamb\\OneDrive\\Escritorio\\PRACTICUM\\PROYECTO_INTEGRADOR\\NORMALIZADO\\Squads.csv"
    val readersq = CSVReader.open(new File(path2DataFile2))
    val contentFilesq: List[Map[String, String]] =
      readersq.allWithHeaders()

    readersq.close()


  /////////////////////////////////////////////////////////////////////

    val path2DataFilem = "C:\\Users\\chamb\\OneDrive\\Escritorio\\PRACTICUM\\PROYECTO_INTEGRADOR\\NORMALIZADO\\Matches.csv"
    val readerm = CSVReader.open(new File(path2DataFile2))
    val contentFilem: List[Map[String, String]] =
      readerm.allWithHeaders()

    readerm.close()


  /////////////////////////////////////////////////////////////////////


    val path2DataFileg = "C:\\Users\\chamb\\OneDrive\\Escritorio\\PRACTICUM\\PROYECTO_INTEGRADOR\\NORMALIZADO\\Goals.csv"
    val readerg = CSVReader.open(new File(path2DataFile2))
    val contentFileg: List[Map[String, String]] =
      readerg.allWithHeaders()

    readerg.close()


  /////////////////////////////////////////////////////////////////////

    //println(contentFile)
    //genData2GenreTable(contentFile)
    //genData2ActorTable(contentFile)

  /////////////////////////////////////////////////////////////////

    //CONEXION A LA BASE DE DATOS

    val xa = Transactor.fromDriverManager[IO](
      driver = "com.mysql.cj.jdbc.Driver",
      url = "jdbc:mysql://localhost:3306/Proyecto_int",
      user = "root",
      password = "12345",
      logHandler = None
    )

    ///////////////////////////////////////////////////////////////

    //INVOCACION DE LAS FUNCIONES//

    /*generateAlineaciones(contentFile)

    generateGoles(contentFile2)

    generateGoles(contentFile2)
      .foreach(insert => insert.run.transact(xa).unsafeRunSync())

    generateGoles2(contentFile2)
      .foreach(insert => insert.run.transact(xa).unsafeRunSync())*/


    players(contentFile)
      .foreach(insert => insert.run.transact(xa).unsafeRunSync())

    stadiums(contentFiles)
      .foreach(insert => insert.run.transact(xa).unsafeRunSync())

    hometeams(contentFileh)
      .foreach(insert => insert.run.transact(xa).unsafeRunSync())

    awayteams(contentFileaw)
      .foreach(insert => insert.run.transact(xa).unsafeRunSync())

    tournaments(contentFilet)
      .foreach(insert => insert.run.transact(xa).unsafeRunSync())

    squads(contentFile)
      .foreach(insert => insert.run.transact(xa).unsafeRunSync())

    matches(contentFilem)
      .foreach(insert => insert.run.transact(xa).unsafeRunSync())

    goals(contentFileg)
      .foreach(insert => insert.run.transact(xa).unsafeRunSync())



/////////////////////////////////////////////////////////////////////////



  def defaultValue(text: String): Double =
    if !text.equals("NA") then {
      0
    } else
      text.toDouble


  ////////////////////////////////////////////////////////////////////

  

   ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

   //INSERCION A LAS TABLAS NORMALIZADAS

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////

  def players(data: List[Map[String, String]]) =
    val player = data
      .map(
          row =>(row("squads_player_id"),
          row("players_family_name"),
          row("players_given_name"),
          row("players_birth_date")  ,
          row("players_female"),
          row("players_goal_keeper"),
          row("players_defender"),
          row("players_midfielder"),
          row("players_forward")
          )
      )
      .map(t9 => sql"INSERT INTO players(squads_player_id,players_family_name, players_given_name, players_birth_date, players_female, players_goal_keeper, players_defender, players_midfielder, players_forward)VALUES (${t9._1},${t9._2},${t9._3},${t9._4},${t9._5},${t9._6},${t9._7},${t9._8},${t9._9})".update)

    player

    //////////////////////////////////////////////////////

  def stadiums(data: List[Map[String, String]]) =
    val stadi = data
      .map(
        row => (row("matches_stadium_id"),
          row("stadiums_stadium_name"),
          row("stadiums_city_name"),
          row("stadiums_country_name"),
          row("stadiums_stadium_capacity"),
        )
      )
      .map(t5 => sql"INSERT INTO stadiums(matches_stadium_id, stadiums_stadium_name, stadiums_city_name, stadiums_country_name, stadiums_stadium_capacity)VALUES(${t5._1},${t5._2},${t5._3},${t5._4},${t5._5})".update)

    stadi


    /////////////////////////////////////////////////////////////



  def hometeams(data: List[Map[String, String]]) =
    val home = data
      .map(
        row => (row("matches_home_team_id"),
          row("home_team_name"),
        )
      )
      .map(t2 => sql"INSERT INTO home_teams(matches_home_team_id, home_team_name)VALUES(${t2._1},${t2._2})".update)

    home
  /////////////////////////////////////////////////////////////


  def awayteams(data: List[Map[String, String]]) =
    val away = data
      .map(
        row => (row("matches_away_team_id"),
          row("away_team_name"),
        )
      )
      .map(t2 => sql"INSERT INTO away_teams(matches_away_team_id, away_team_name)VALUES (${t2._1},${t2._2})".update)

    away


  /////////////////////////////////////////////////////////////

  def tournaments(data: List[Map[String, String]]) =
    val tour = data
      .map(
        row => (row("matches_tournament_id"),
          row("matches_tournament_id"),
          row("tournaments_year"),
          row("tournaments_host_country"),
          row("tournaments_winner"),
          row("tournaments_count_teams")
        )
      )
      .map(t6 => sql"INSERT INTO tournaments(matches_tournament_id, tournaments_tournament_name, tournaments_year, tournaments_host_country, tournaments_winner, tournaments_count_teams)VALUES(${t6._1},${t6._2},${t6._3},${t6._4},${t6._5},${t6._6})".update)

    tour

  /////////////////////////////////////////////////////////////

  def squads(data: List[Map[String, String]]) =
    val squad = data
      .map(
        row => (row("squads_team_id"),
          row("squads_player_id"),
          row("squads_shirt_number"),
          row("squads_position_name"),
        )
      )
      .map(t4 => sql"INSERT INTO squads(squads_team_id, squads_player_id, squads_shirt_number, squads_position_name)VALUES (${t4._1},${t4._2},${t4._3},${t4._4})".update)

    squad

  /////////////////////////////////////////////////////////////

  def matches(data: List[Map[String, String]]) =
    val matc = data
    .map(
      row => (
        row("matches_match_id"),
        row("matches_tournament_id"),
        row("matches_away_team_id"),
        row("matches_home_team_id"),
        row("matches_stadium_id"),
        row("matches_match_date"),
        row("matches_match_time"),
        row("matches_stage_name"),
        row("matches_home_team_score"),
        row("matches_away_team_score"),
        row("matches_extra_time"),
        row("matches_penalty_shootout"),
        row("matches_home_team_score_penalties"),
        row("matches_away_team_score_penalties"),
        row("matches_result")
      )
    ).map (t15 => sql"INSERT INTO matches(matches_match_id, matches_tournament_id, matches_away_team_id, matches_home_team_id, matches_stadium_id, matches_match_date, matches_match_time,matches_stage_name,matches_home_team_score,matches_away_team_score,matches_extra_time,matches_penalty_shootout,matches_home_team_score_penalties,matches_away_team_score_penalties ,matches_result) VALUES (${t15._1},${t15._2},${t15._3},${t15._4},${t15._5},${t15._6},${t15._7},${t15._8},${t15._9},${t15._10},${t15._11},${t15._12},${t15._13},${t15._14},${t15._15})".update)
    matc



  /////////////////////////////////////////////////////////////

  def goals(data: List[Map[String, String]]) =
    val goal = data
      .map(
        row => (row("goals_goal_id"),
          row("goals_team_id"),
          row("goals_player_id"),
          row("goals_player_team_id"),
          row("goals_minute_label"),
          row("goals_minute_regulation"),
          row("goals_minute_stoppage"),
          row("goals_match_period"),
          row("goals_own_goal"),
          row("goals_penalty"),
        )
      )
      .filterNot(_._6.equals("NA"))
      .map(t10 => sql"INSERT INTO goals(goals_goal_id, goals_team_id, goals_player_id, goals_player_team_id, goals_minute_label, goals_minute_regulation,goals_minute_stoppage,goals_match_period,goals_own_goal, goals_penalty) VALUES(${t10._1},${t10._2},${t10._3},${t10._4},${t10._5},${t10._6},${t10._7},${t10._8},${t10._9},${t10._10})".update)
    goal

  /////////////////////////////////////////////////////////////

  /*def generateGoles(data: List[Map[String, String]]) =
    val gol = data
      .map ( row =>
          (
            row("ID").toInt,
            row("matches_tournament_id"),
            row("matches_match_id"),
            row("matches_away_team_id"),
            row("matches_home_team_id"),
            row("matches_stadium_id"),
            row("matches_match_date"),
            row("matches_match_time"),
            row("matches_stage_name"),
            row("matches_home_team_score").toInt,
            row("matches_away_team_score").toInt,
            row("matches_extra_time").toInt,
            row("matches_penalty_shootout").toInt,
            row("matches_home_team_score_penalties").toInt,
            row("matches_away_team_score_penalties").toInt,
            row("matches_result"),
            row("tournaments_tournament_name"),
            row("tournaments_year").toInt,
            row("tournaments_host_country"),
            row("tournaments_winner"),
            row("tournaments_count_teams").toInt,

          )
        ).map(tuple21 => sql"INSERT INTO partidosygoles (ID, matches_tournament_id, matches_match_id, matches_away_team_id, matches_home_team_id, matches_stadium_id, matches_match_date, matches_match_time, matches_stage_name, matches_home_team_score, matches_away_team_score, matches_extra_time, matches_penalty_shootout, matches_home_team_score_penalties, matches_away_team_score_penalties, matches_result, tournaments_tournament_name, tournaments_year, tournaments_host_country, tournaments_winner, tournaments_count_teams) VALUES (${tuple21._1}, ${tuple21._2}, ${tuple21._3}, ${tuple21._4}, ${tuple21._5}, ${tuple21._6}, ${tuple21._7}, ${tuple21._8}, ${tuple21._9}, ${tuple21._10}, ${tuple21._11}, ${tuple21._12}, ${tuple21._13}, ${tuple21._14}, ${tuple21._15}, ${tuple21._16}, ${tuple21._17}, ${tuple21._18}, ${tuple21._19}, ${tuple21._20}, ${tuple21._21})".update)
      gol*/

  //.map(t7 => sql"INSERT INTO MOVIE(NAME, RATING, VOTES, META_SCORE, PG_RATING, YEAR, DURATION) VALUES(${t7._1}, ${t7._2}, ${t7._3}, ${t7._4}, ${t7._5}, ${t7._6} ,${t7._7})".update)

////////////////////////////////////////////////////////////////////////////////////////////////////


  /*def generateGoles2(data: List[Map[String, String]]) =
      val gol2 = data
      .map ( row =>
        (
          row("stadiums_stadium_name"),
          row("stadiums_country_name"),
          row("stadiums_city_name"),
          row("stadiums_stadium_capacity").toInt,
          row("home_team_name"),
          row("home_mens_team").toInt,
          row("home_womens_team"),
          row("home_region_name"),
          row("away_team_name"),
          row("away_mens_team").toInt,
          row("away_womens_team").toInt,
          row("away_region_name"),
          row("goals_goal_id"),
          row("goals_team_id"),
          row("goals_player_id"),
          row("goals_player_team_id"),
          row("goals_minute_label"),
          defaultValue(row("goals_minute_regulation")).toInt,
          defaultValue(row("goals_minute_stoppage")).toInt,
          row("goals_match_period"),
          defaultValue(row("goals_own_goal")).toInt,
          defaultValue(row("goals_penalty")).toInt,

        )

    ).map(tuple22 => sql"INSERT INTO partidosygoles(stadiums_stadium_name, stadiums_country_name, stadiums_city_name, stadiums_stadium_capacity, home_team_name, home_mens_team, home_womens_team, home_region_name, away_team_name, away_mens_team, away_womens_team, away_region_name, goals_goal_id, goals_team_id, goals_player_id, goals_player_team_id, goals_minute_label, goals_minute_regulation, goals_minute_stoppage, goals_match_period, goals_own_goal, goals_penalty) VALUES (${tuple22._1}, ${tuple22._2}, ${tuple22._3}, ${tuple22._4}, ${tuple22._5}, ${tuple22._6}, ${tuple22._7}, ${tuple22._8}, ${tuple22._9}, ${tuple22._10}, ${tuple22._11}, ${tuple22._12}, ${tuple22._13}, ${tuple22._14}, ${tuple22._15}, ${tuple22._16}, ${tuple22._17}, ${tuple22._18}, ${tuple22._19}, ${tuple22._20}, ${tuple22._21}, ${tuple22._22})".update)
      gol2*/


      //gol2.foreach(println)

  //TABLA GENERAL//

  /*def generateAlineaciones(data: List[Map[String, String]]) =
    val sqlInsert = s"INSERT INTO AlineacionesXTorneo(id, squads_player_id, squads_tournament_id, squads_team_id, squads_shirt_number, squads_position_name, players_family_name, players_given_name, players_birth_date, players_female, players_goal_keeper, players_defender, players_midfielder, players_forward) VALUES(%d ,'%s','%s','%s', %d, '%s','%s','%s','%s',%d ,%d ,%d ,%d ,%d);"
    val alin = data
      .map(
        row => (row("id").toInt,
          defaultValue(row("squads_player_id")),
          defaultValue(row("squads_tournament_id")),
          defaultValue(row("squads_team_id")),
          defaultValue(row("squads_shirt_number")).toInt,
          defaultValue(row("squads_position_name")),
          defaultValue(row("players_family_name")),
          defaultValue(row("players_given_name")),
          defaultValue(row("players_birth_date")),
          defaultValue(row("players_female")).toInt,
          defaultValue(row("players_goal_keeper")).toInt,
          defaultValue(row("players_defender")).toInt,
          defaultValue(row("players_midfielder")).toInt,
          defaultValue(row("players_forward")).toInt,
          )
      )
      .map(t14 => sqlInsert.formatLocal(java.util.Locale.US, t14._1,t14._2,t14._3,t14._4,t14._5,t14._6,t14._7,t14._8,t14._9,t14._10,t14._11,t14._12,t14._13,t14._14))

    alin.foreach(println)*/


  /*def convertDuration2Int(txtDuration: String): Int =
    if (txtDuration.contains("h") && txtDuration.contains("m")) {
      txtDuration
        .trim
        .split("\\s")(0)
        .replaceAll("h", "")
        .toInt * 60
        +
        txtDuration
          .trim
          .split("\\s")(1)
          .replaceAll("m", "")
          .toInt
    } else {
      0
    }*/


  //////////////////////////////////////////////////////////////////////

































  /*def genData2GenreTable(data: List[Map[String, String]]) =
    val insertFormat = s"INSERT INTO GENRE(NAME) VALUES('%s');"//%s = String
    val genre = data
      .map(_("Genre"))//List("Drama,Thriller,Mistery")
      .flatMap(_.split(","))//Proporciona un arreglo de los elementos separados por comas
      .filterNot(_ == "NA")
      .map(_.trim)//trim = borra espacios en blanco ubicados antes y despues del texto
      .distinct
      .sorted
      .map(name => insertFormat.format(name))


    //println(genre)
    genre.foreach(println)*/

    /*
    def genData2ActorTable(data: List[Map[String, String]]) =
        val insertFormat = s"INSERT INTO ACTOR(NAME) VALUES('%s');"
      val genre = data
        .map(_("Cast"))
        .flatMap(_.split(","))
        .filterNot(_ == "NA")
        .map(_.trim)
        .map(_.replaceAll("'", "\\\\'"))
        .distinct
        .sorted
        .map(name => insertFormat.format(name))
   */

    //println(genre)
    //genre.foreach(println)


}