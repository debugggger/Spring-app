package main.service;

import javassist.NotFoundException;
import main.entity.Goods;
import main.entity.Warehouse1;

import java.util.List;

public interface Warehouse1Service {
    List<Warehouse1> listWarehouse1();
    Warehouse1 findWarehouse1(long id) throws NotFoundException;
    void deleteWarehouse1(Long id) throws NotFoundException;
    Warehouse1 addWarehouse1(Warehouse1 warehouse);
    List<Warehouse1> listWarehouse1WithPriorityUnder(float priority);
    List<Warehouse1> listWarehouse1WithName(String name);
    List<Warehouse1> listWarehouse1Goods(long id) ;
    long updateCount(Integer count, Long id) throws NotFoundException;

}
