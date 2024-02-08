import com.github.tototoshi.csv.*
import org.nspl.awtrenderer.*
import org.nspl.{data, *}
import org.saddle.{Index, Series, Vec}

import java.io.File

object graficas {
@main
  def generarG()={
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

    jugadoresGolesGrafica(contentFile2)
    posicionxTorneo(contentFile)
    estadios(contentFile2)

    golesxJugador()
    estadioxnombre()
    numeroEstadio()
  }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

//GRAFICAS POR CSV

  def jugadoresGolesGrafica(data: List[Map[String,String]]) = {
    val dataForChar = data.map(x => (x("matches_tournament_id"),x("matches_match_id"))).distinct.groupBy(_._1)
      .map(x => x._1 -> x._2.length.toDouble)

    val indices = Index(dataForChar.map(_._1.substring(3,7)).toArray)
    val values = Vec(dataForChar.map(_._2).toArray)
    val series = Series(indices,values)
    val bar = saddle.barplotHorizontal(series,xLabFontSize = Option(RelFontSize(1)))(par
      .xLabelRotation(-77)
    .xlab("Torneos")
    .ylab("Cantida_Partidos")
    .main("JuegosXTorneo"))
    pngToFile(new File("C:\\Users\\chamb\\OneDrive\\Escritorio\\PRACTICUM\\PROYECTO_INTEGRADOR\\GRAFICAS\\1jugadoresGolesGrafica.png"),bar.build,500)

  }


  //Generar Imagen Posicion en todos los torneos
  def posicionxTorneo(data: List[Map[String, String]]) = {
    val data4Chart: Map[String, Double] = data
      .map(row => (row("squads_tournament_id"), row("squads_team_id"), row("squads_player_id"), row("squads_position_name"))) // Mapea los datos para obtener una tupla con información relevante de cada jugador.
      .distinct // Eliminar duplicados
      .map(row => row._4 -> (row._1, row._2)) // Mapea los datos para obtener una tupla con la posición del jugador y el identificador del equipo y del torneo.
      .groupBy(_._1) // Agrupa los datos por la posición del jugador.
      .map(row => (row._1, row._2.length.toDouble)) // Mapea los datos para calcular la cantidad de jugadores por posición.

    val indices = Index(data4Chart.map(value => value._1).toArray) // Representan las posiciones de los jugadores.
    val values = Vec(data4Chart.map(value => value._2).toArray) // Representan la cantidad de jugadores por posición.

    val series = Series(indices, values)
    val minValue = data4Chart.values.min // Calcula el valor mínimo de la cantidad de jugadores por posición.
    val maxValue = data4Chart.values.max // Calcula el valor máximo

    val color = RedBlue(minValue, maxValue) // Define el esquema de color para el gráfico, basado en el valor mínimo y máximo de la cantidad de jugadores.
    val bar1 = saddle.barplotHorizontal(series,
      xLabFontSize = Option(RelFontSize(1)),
      color = color
    )(par
      .xlab("Posicion")
      .ylab("freq.")
      .xLabelRotation(-77)
      .xNumTicks(0)
      .main("Posiciones en Torneos"))
    pngToFile(new File("C:\\Users\\chamb\\OneDrive\\Escritorio\\PRACTICUM\\PROYECTO_INTEGRADOR\\GRAFICAS\\1posicionTorneo.png"), bar1.build, 400)
  }

  def estadios(data: List[Map[String, String]]) = {
    val dataForChar = data.map(x => (x("stadiums_stadium_name"), x("stadiums_stadium_capacity"))).distinct.groupBy(_._1)
      .map(x => x._1 -> x._2.length.toDouble)

    val indices = Index(dataForChar.map(_._1.substring(0,5)).toArray)
    val values = Vec(dataForChar.map(_._2).toArray)
    val series = Series(indices, values)
    val bar = saddle.barplotHorizontal(series, xLabFontSize = Option(RelFontSize(0.1)))(par
      .xLabelRotation(-77)
      .xlab("Nombre")
      .ylab("Ciudad")
      .main("Estadios"))
    pngToFile(new File("C:\\Users\\chamb\\OneDrive\\Escritorio\\PRACTICUM\\PROYECTO_INTEGRADOR\\GRAFICAS\\1estadios.png"), bar.build, 500)

  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  //GRAFICAS POR DATABASE

  def golesxJugador() = {
    val dataForChar = consultas.golesXjugador()

    val indices = Index(dataForChar.map(_._1.substring(3, 7)).toArray)
    val values = Vec(dataForChar.map(_._2).toArray)
    val series = Series(indices, values)
    val bar = saddle.barplotHorizontal(series, xLabFontSize = Option(RelFontSize(1)))(par
      .xLabelRotation(-77)
      .xlab("Jugador")
      .ylab("Goles")
      .main("GolesXJugador"))
    pngToFile(new File("C:\\Users\\chamb\\OneDrive\\Escritorio\\PRACTICUM\\PROYECTO_INTEGRADOR\\GRAFICAS\\2golesxJugador.png"), bar.build, 500)

  }

  def estadioxnombre() = {
    val dataForChar = consultas.promedioCapacidadEstadios()

    val indices = Index(dataForChar.map(_._1.substring(0, 5)).toArray)
    val values = Vec(dataForChar.map(_._2).toArray)
    val series = Series(indices, values)
    val bar = saddle.barplotHorizontal(series, xLabFontSize = Option(RelFontSize(1)))(par
      .xLabelRotation(-77)
      .xlab("Jugador")
      .ylab("Goles")
      .main("GolesXJugador"))
    pngToFile(new File("C:\\Users\\chamb\\OneDrive\\Escritorio\\PRACTICUM\\PROYECTO_INTEGRADOR\\GRAFICAS\\2estadioXnombre.png"), bar.build, 500)

  }

  def numeroEstadio() = {
    val dataForChar = consultas.numEstadioPais()

    val indices = Index(dataForChar.map(_._1.substring(0, 4)).toArray)
    val values = Vec(dataForChar.map(_._2).toArray)
    val series = Series(indices, values)
    val bar = saddle.barplotHorizontal(series, xLabFontSize = Option(RelFontSize(1)))(par
      .xLabelRotation(-77)
      .xlab("Jugador")
      .ylab("Goles")
      .main("GolesXJugador"))
    pngToFile(new File("C:\\Users\\chamb\\OneDrive\\Escritorio\\PRACTICUM\\PROYECTO_INTEGRADOR\\GRAFICAS\\2numEstadio.png"), bar.build, 500)

  }

}
