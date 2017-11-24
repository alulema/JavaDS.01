package com.alexisalulema;

import cyclops.async.LazyReact;
import joinery.DataFrame;
import org.jooq.lambda.tuple.Tuple2;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

class JoineryExample {
    static void run() throws IOException {

        InputStream fStream = Main.class.getResourceAsStream("persons.csv");
        DataFrame<Object> df = DataFrame.readCsv(fStream);
        List<Object> country = df.col("country");
        Map<String, Long> map = LazyReact.sequentialBuilder()
                .from(country)
                .cast(String.class)
                .distinct()
                .zipWithIndex()
                .toMap(Tuple2::v1, Tuple2::v2);

        List<Object> indexes = country.stream()
                .map(map::get)
                .collect(Collectors.toList());

        df = df.drop("country");
        df.add("country_index", indexes);
        System.out.println(df);
        System.out.println("\nNow, let's try Executors");

        DB db = DBMaker.fileDB("urls.db").closeOnJvmShutdown().make();
        HTreeMap<?, ?> htreeMap = db.hashMap("urls").createOrOpen();
        Map<String, String> urls = (Map<String, String>) htreeMap;

        Crawler crawler = new Crawler(30);
        String url = "https://www.kaggle.com/c/avito-duplicate-ads-detection/leaderboard";
        Optional<String> result = crawler.crawl(url);
        if (result.isPresent()) {
            System.out.println("Successfully crawled");
            urls.put(url, result.get());
        }
        crawler.close();
    }
}
