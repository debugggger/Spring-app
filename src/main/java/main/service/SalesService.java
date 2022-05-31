package main.service;

import javassist.NotFoundException;
import main.entity.Sales;

import java.util.List;

public interface SalesService {
    List<Sales> listSales();
    Sales findSale(long id) throws NotFoundException;
    void deleteSale(Long id) throws NotFoundException;
    Sales addSale(Sales sales);
    List<Sales> findSalesByGoodName(String name);
}
