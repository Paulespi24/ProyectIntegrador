
import org.nspl.*
import org.nspl.awtrenderer.*
import org.nspl.data.HistogramData
import org.saddle.Index
import org.saddle.Series
import org.saddle.Vec
import java.io.File
import com.github.tototoshi.csv._


@main
def main(): Unit = {


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
  val csv1 = leerCsv("C:\\Users\\chamb\\OneDrive\\Escritorio\\PROYECTO_INTEGRADOR\\dsAlineacionesXTorneo-2.csv")
  val csv2 = leerCsv("C:\\Users\\chamb\\OneDrive\\Escritorio\\PROYECTO_INTEGRADOR\\dsPartidosYGoles.csv")

  // Unir los dos archivos csv por el campo team_id
  val csvUnido = unirCsv(csv1, csv2)

  try {
    escribirCsv(csvUnido, "C:\\Users\\chamb\\OneDrive\\Escritorio\\PROYECTO_INTEGRADOR.csv")
  } catch {
    case e: Exception => println(s"Error al escribir el archivo: $e")
  }

}