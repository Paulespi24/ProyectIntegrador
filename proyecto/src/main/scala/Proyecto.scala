package ec.edu.utpl.presencial.computacion.pfr.pintegra
import com.github.tototoshi.csv.*
import org.nspl.*
import org.nspl.awtrenderer.*
import org.nspl.awtrenderer.pngToFile
import org.nspl.data.HistogramData
import org.saddle.{Index, Series, Vec}

import java.io.File

implicit object CustomFormat extends DefaultCSVFormat {
  override val delimiter: Char = ';'
}

object Proyecto {
  //var contentFile: List[Map[String, String]] = List.empty

  @main
  def work2(): Unit = {
    val pathDataFile2: String = "C:\\Users\\chamb\\OneDrive\\Escritorio\\PROGRMACION\\dsPartidosYGoles.csv"
    val reader2 = CSVReader.open(new File(pathDataFile2))
    val contentFile = reader2.allWithHeaders()
    reader2.close()

    println(s"Filas: ${contentFile.length} y Columnas: ${contentFile(0).keys.size}")

    print(
      datosGrafica(contentFile)
    )
    chartting(contentFile)

    val v1 = datosGrafica(contentFile)

    chartBarPlot(v1)
    charting2(contentFile)
  }

  def chartting(data: List[Map[String, String]]): Unit = {
    val listNroShirt: List[Double] = data
      .filter(row => row("squads_position_name") == "forward" && row("squads_shirt_number") != "0")
      .map(row => row("squads_shirt_number").toDouble)

    val histForwardShirtNumber = xyplot(HistogramData(listNroShirt, 25) -> bar())(
      par
        .xlab("Shirt number")
        .ylab("freq.")
        .main("Forward shirt number")
    )
    pngToFile(new File("C:\\Users\\chamb\\OneDrive\\Escritorio\\P FUNCIONAL REACTIVA\\fhsn.png"), histForwardShirtNumber.build, 1000)
    renderToByteArray(histForwardShirtNumber.build, width = 2000)
  }

  def charting2(data: List[Map[String, String]]): Unit = {
    val listNroShirt: List[Double] = data
      .filter(row => row("goals_minute_regulation") != "NA")
      .map(row => row("goals_minute_regulation").toDouble)

    val histForwardShirtNumber = xyplot(HistogramData(listNroShirt, 20) -> bar())(
      par
        .xlab("Minutos")
        .ylab("frecuencia")
        .main("Goles por minuto")
    )
    pngToFile(new File("C:\\Users\\chamb\\OneDrive\\Escritorio\\P FUNCIONAL REACTIVA\\minutos.png"), histForwardShirtNumber.build, 1000)
    renderToByteArray(histForwardShirtNumber.build, width = 2000)
  }


  def datosGrafica(data: List[Map[String, String]]) = {
    val dataGoles: List[(String, Int)] = data
      .filter(_("tournaments_tournament_name").contains("Women"))
      .map(row => (
        row("tournaments_tournament_name"),
        row("matches_match_id"),
        row("matches_home_team_score"),
        row("matches_away_team_score")
      )) // se obtiene el nombreTorneo, idPartido
      .distinct //saca los repetidos
      .map(t4 => (t4._1, t4._3.toInt + t4._4.toInt)) //suma goles de visitantes y locales
      .groupBy(_._1) //agrupa por nombre del torneo
      .map(t2 => (t2._1, t2._2.map(_._2).sum) )
      .toList
      .sortBy(_._1)
    dataGoles
  }



  def chartBarPlot(data: List[(String, Int)]) : Unit =
    val data4Chart: List[(String, Double)] = data
      .map(t2 => (t2._1, t2._2.toDouble))

    val indices = Index(data4Chart.map(value => value._1.substring(0,4)).toArray)
    val values = Vec(data4Chart.map(value => value._2).toArray)

    val series = Series(indices, values)


    val bar1 = saddle.barplotHorizontal(series,
      xLabFontSize = Option(RelFontSize(0.9)),
      color = RedBlue(86,146))(par.xLabelRotation(-77).xNumTicks(0)
      .xlab("AÑO")
      .ylab("Frecuencia")
      .main("Goles Torneo")
    )

    pngToFile(new File("C:\\Users\\chamb\\OneDrive\\Escritorio\\P FUNCIONAL REACTIVA\\chartBar2.png"),bar1.build,1000)


  // Función para leer un archivo csv y convertirlo en una lista de listas
  def leerCsv(nombre: String): List[List[String]] = {
    // Crear un lector de archivos
    val lector = new java.io.BufferedReader(new java.io.FileReader(nombre))
    // Crear una lista vacía para almacenar las filas
    var filas = List[List[String]]()
    // Leer la primera línea del archivo
    var linea = lector.readLine()
    // Mientras haya líneas por leer
    while (linea != null) {
      // Separar los valores por comas y convertirlos en una lista
      val valores = linea.split(";").toList
      // Añadir la lista a la lista de filas
      filas = filas :+ valores
      // Leer la siguiente línea del archivo
      linea = lector.readLine()
    }
    // Cerrar el lector de archivos
    lector.close()
    // Devolver la lista de filas
    filas
  }

  // Función para unir dos listas de listas por el campo team_id
  def unirCsv(lista1: List[List[String]], lista2: List[List[String]]): List[List[String]] = {
    // Crear un mapa que asocie cada team_id con su fila correspondiente en la lista2
    val mapa = lista2.tail.map(x => (x.head, x.tail)).toMap
    // Crear una lista vacía para almacenar las filas unidas
    var filas = List[List[String]]()
    // Añadir la fila de cabecera, uniendo las cabeceras de las dos listas
    filas = filas :+ (lista1.head ++ lista2.head.tail)
    // Para cada fila de la lista1, excepto la cabecera
    for (fila <- lista1.tail) {
      // Obtener el team_id de la fila
      val team_id = fila.head
      // Si el mapa contiene el team_id
      if (mapa.contains(team_id)) {
        // Obtener la fila correspondiente de la lista2, sin el team_id
        val fila2 = mapa(team_id)
        // Unir las dos filas por el team_id
        val filaUnida = fila ++ fila2
        // Añadir la fila unida a la lista de filas
        filas = filas :+ filaUnida
      }
    }
    // Devolver la lista de filas
    filas
  }

  // Función para escribir una lista de listas en un nuevo archivo csv
  def escribirCsv(lista: List[List[String]], nombre: String): Unit = {
    // Crear un escritor de archivos
    val escritor = new java.io.PrintWriter(new java.io.File(nombre))
    // Para cada sublista de la lista
    for (fila <- lista) {
      // Unir los valores por comas usando mkString
      val linea = fila.mkString(";")
      // Escribir la línea en el archivo
      escritor.println(linea)
    }
    // Cerrar el escritor de archivos
    escritor.close()
  }

  // Leer los dos archivos csv que se quieren unir
  val csv1 = leerCsv("C:\\Users\\chamb\\OneDrive\\Escritorio\\PROYECTO_INTEGRADOR\\dsPartidosYGoles.csv")
  val csv2 = leerCsv("C:\\Users\\chamb\\OneDrive\\Escritorio\\PROYECTO_INTEGRADOR\\dsAlineacionesXTorneo-2.csv")

  // Unir los dos archivos csv por el campo team_id
  val csvUnido = unirCsv(csv1, csv2)

  // Escribir el archivo csv unido en un nuevo archivo
  escribirCsv(csvUnido, "C:\\Users\\chamb\\OneDrive\\Escritorio\\PROYECTO_INTEGRADOR\\archivoUnido.csv")
}


/*@main
 def work() = {
   val pathDataFile: String = "C:\\Users\\chamb\\OneDrive\\Escritorio\\PROGRMACION\\dsPartidosYGoles.csv"
   val reader1 = CSVReader.open(new File(pathDataFile))

   contentFile = reader1.allWithHeaders()

   reader1.close()

   println(s"Filas: ${contentFile.length} y Columnas: ${contentFile(0).keys.size}")
 }*/