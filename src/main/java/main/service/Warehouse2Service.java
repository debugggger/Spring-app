package main.service;

import javassist.NotFoundException;
import main.entity.Warehouse1;
import main.entity.Warehouse2;

import java.util.List;

public interface Warehouse2Service {
    List<Warehouse2> listWarehouse2();
    Warehouse2 findWarehouse2(long id) throws NotFoundException;
    void deleteWarehouse2(Long id) throws NotFoundException;
    Warehouse2 addWarehouse2(Warehouse2 warehouse);
    List<Warehouse2> listWarehouse2WithPriority(float priority);
    List<Warehouse2> listWarehouse2WithName(String name);
    List<Warehouse2> listWarehouse2Goods(long id) ;
    long updateCount(Integer count, Long id) throws NotFoundException;
}
