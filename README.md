
This repo has a simple example to produce messages to a Kafka topic using Java 11 & Spring Boot.


## Kafka Setup in Mac

- Download Kafka installation file from https://kafka.apache.org/downloads

```bash
wget https://dlcdn.apache.org/kafka/3.2.0/kafka_2.13-3.2.0.tgz

# Extract it `tar -xzf kafka_2.13-3.2.0.tgz`
cd kafka_2.13-3.2.0/

```

- Start Zookeeper
```bash
bin/zookeeper-server-start.sh config/zookeeper.properties
```

On successful start, you should see a log message as follows.
It means the Zookeeper server is successfully running on port 2128 locally.

```bash
 INFO binding to port 0.0.0.0/0.0.0.0:2181 (org.apache.zookeeper.server.NIOServerCnxnFactory)
```

- Start Kafka Broker

```bash
bin/kafka-server-start.sh config/server.properties
```

On successful start, you should see a log message as follows. 
It means the Kafka server is successfully running on port 9092 locally.

```bash
INFO [KafkaServer id=0] started (kafka.server.KafkaServer)
```

## Kafka Topic Setup

To create a Kafka topic

> Note: Avoid '_' in topic names

```bash
bin/kafka-topics.sh --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic fizaltopic1
```

## Kafka Producer and Consumer Examples

To publish messages to a topic using CLI

```bash
bin/kafka-console-producer.sh --broker-list localhost:9092 --topic fizaltopic1
```

To consumer messages from a topic using CLI

```bash
bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic fizaltopic1 --from-beginning
```

To start this Spring Boot application

```bash
./gradlew bootRun
```

To publish a message hitting this application API endpoint

```bash
curl http://localhost:8080/kafka/producer\?message\=HelloWorld
```