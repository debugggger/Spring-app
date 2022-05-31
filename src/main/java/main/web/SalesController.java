package main.web;

import javassist.NotFoundException;
import main.entity.Goods;
import main.entity.Sales;
import main.service.SalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/bt")
public class SalesController {
    private SalesService salesService;

    @PostMapping(value = "/addSale")
    public Sales addSale(@RequestBody Sales newSale){
        return salesService.addSale(newSale);
    }

    @GetMapping("/sales")
    public ResponseEntity<List<Sales>> getAllSales(){
        List<Sales> list = salesService.listSales();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/findSale/{id}")
    public ResponseEntity<Sales> getSale(@PathVariable("id") long id){
        try{
            return new ResponseEntity<>(salesService.findSale(id), HttpStatus.OK);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "sale not found");
        }
    }

    @DeleteMapping("/deleteSale/{id}")
    public ResponseEntity<List<Sales>> deleteGood(@PathVariable("id") long id) {
        try{
            salesService.deleteSale(id);
            List<Sales> list = salesService.listSales();
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "sale not found");
        }
    }

    @GetMapping("/salesByName/{name}")
    public ResponseEntity<List<Sales>> findGoodsByName(@PathVariable("name") String name){
        List<Sales> list = salesService.findSalesByGoodName(name);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @Autowired
    public void setSalesService(SalesService salesService) {
        this.salesService = salesService;
    }
}
