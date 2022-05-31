package main.web;

import javassist.NotFoundException;
import main.entity.Warehouse1;
import main.entity.Warehouse2;
import main.service.Warehouse2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/bt")
public class Warehouse2Controller {
    private Warehouse2Service warehouse2Service;

    @PostMapping(value = "/addWarehouse2")
    public Warehouse2 addWarehouse2(@RequestBody Warehouse2 newWarehouse){
        return warehouse2Service.addWarehouse2(newWarehouse);
    }

    @GetMapping("/warehouse2")
    public ResponseEntity<List<Warehouse2>> getAllWarehouse2(){
        List<Warehouse2> list = warehouse2Service.listWarehouse2();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/findWarehouse2/{id}")
    public ResponseEntity<Warehouse2> getWarehouse1(@PathVariable("id") long id){
        try{
            return new ResponseEntity<>(warehouse2Service.findWarehouse2(id), HttpStatus.OK);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "warehouse not found");
        }
    }

    @DeleteMapping("/deleteWarehouse2/{id}")
    public ResponseEntity<List<Warehouse2>> deleteWarehouse1(@PathVariable("id") long id) {
        try{
            warehouse2Service.deleteWarehouse2(id);
            List<Warehouse2> list = warehouse2Service.listWarehouse2();
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "warehouse not found");
        }
    }

    @GetMapping("/warehouse2ByName/{name}")
    public ResponseEntity<List<Warehouse2>> findWarehouse1ByName(@PathVariable("name") String name){
        List<Warehouse2> list = warehouse2Service.listWarehouse2WithName(name);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/warehouse2ByGoodId/{id}")
    public ResponseEntity<List<Warehouse2>> findWarehouse1ByName(@PathVariable("id") long id){
        List<Warehouse2> list = warehouse2Service.listWarehouse2Goods(id);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PutMapping("/updateWarehouse2/{id}/{goodCount}")
    public ResponseEntity<List<Warehouse2>> updateGood(@PathVariable("id") Long id, @PathVariable("goodCount") Integer goodCount){
        try{
            warehouse2Service.updateCount(goodCount, id);
            List<Warehouse2> list = warehouse2Service.listWarehouse2();
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "warehouse not found");
        }
    }

    @GetMapping("/warehouse2Priority/{priority}")
    public ResponseEntity<List<Warehouse2>> findWarehouse1PriorityUnder(@PathVariable("priority") float priority){
        List<Warehouse2> list = warehouse2Service.listWarehouse2WithPriority(priority);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @Autowired
    public void setWarehouse2Service(Warehouse2Service warehouse2Service) {
        this.warehouse2Service = warehouse2Service;
    }
}
