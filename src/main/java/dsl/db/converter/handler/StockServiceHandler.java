/*
 * Author Steven Yeoh
 * Copyright (c) 2021. All rights reserved.
 */

package dsl.db.converter.handler;

import dsl.db.converter.domain.StockEntity;
import dsl.db.converter.object.Stock;
import dsl.db.converter.repository.StockRepository;
import dsl.db.converter.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service("stockService")
@Transactional
public class StockServiceHandler implements StockService
{
    @Autowired
    private StockRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<Stock> list()
    {
        return repository.findAll().stream().map(this::fromEntity).collect(Collectors.toList());
    }

    private Stock fromEntity(StockEntity entity)
    {
        Stock stock = new Stock();
        stock.setId(entity.getId());
        stock.setName(entity.getName());
        stock.setDescription(entity.getDescription());
        stock.setPrice(entity.getPrice());
        stock.setQuantity(entity.getQuantity());
        return stock;
    }
}
