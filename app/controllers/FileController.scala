package controllers

import javax.inject._
import play.api.mvc._
import play.api.libs.json._
import services.FileService
import models.SaveRequest



@Singleton
class FileController @Inject()(cc: ControllerComponents,fileService: FileService) extends AbstractController(cc) {

    def save = Action(parse.json) { request =>
         val json = request.body.validate[SaveRequest]
    if(json.isError){
          BadRequest(Json.obj("status"-> "failed"))
    }
    else{
      val data = json.get
      if(data.start > data.end){
        BadRequest(Json.obj("status"-> "failed"))
      } else{
        val ok = fileService.save(data.start, data.end)
        if(ok) Ok(Json.obj("status" -> "success"))
        else InternalServerError(Json.obj("status" -> "failed"))
      }
    }
  }

  def fetch = Action {
    val nums = fileService.fetch()
    Ok(Json.obj("res" -> nums))
  }

  def delete =   Action {

    val ok = fileService.delete()
    Ok(Json.obj("success"-> ok))
  }
}
