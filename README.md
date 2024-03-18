# Vehicle Telemetry
Purpose of this project is to provide end user two rest endpoints 
in order to import telemetry data of vehicles used in agriculture . 
One is used for importing data from CSV and another endpoint for filtering imported data
based on field name value and eventually operation.


The core of this solution is built using the Java programming language, and Java Spring Boot framework. 
Given the nature of the telemetry data, which is characterized by its variable structure with numerous columns and the inconsistency across different CSV files, 
a flexible storage approach was essential. 
After a thorough evaluation, I selected MongoDB for persisting data imported from CSVs.
Its schema-less nature makes it particularly well-suited for handling the variability and complexity of the telemetry data, 
offering the flexibility required to accommodate the diverse datasets without the constraints of a fixed schema.

## Setting up project

### Prerequisites

Before starting, ensure you have Docker installed on your machine.

### Running the Project with Docker

To get your Docker environment set up and the application running, follow these steps:

```bash
# Clone the repository
git clone https://github.com/laki990/vehicleTelemetry.git

cd vehicleTelemetry

# Build and start application
docker-compose up --build

```

## Usage
```bash
# Inserting data
curl -X POST -F "file=@LD_A5304997_20230331_20230401.csv" http://localhost:8080/vehicles/importCsv

# Filtering data
# Endpoint POST
localhost:8080/vehicles/filter

# Body request
[{
    "field": "Type",
    "value": "tractor"
},
{
    "field": "Engine load [%]",
    "value": 30,
    "operation": "GreaterThan"
}]


```

## Further improvements
Besides all fields from CSV documents, in MongoDB document is created new field "**Type**" indicating type of vehicle based on
filename. Indexing should be considered. From my point of view i guess user will maybe track just tractors or just combines
not just some specific telemetry data for all vehicle, but it really depends on use case so
there is space for improvements in this part. Also, when it comes to filtering endpoint i think it is good idea 
to add paging.

