package main.repository;

import main.entity.Goods;
import main.entity.Sales;
import main.entity.Warehouse1;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface GoodsRepository extends CrudRepository<Goods, Long> {

    @Query ("select g from Goods g where g.name = ?1")
    List<Goods> goodsWithName(String name);

    @Transactional
    @Modifying
    @Query("update Goods g set g.priority = ?1 where g.id = ?2")
    int updatePriorityById(float priority, Long id);
}
