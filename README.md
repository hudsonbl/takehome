# Take Home Outline

For scaling this project, if this was something deployed on AWS. I would assign users to be stored within DB's inside their availabitlity zone.
Sectioning off regions by replicating the tables in each zone. With the possibility of either replica servers or a master server containing
all of the data. This would result in querying only for stores within a region as well; which is more logical from the users perspective.

Code Coverage: 61.4%

REST Compliance: I would add HATEOS to the GET request for closest drivers to store. This would allow a paginated response if N is large.
    ex: GET /api/drivers?StoreID=1234&N={100}
    response: 
        {
            drivers: [length = 10]
            nextPage: 2
            prevPage: 1
            currentPage: 1
        }

## Endpoints:
1. End point should store users location
Kafka: id="DriverGroup" topics="topic-driverLocation" type=DriverLocation
POST /api/drivers/location
{
 "driverID": "m123@gmail.com",
 "latitude": 27.876,
 "longitude": -128.33
}

Response:
{
    "ok":"success saving data"
}

2. End point should store a stores location
Kafka: id="StoreGroup" topics="topic-storeLocation" type=StoreLocation
POST /api/stores/location
{
 "storeID": "1234",
 "latitude": 27.876,
 "longitude": -128.33
}

Response:
{
    "ok":"success saving data"
}

3. GET /api/drivers?StoreID={storeId}&N={#}
Kafka: id="GetDriverGroup" topics="topic-getDrivers" type=ArrayList<DriverLocation>

Response: 
{
    "drivers": [
        {
            "dirverID":"m123@gmail.com",
            "distance":"100m"
        },
        {
            "dirverID":"m456@gmail.com",
            "distance":"200m"
        }
    ]
}

## MYSQL Tables:
Example Region Table Names:
    - store_location_NA_west, store_location_NA_central, store_location_NA_east
Table Names:
    - store_location

Table Content: 
|  storeID (U)(K) |   latitude    |   longitude   | 
|-----------------|---------------|---------------|
|       2231      |  27.876       |  -128.33      |
|       2232      |  27.876       |  -128.33      |

Example Region Table Names:
    - driver_location_NA_west, driver_location_NA_central, driver_location_NA_east

Table Names:
    - driver_location

|  driverID (U)(K)|   latitude    |   longitude   | 
|-----------------|---------------|---------------|
| m123@gmail.com  |  27.876       |  -128.33      |
| m456@gmail.com  |  27.876       |  -128.33      |