package main.web;

import javassist.NotFoundException;
import main.entity.Goods;
import main.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/bt")
public class GoodsController {
    private GoodsService goodsService;

    @PostMapping(value = "/addGood")
    public Goods addGood(@RequestBody Goods newGood){
        return goodsService.addGood(newGood);
    }

    @GetMapping("/goods")
    public ResponseEntity<List<Goods>> getAllGoods(){
        List<Goods> list = goodsService.listGoods();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/findGood/{id}")
    public ResponseEntity<Goods> getGood(@PathVariable("id") long id){
        try{
            return new ResponseEntity<>(goodsService.findGood(id), HttpStatus.OK);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "good not found");
        }
    }

    @DeleteMapping("/deleteGood/{id}")
    public ResponseEntity<List<Goods>> deleteGood(@PathVariable("id") long id){
        try{
            goodsService.deleteGood(id);
            List<Goods> list = goodsService.listGoods();
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "good not found");
        }
    }

    @GetMapping("/goodsByName/{name}")
    public ResponseEntity<List<Goods>> findGoodsByName(@PathVariable("name") String name){
        List<Goods> list = goodsService.findByName(name);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PutMapping("/updateGood/{id}/{priority}")
    public ResponseEntity<List<Goods>> updateGood(@PathVariable("id") Long id, @PathVariable("priority") float priority){
        try{
            goodsService.updatePriority(priority, id);
            List<Goods> list = goodsService.listGoods();
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "good not found");
        }
    }

    @Autowired
    public void setGoodsService(GoodsService goodService) {
        this.goodsService = goodService;
    }
}
