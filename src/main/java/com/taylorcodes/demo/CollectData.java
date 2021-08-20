package com.taylorcodes.demo;

import java.io.BufferedReader;
// import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class CollectData {
    public static void main(String[] args) throws IOException {
        System.out.print("Hello World!\n\n");

        readCSV();
    }// END main()

    public static void readCSV() throws IOException {

        // https://www.baeldung.com/opencsv
        // List<List<String>> records = new ArrayList<List<String>>();
        // try (CSVReader csvReader = new CSVReader(new FileReader("book.csv"));) {
        //     String[] values = null;
        //     while ((values = csvReader.readNext()) != null) {
        //         records.add(Arrays.asList(values));
        //     }
        // }

        Properties props = new Properties();

        props.put("bootstrap.servers", "localhost:9092, localhost:9093, localhost:9094");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(props);

        List<List<String>> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/data.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                records.add(Arrays.asList(values));
                System.out.println(line);
                producer.send(new ProducerRecord<String,String>("DATA-COLLECTION", line, line));
            }// end while()
            System.out.print("\n" + records);

            producer.close();
        }

        System.exit(0);

    }// END readCSV()    

}// END class
