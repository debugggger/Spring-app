package main.repository;

import main.entity.Warehouse1;
import main.entity.Warehouse2;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface Warehouse2Repository extends CrudRepository<Warehouse2, Long> {

    @Query("select w from Warehouse2 w where w.good.priority > ?1")
    List<Warehouse2> findGoodsWithHighPriority(float priority);

    @Query("select w from Warehouse2 w where w.good.name = ?1")
    List<Warehouse2> findGoodOnWH2WithName(String name);

    @Query("select w from Warehouse2 w where w.good.id = ?1")
    List<Warehouse2> findGoodOnWH2WithId(long id);

    @Transactional
    @Modifying
    @Query("update Warehouse2 w set w.goodCount = ?1 where w.id = ?2")
    int updateCount(Integer count, Long id);
}
