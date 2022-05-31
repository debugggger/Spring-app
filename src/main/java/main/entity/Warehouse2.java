package main.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class Warehouse2 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JsonIgnoreProperties({"name", "priority"})
    @ManyToOne
    @JoinColumn(name = "good_id")
    private Goods good;

    @Column(name = "good_count")
    private Integer goodCount;

    public Warehouse2(){

    }

    public Warehouse2(Integer goodCount, Goods good){
        this.good = good;
        this.goodCount = goodCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getGoodCount() {
        return goodCount;
    }

    public void setGoodCount(Integer goodCount) {
        this.goodCount = goodCount;
    }

    public Goods getGood() {
        return good;
    }

    public void setGood(Goods good) {
        this.good = good;
    }

    @Override
    public String toString() {
        return "Warehouse{" +
                "id=" + id +
                ", goodId=" + good.getId() +
                ", goodCount=" + goodCount +
                '}';
    }
}
