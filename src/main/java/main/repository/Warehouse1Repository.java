package main.repository;

import main.entity.Goods;
import main.entity.Sales;
import main.entity.Warehouse1;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface Warehouse1Repository extends CrudRepository<Warehouse1, Long> {

    @Query("select w from Warehouse1 w where w.good.priority < ?1")
    List<Warehouse1> findGoodsOnWH1WithPriorityUnder(float priority);

    @Query("select w from Warehouse1 w where w.good.name = ?1")
    List<Warehouse1> findGoodOnWH1WithName(String name);

    @Query("select w from Warehouse1 w where w.good.id = ?1")
    List<Warehouse1> findGoodOnWH1WithId(long id);

    @Transactional
    @Modifying
    @Query("update Warehouse1 w set w.goodCount = ?1 where w.id = ?2")
    int updateCount(Integer count, Long id);
}
