## PoC of Apache Camel, Spring Boot, Redis and AMQP working together

`MessageQueueRoute` is listening for messages (an upload id). When a message is received, it is multicasted to `FileDownloadRoute`, which downloads `customer.json`, after that,  `CustomerRoute` extracts customer's names and inserts them into a Redis set.

Service providers:
* CloudAMQP - RabbitMQ
* Dropbox - cloud file storage
* Redis Labs - Redis database

### Example of processing:

```
Receiving upload id: 8f9ed0f5-1440-4754-954a-4ef4da1a2093
Downloading file for upload id: 8f9ed0f5-1440-4754-954a-4ef4da1a2093
Inserting customers into Redis for upload id: 8f9ed0f5-1440-4754-954a-4ef4da1a2093
Konoba Ltd was inserted into Redis
Obel Inc was inserted into Redis
```

### Unit tests
* `CustomerRouteTest`