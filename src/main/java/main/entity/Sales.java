package main.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class Sales {
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

    @Column(name = "create_date")
    private Timestamp createDate;

    public Sales(){

    }

    public Sales(Integer goodCount, Timestamp createDate, Goods good){
        this.good = good;
        this.goodCount = goodCount;
        this.createDate = createDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Goods getGood() {
        return good;
    }

    public void setGood(Goods good) {
        this.good = good;
    }

    public Integer getGoodCount() {
        return goodCount;
    }

    public void setGoodCount(Integer goodCount) {
        this.goodCount = goodCount;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "Sales{" +
                "id=" + id +
                ", goodId=" + good.getId() +
                ", goodCount=" + goodCount +
                ", createDate=" + createDate +
                '}';
    }
}
