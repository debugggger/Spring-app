package main.web;

import javassist.NotFoundException;
import main.entity.Goods;
import main.entity.Sales;
import main.entity.Warehouse1;
import main.service.Warehouse1Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/bt")
public class Warehouse1Controller {
    private Warehouse1Service warehouse1Service;

    @PostMapping(value = "/addWarehouse1")
    public Warehouse1 addWarehouse1(@RequestBody Warehouse1 newWarehouse){
        return warehouse1Service.addWarehouse1(newWarehouse);
    }

    @GetMapping("/warehouse1")
    public ResponseEntity<List<Warehouse1>> getAllWarehouse1(){
        List<Warehouse1> list = warehouse1Service.listWarehouse1();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/findWarehouse1/{id}")
    public ResponseEntity<Warehouse1> getWarehouse1(@PathVariable("id") long id){
        try{
            return new ResponseEntity<>(warehouse1Service.findWarehouse1(id), HttpStatus.OK);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "warehouse not found");
        }
    }

    @DeleteMapping("/deleteWarehouse1/{id}")
    public ResponseEntity<List<Warehouse1>> deleteWarehouse1(@PathVariable("id") long id) {
        try{
            warehouse1Service.deleteWarehouse1(id);
            List<Warehouse1> list = warehouse1Service.listWarehouse1();
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "warehouse not found");
        }
    }

    @GetMapping("/warehouse1ByName/{name}")
    public ResponseEntity<List<Warehouse1>> findWarehouse1ByName(@PathVariable("name") String name){
        List<Warehouse1> list = warehouse1Service.listWarehouse1WithName(name);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/warehouse1ByGoodId/{id}")
    public ResponseEntity<List<Warehouse1>> findWarehouse1ByName(@PathVariable("id") long id){
        List<Warehouse1> list = warehouse1Service.listWarehouse1Goods(id);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/warehouse1PriorityUnder/{priority}")
    public ResponseEntity<List<Warehouse1>> findWarehouse1PriorityUnder(@PathVariable("priority") float priority){
        List<Warehouse1> list = warehouse1Service.listWarehouse1WithPriorityUnder(priority);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PutMapping("/updateWarehouse1/{id}/{goodCount}")
    public ResponseEntity<List<Warehouse1>> updateGood(@PathVariable("id") Long id, @PathVariable("goodCount") Integer goodCount){
        try{
            warehouse1Service.updateCount(goodCount, id);
            List<Warehouse1> list = warehouse1Service.listWarehouse1();
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "warehouse not found");
        }
    }

    @Autowired
    public void setWarehouse1Service(Warehouse1Service warehouse1Service) {
        this.warehouse1Service = warehouse1Service;
    }
}
