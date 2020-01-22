# Batch Execution Demo

Demostrates slow load time for Async execution strategy with graphql-kotlin when loading large result list.

## Run

```bash
mvn package && java -jar target/BatchExecutionDemo-1.0-SNAPSHOT.jar
```

Make queries against endpoings:

* http://localhost:5000/graphqlBatch - batch execution
* http://localhost:5000/graphqlAsync - Async execution

```graphl
query {
  getProjectToc {
    id
    title
    linkId
    parentId
    positionPath
    title
    type
  }
}
```
