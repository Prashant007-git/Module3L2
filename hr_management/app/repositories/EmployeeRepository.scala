package repositories

import models.Employee
import javax.inject._
import reactivemongo.api.bson.collection.BSONCollection
import play.modules.reactivemongo.ReactiveMongoApi
import scala.concurrent.{ExecutionContext, Future}
import reactivemongo.api.{Cursor, ReadPreference}
import reactivemongo.bson.{BSONDocument, BSONObjectID}
import org.joda.time.DateTime
import reactivemongo.api.commands.WriteResult

@Singleton
class EmployeeRepository @Inject()(
                                    implicit executionContext: ExecutionContext,
                                    reactivemongoApi: ReactiveMongoApi
                               ) {
  def collection: Future[BSONCollection] = reactivemongoApi.database.map(db => db.collection("employee"))

  def findAll(limit: Int = 100): Future[Seq[Employee]] = {

    collection.flatMap(
      _.find(BSONDocument(), Option.empty[Employee])
        .cursor[Employee](ReadPreference.Primary)
        .collect[Seq](limit, Cursor.FailOnError[Seq[Employee]]())
    )
  }

  def findOne(id: BSONObjectID): Future[Option[Employee]] = {
    collection.flatMap(_.find(BSONDocument("_id" -> id), Option.empty[Employee]).one[Employee])
  }

  def create(employee: Employee): Future[WriteResult] = {
    collection.flatMap(_.insert(ordered = false)
      .one(employee.copy(_creationDate = Some(new DateTime()), _updateDate = Some(new DateTime()))))
  }

  def update(id: BSONObjectID, employee: Employee):Future[WriteResult] = {

    collection.flatMap(
      _.update(ordered = false).one(BSONDocument("_id" -> id),
        employee.copy(
          _updateDate = Some(new DateTime())))
    )
  }

  def delete(id: BSONObjectID):Future[WriteResult] = {
    collection.flatMap(
      _.delete().one(BSONDocument("_id" -> id), Some(1))
    )
  }
}