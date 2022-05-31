package main;

import main.entity.*;
import main.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collections;

@Component
public class TestDataInit implements CommandLineRunner {

    @Autowired
    GoodsRepository goodsRepository;

    @Autowired
    SalesRepository saleRepository;

    @Autowired
    Warehouse1Repository warehouse1Repository;

    @Autowired
    Warehouse2Repository warehouse2Repository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder pwdEncoder;

    @Override
    public void run(String... args) throws Exception {
        Goods good1 = new Goods("good1", 1);
        Goods good2 = new Goods("good2", 2);
        goodsRepository.save(good1);
        goodsRepository.save(good2);

        Instant instant1 = Instant.now();
        Timestamp sqlTimestamp = Timestamp.from(instant1);

        Sales sale1 = new Sales(5, Timestamp.valueOf(String.valueOf(sqlTimestamp)), good1);
        Sales sale2 = new Sales(3, Timestamp.valueOf(String.valueOf(sqlTimestamp)), good2);
        saleRepository.save(sale1);
        saleRepository.save(sale2);

        Warehouse1 warehouse1 = new Warehouse1(6, good1);
        Warehouse1 warehouse1_2 = new Warehouse1(4, good2);
        warehouse1Repository.save(warehouse1);
        warehouse1Repository.save(warehouse1_2);

        Warehouse2 warehouse2 = new Warehouse2(5, good1);
        Warehouse2 warehouse2_2 = new Warehouse2(10, good2);
        warehouse2Repository.save(warehouse2);
        warehouse2Repository.save(warehouse2_2);

        userRepository.save(new User("admin", pwdEncoder.encode("apwd"), Collections.singletonList("ROLE_ADMIN")));
    }
}
