
#!/bin/bash
######################################################
######################################################
########    Author: Blake Hudson
########    File: Provides test queries for REST API
######################################################
######################################################

########################################################## Driver Creation ##########################################################
declare -i longCounter=0
declare -i port=9080
## Creation around 20 latitude
for i in {1..10000}
do
    echo "$(curl -X POST "http://localhost:${port}/api/drivers/location" -H "accept: application/json" -H "Content-Type: application/json" -d "{\"driverID\":\"user_1_${i}@gmail.com\",\"latitude\":\"10\",\"longitude\":\"${longCounter}\"}" | jq -r '.vcode')"
    ((longCounter++))
done
## Creation around 60 latitude
longCounter=0
for i in {1..10000}
do
    echo "$(curl -X POST "http://localhost:${port}/api/drivers/location" -H "accept: application/json" -H "Content-Type: application/json" -d "{\"driverID\":\"user_2_${i}@gmail.com\",\"latitude\":\"60\",\"longitude\":\"${longCounter}\"}" | jq -r '.vcode')"
    ((longCounter++))
done
## Creation around 90 latitude
longCounter=0
for i in {1..10000}
do
    echo "$(curl -X POST "http://localhost:${port}/api/drivers/location" -H "accept: application/json" -H "Content-Type: application/json" -d "{\"driverID\":\"user_3_${i}@gmail.com\",\"latitude\":\"90\",\"longitude\":\"${longCounter}\"}" | jq -r '.vcode')"
    ((longCounter++))
done
########################################################## Store Creation ##########################################################


## Creation around 20 latitude
echo "$(curl -X POST "http://localhost:${port}/api/stores/location" -H "accept: application/json" -H "Content-Type: application/json" -d "{\"storeID\":\"1234\",\"latitude\":\"15\",\"longitude\":\"0\"}" | jq -r '.vcode')"

## Creation around 60 latitude
echo "$(curl -X POST "http://localhost:${port}/api/stores/location" -H "accept: application/json" -H "Content-Type: application/json" -d "{\"storeID\":\"4567\",\"latitude\":\"65\",\"longitude\":\"0\"}" | jq -r '.vcode')"

## Creation around 90 latitude
echo "$(curl -X POST "http://localhost:${port}/api/stores/location" -H "accept: application/json" -H "Content-Type: application/json" -d "{\"storeID\":\"7890\",\"latitude\":\"95\",\"longitude\":\"0\"}" | jq -r '.vcode')"