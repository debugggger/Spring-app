package main.service;

import javassist.NotFoundException;
import main.entity.Goods;
import main.entity.Warehouse1;

import java.util.List;

public interface GoodsService {
    List<Goods> listGoods();
    Goods findGood(long id) throws NotFoundException;
    void deleteGood(Long id) throws NotFoundException;
    Goods addGood(Goods goods);
    List<Goods> findByName(String name);
    float updatePriority(float priority, Long id) throws NotFoundException;
}
