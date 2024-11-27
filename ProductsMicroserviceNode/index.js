import express from "express";
import { Kafka } from "kafkajs";

const app = express();

const kafka = new Kafka({
  retry: 4,

  clientId: "producer-4",
  brokers: ["localhost:9092"], // Kafka broker addresses
});

// Create a Kafka consumer
const consumer = kafka.consumer({ groupId: "product-created-event" });

// Subscribe to a topic
const topic = "product-created-events-topic";
await consumer.connect();
await consumer.subscribe({ topic });

await consumer.run({
  eachMessage: async ({ topic, partition, message }) => {
    console.log(JSON.parse(message.value));
    // Process the message as needed
  },
});

app.listen(3000, () => {
  console.log("Server Listening on port 3000");
});
