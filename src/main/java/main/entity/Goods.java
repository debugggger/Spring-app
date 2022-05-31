package main.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
public class Goods {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "priority", nullable = false)
    private float priority;

    @JsonIgnore
    @OneToMany(mappedBy = "good", cascade = CascadeType.ALL)
    private List<Sales> sales;

    @JsonIgnore
    @OneToMany(mappedBy = "good", cascade = CascadeType.ALL)
    private List<Warehouse2> warehouse2;

    @JsonIgnore
    @OneToMany(mappedBy = "good", cascade = CascadeType.ALL)
    private List<Warehouse1> warehouse1;

    public Goods(){

    }

    public Goods(String name, float priority){
        this.name = name;
        this.priority = priority;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPriority() {
        return priority;
    }

    public void setPriority(float priority) {
        this.priority = priority;
    }

    public List<Sales> getSales() {
        return sales;
    }

    public void setSales(List<Sales> sales) {
        this.sales = sales;
    }

    public List<Warehouse2> getWarehouse2() {
        return warehouse2;
    }

    public void setWarehouse2(List<Warehouse2> warehouse2) {
        this.warehouse2 = warehouse2;
    }

    public Collection<Warehouse1> getWarehouse1() {
        return warehouse1;
    }

    public void setWarehouse1(List<Warehouse1> warehouse1) {
        this.warehouse1 = warehouse1;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", priority=" + priority +
                '}';
    }
}
