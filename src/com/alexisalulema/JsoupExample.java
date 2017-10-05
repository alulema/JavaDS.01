package com.alexisalulema;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JsoupExample {
    public static void run() throws IOException {

        InputStream fStream = Main.class.getResourceAsStream("asian-view.html");
        InputStreamReader fReader = new InputStreamReader(fStream, StandardCharsets.UTF_8);
        StringBuilder entireText = new StringBuilder();

        try (Stream<String> lines = new BufferedReader(fReader).lines()) {
            List<String> allLines = lines.collect(Collectors.toList());

            for (String l: allLines) {
                entireText.append(l);
            }
        }

        fReader.close();
        fStream.close();

        String rawHtml = entireText.toString(); // UrlUtils.request("https://www.kaggle.com/c/avito-duplicate-ads-detection/leaderboard");
        Document document = Jsoup.parse(rawHtml);
        Elements tableRows = document.select("#mainTable tr");

        for (Element tr : tableRows) {
            Elements columns = tr.select("td");

            if (columns.isEmpty())
                continue;

            String ob1 = columns.get(2).text();
            System.out.println(ob1);
        }
    }
}
