package main.service;

import javassist.NotFoundException;
import main.entity.Goods;
import main.entity.Warehouse1;
import main.repository.GoodsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    private GoodsRepository goodsRepository;

    @Override
    public List<Goods> listGoods() {
        return (List<Goods>) goodsRepository.findAll();
    }

    @Override
    public Goods findGood(long id) throws NotFoundException {
        Optional<Goods> optionalGoods = goodsRepository.findById(id);
        if (optionalGoods.isPresent()){
            return optionalGoods.get();
        }
        else{
            throw new NotFoundException("Good not found");
        }
    }

    @Override
    public List<Goods> findByName(String name){
        return goodsRepository.goodsWithName(name);
    }

    @Override
    public float updatePriority(float priority, Long id) throws NotFoundException{
        if (!goodsRepository.existsById(id)){
            throw new NotFoundException("Good not found");
        }
        return goodsRepository.updatePriorityById(priority, id);
    }

    @Override
    public void deleteGood(Long id) throws NotFoundException{
        if(!goodsRepository.existsById(id)){
            throw new NotFoundException("Good not found");
        }
        goodsRepository.deleteById(id);
    }

    @Override
    public Goods addGood(Goods goods) {
        return goodsRepository.save(goods);
    }
}
