package controllers

import models.Employee
import play.api.libs.json.{JsValue, Json}
import play.api.mvc._
import reactivemongo.bson.BSONObjectID
import repositories.EmployeeRepository

import javax.inject._
import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}
/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class EmployeeController @Inject()(
                                 implicit executionContext: ExecutionContext,
                                 val employeeRepository: EmployeeRepository,
                                 val controllerComponents: ControllerComponents)
  extends BaseController {
  def findAll(): Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
    employeeRepository.findAll().map {
      employees => Ok(Json.toJson(employees))
    }
  }

  def findOne(id: String): Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
    val objectIdTryResult = BSONObjectID.parse(id)
    objectIdTryResult match {
      case Success(objectId) => employeeRepository.findOne(objectId).map {
        employee => Ok(Json.toJson(employee))
      }
      case Failure(_) => Future.successful(BadRequest("Cannot parse the movie id"))
    }
  }

  def create(): Action[JsValue] = Action.async(controllerComponents.parsers.json) { implicit request => {

    request.body.validate[Employee].fold(
      _ => Future.successful(BadRequest("Cannot parse request body")),
      employee =>
        employeeRepository.create(employee).map {
          _ => Created(Json.toJson(employee))
        }
    )
  }
  }

  def update(
              id: String): Action[JsValue] = Action.async(controllerComponents.parsers.json) { implicit request => {
    request.body.validate[Employee].fold(
      _ => Future.successful(BadRequest("Cannot parse request body")),
      employee => {
        val objectIdTryResult = BSONObjectID.parse(id)
        objectIdTryResult match {
          case Success(objectId) => employeeRepository.update(objectId, employee).map {
            result => Ok(Json.toJson(result.ok))
          }
          case Failure(_) => Future.successful(BadRequest("Cannot parse the movie id"))
        }
      }
    )
  }
  }

  def delete(id: String): Action[AnyContent] = Action.async { implicit request => {
    val objectIdTryResult = BSONObjectID.parse(id)
    objectIdTryResult match {
      case Success(objectId) => employeeRepository.delete(objectId).map {
        _ => NoContent
      }
      case Failure(_) => Future.successful(BadRequest("Cannot parse the employee id"))
    }
  }
  }
}