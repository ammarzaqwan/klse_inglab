package com.inglab.klse.service;

import com.inglab.klse.model.Stock;
import org.springframework.stereotype.Service;

import java.io.IOException;

public interface StockService {

    Stock retrieveStock() throws IOException;
}
