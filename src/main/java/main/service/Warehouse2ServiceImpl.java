package main.service;

import javassist.NotFoundException;
import main.entity.Warehouse1;
import main.entity.Warehouse2;
import main.repository.Warehouse2Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class Warehouse2ServiceImpl implements Warehouse2Service {
    @Autowired
    private Warehouse2Repository warehouse2Repository;

    @Override
    public List<Warehouse2> listWarehouse2() {
        return (List<Warehouse2>) warehouse2Repository.findAll();
    }

    @Override
    public Warehouse2 findWarehouse2(long id) throws NotFoundException {
        Optional<Warehouse2> optionalWarehouse1 = warehouse2Repository.findById(id);
        if (optionalWarehouse1.isPresent()){
            return optionalWarehouse1.get();
        }
        else {
            throw new NotFoundException("not found");
        }
    }

    @Override
    public void deleteWarehouse2(Long id) throws NotFoundException {
        if(!warehouse2Repository.existsById(id)) {
            throw new NotFoundException("not found");
        }
        warehouse2Repository.deleteById(id);
    }

    @Override
    public List<Warehouse2> listWarehouse2Goods(long id) {
        return warehouse2Repository.findGoodOnWH2WithId(id);
    }

    @Override
    public Warehouse2 addWarehouse2(Warehouse2 warehouse) {
        return warehouse2Repository.save(warehouse);
    }

    @Override
    public List<Warehouse2> listWarehouse2WithPriority(float priority) {
        return warehouse2Repository.findGoodsWithHighPriority(priority);
    }

    @Override
    public List<Warehouse2> listWarehouse2WithName(String name) {
        return warehouse2Repository.findGoodOnWH2WithName(name);
    }

    @Override
    public long updateCount(Integer count, Long id) throws NotFoundException{
        if (!warehouse2Repository.existsById(id)){
            throw new NotFoundException("Good not found");
        }
        return warehouse2Repository.updateCount(count, id);
    }
}
