package main.service;

import javassist.NotFoundException;
import main.entity.Warehouse1;
import main.repository.Warehouse1Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class Warehouse1ServiceImpl implements Warehouse1Service {
    @Autowired
    private Warehouse1Repository warehouse1Repository;

    @Override
    public List<Warehouse1> listWarehouse1() {
        return (List<Warehouse1>) warehouse1Repository.findAll();
    }

    @Override
    public Warehouse1 findWarehouse1(long id) throws NotFoundException {
        Optional<Warehouse1> optionalWarehouse1 = warehouse1Repository.findById(id);
        if (optionalWarehouse1.isPresent()){
            return optionalWarehouse1.get();
        }
        else {
            throw new NotFoundException("not found");
        }
    }

    @Override
    public void deleteWarehouse1(Long id) throws NotFoundException {
        if(!warehouse1Repository.existsById(id)) {
            throw new NotFoundException("not found");
        }
        warehouse1Repository.deleteById(id);
    }

    @Override
    public Warehouse1 addWarehouse1(Warehouse1 warehouse) {
        return warehouse1Repository.save(warehouse);
    }

    @Override
    public List<Warehouse1> listWarehouse1WithPriorityUnder(float priority) {
        return warehouse1Repository.findGoodsOnWH1WithPriorityUnder(priority);
    }

    @Override
    public List<Warehouse1> listWarehouse1Goods(long id) {
        return warehouse1Repository.findGoodOnWH1WithId(id);
    }

    @Override
    public List<Warehouse1> listWarehouse1WithName(String name) {
        return warehouse1Repository.findGoodOnWH1WithName(name);
    }

    @Override
    public long updateCount(Integer count, Long id) throws NotFoundException{
        if (!warehouse1Repository.existsById(id)){
            throw new NotFoundException("Good not found");
        }
        return warehouse1Repository.updateCount(count, id);
    }
}
