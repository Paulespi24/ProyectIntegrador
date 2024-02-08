package ec.edu.utpl.presencial.computacion.pfr.pintegra

import com.github.tototoshi.csv._

implicit object CustomFormat extends DefaultCSVFormat{
  override val delimiter:Char=';'
}
import java.io.File

object funciones {
  @main
  def work() =

    val path2DataFile: String = "C:\\Users\\chamb\\OneDrive\\Escritorio\\P FUNCIONAL REACTIVA\\dsPartidosYGoles.csv"
    val reader = CSVReader.open(new File(path2DataFile))

    val contentFile: List[Map[String, String]] = reader.allWithHeaders()


    reader.close()


    // capacidadXestadio
    val capacidadesEstadios: List[Int] = contentFile.map(row => row("stadiums_stadium_capacity").toInt)

    // capacidad promedio estadios
    val capacidadPromedio: Double = if (capacidadesEstadios.nonEmpty) capacidadesEstadios.sum.toDouble
      / capacidadesEstadios.length else 0.0
    val maxCapacidad: Int = contentFile.map(x => x("stadiums_stadium_capacity").toInt).max
    val minCapacidad: Int = contentFile.map(x => x("stadiums_stadium_capacity").toInt).min
    println(s"Capacidad promedio de personas por estadio: $capacidadPromedio")
    println(s"Capacidad minima de personas por estadio: $minCapacidad")
    println(s"Capacidad maxima de personas por estadio: $maxCapacidad")


    // numero Total Partidos

    val numeroTotalPartidos: Int = contentFile.length
    println(s"Número total de partidos: $numeroTotalPartidos")



}
