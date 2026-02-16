package services

import javax.inject._
import play.api.Configuration
import java.io.PrintWriter
import scala.io.Source
import java.io.File

@Singleton
class FileService @Inject()(config: Configuration) {
  val path = config.get[String]("file.storage.path")
  def save(start: Int, end: Int): Boolean = {
    try {
      val writer = new PrintWriter(path)
      var first = true
      for(i <- start to end){
        if(!first) writer.print(",")
        writer.print(i)
        first = false
      }
      writer.close()
      true
    }
    catch {
      case e: Exception =>
        println(e.getMessage)
        false
    }
  }

  def fetch(): Seq[Int] = {
    try {
      val file = new File(path)
      if(!file.exists()) return Seq()
      val data = Source.fromFile(file).mkString
      if(data.trim == "") Seq()
      else data.split(",").map(_.toInt).toSeq
    }
    catch {
      case e: Exception =>
         println(e.getMessage)
         Seq()
    }
  }

  def delete(): Boolean = {

    try {
       val file = new File(path)
       if(file.exists()) file.delete()
      else false
    }
    catch {
       case e: Exception =>
         println(e.getMessage)
         false
    }
  }
}
