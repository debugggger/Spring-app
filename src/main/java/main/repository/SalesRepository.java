package main.repository;

import main.entity.Sales;
import main.entity.Warehouse1;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.sql.Timestamp;
import java.util.List;

public interface SalesRepository extends CrudRepository<Sales, Long> {

    @Query("select s from Sales s where s.good.name = ?1")
    List<Sales> findSalesWithName(String name);
}
