package com.inglab.klse.service;

import com.inglab.klse.model.Stock;
import com.inglab.klse.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor

public class StockServiceImpl implements StockService {

    private final StockRepository repository;

    // part of the html has this line of code
//{ const size = 180; const gainers = 0; const losers = 0; const unchanged = 0; const untraded = 2419; const values = [gainers, losers, unchanged, untraded];
    public  Stock  retrieveStock() throws IOException {

        URL url;
        try {
            url = new URL("https://klse.i3investor.com/web/plugin/market-summary");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        Stock stock = new Stock();

        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", "Mozilla/5.0");

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder html = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            html.append(inputLine);
        }
        in.close();

        Pattern pattern = Pattern.compile("const\\s+gainers\\s*=\\s*(\\d+);.*?" +
                        "const\\s+losers\\s*=\\s*(\\d+);.*?" +
                        "const\\s+unchanged\\s*=\\s*(\\d+);.*?" +
                        "const\\s+untraded\\s*=\\s*(\\d+);",
                Pattern.DOTALL);

        Matcher matcher = pattern.matcher(html.toString());

        // if (matcher.find()) {
        //     int gainers = Integer.parseInt(matcher.group(1));
        //     int losers = Integer.parseInt(matcher.group(2));
        //     int unchanged = Integer.parseInt(matcher.group(3));
        //     int untraded = Integer.parseInt(matcher.group(4));

        //     System.out.println("gain : " + gainers);
        //     System.out.println("lose : " + losers);
        //     System.out.println("unchange : " + unchanged);
        //     System.out.println("untraded : " + untraded);
        // } else {
        //     System.out.println("Pattern not found.");
        // }


        if (matcher.find()) {

            stock.setNumGainers(Integer.parseInt(matcher.group(1)));
            stock.setNumLosers(Integer.parseInt(matcher.group(2)));
            stock.setRecordDatetime(LocalDateTime.now());

        } else {
            System.out.println("Pattern not found.");
        }

        try {
            Stock saved = repository.save(stock);
            return saved;
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Failed to save stock: " + e.getMessage(), e);
        }


    }


}






