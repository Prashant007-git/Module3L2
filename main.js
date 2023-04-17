use my_database

//creating collections
db.createCollection("cities")
db.createCollection("stadium")

// inserting document in cities collection
db.cities.insertMany([
  { _id: 1, name: "Mumbai", state: "Maharashtra" },
  { _id: 2, name: "Delhi",  state: "Delhi" },
  { _id: 3, name: "Bengaluru",  state: "Karnataka" },
  { _id: 4, name: "Hyderabad",  state: "Telangana" }
])

// inserting document in stadium collection
db.stadium.insertMany([
  { _id: 1, name: "dy patil", city_id: 1, capacity: 30, rating: 4.5 },
  { _id: 2, name: "kotla", city_id: 2, capacity: 38, rating: 4.3 },
  { _id: 3, name: "chinnaswamy", city_id: 3, capacity: 47, rating: 4.2 },
  { _id: 4, name: "rajiv gandhi stadium", city_id: 4, capacity: 27, rating: 4.6 }
])

// cross join
db.cities.aggregate([
  {
    $lookup: {
      from: "stadium",
      let: {},
      pipeline: [],
      as: "stadium"
    }
  }
])

// inner join

db.cities.aggregate([
  {
    $lookup: {
      from: "stadium",
      let: { cityId: "$_id" },
      pipeline: [
        {
          $match: {
            $expr: {
              $eq: ["$city_id", "$$cityId"]
            }
          }
        }
      ],
      as: "stadium"
    }
  }
])

// left join

db.cities.aggregate([
  {
    $lookup: {
      from: "stadium",
      localField: "_id",
      foreignField: "city_id",
      as: "stadium"
    }
  }
])

// right join

db.stadium.aggregate([
  {
    $lookup: {
      from: "cities",
      localField: "city_id",
      foreignField: "_id",
      as: "city"
    }
  },
  {
    $unwind: {
      path: "$city",
      preserveNullAndEmptyArrays: true
    }
  },
  {
    $group: {
      _id: "$city._id",
      name: { $first: "$city.name" },
      stadium: {
        $push: {
          _id: "$_id",
          name: "$name",
          capacity: "$capacity"
        }
      }
    }
  }
])

// update field

db.stadium.updateOne(
  { name: "rajiv gandhi stadium" },
  { $set: { capacity: 32 } }
)
db.stadium.find()

//create index
db.stadium.createIndex({ name: 1 })

//foreign key
db.stadium.aggregate([
  {
    $lookup: {
      from: "cities",
      localField: "city_id",
      foreignField: "_id",
      as: "city"
    }
  },
  {
    $unwind: "$city"
  }
])



