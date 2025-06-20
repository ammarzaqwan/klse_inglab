package com.inglab.klse.controller;


import com.inglab.klse.model.Stock;
import com.inglab.klse.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class StockController {

    private final StockService service;

    @GetMapping("api/stocks")
    public ResponseEntity<Stock> addStock() throws IOException {
        Stock stock = service.retrieveStock();
        return ResponseEntity.ok(stock);
    }

}
