package models

import org.joda.time.DateTime
import play.api.libs.json.JodaReads._
import play.api.libs.json.JodaWrites._
import play.api.libs.json.{Format, Json}
import reactivemongo.bson.{BSONObjectID, _}
import reactivemongo.play.json._

case class Employee(
                  _id:Option[BSONObjectID],
                  _creationDate: Option[DateTime],
                  _updateDate: Option[DateTime],
                  name:String,
                  designation:String
                )
object Employee{
  implicit val fmt : Format[Employee] = Json.format[Employee]
  implicit object MovieBSONReader extends BSONDocumentReader[Employee] {
    def read(doc: BSONDocument): Employee = {
      Employee(
        doc.getAs[BSONObjectID]("_id"),
        doc.getAs[BSONDateTime]("_creationDate").map(dt => new DateTime(dt.value)),
        doc.getAs[BSONDateTime]("_updateDate").map(dt => new DateTime(dt.value)),
        doc.getAs[String]("name").get,
        doc.getAs[String]("designation").get)
    }
  }

  implicit object EmployeeBSONWriter extends BSONDocumentWriter[Employee] {
    def write(employee: Employee): BSONDocument = {
      BSONDocument(
        "_id" -> employee._id,
        "_creationDate" -> employee._creationDate.map(date => BSONDateTime(date.getMillis)),
        "_updateDate" -> employee._updateDate.map(date => BSONDateTime(date.getMillis)),
        "name" -> employee.name,
        "designation" -> employee.designation

      )
    }
  }
}
