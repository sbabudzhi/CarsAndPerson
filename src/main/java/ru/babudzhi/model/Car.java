package ru.babudzhi.model;

import javax.persistence.*;

@Entity
@Table(name = "car" ,schema = "cars")
public class Car {
    private Long id = null;
    private String model = "";
    private Integer horsePower = null;
    private Person owner = null;

    public Car() {
    }

    public Car(Long id, String model, Integer horsePower) {
        this.id = id;
        this.model = model;
        this.horsePower = horsePower;
        this.owner = owner;
    }

    public Car(String model, Integer horsePower, Person owner) {
        this.model = model;
        this.horsePower = horsePower;
        this.owner = owner;
    }


    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "car_generator")
//    @SequenceGenerator(name="car_generator", sequenceName = "car_seq", allocationSize = 0)
    @Column(name = "ID", nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "MODEL", nullable = false, length = 45)
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Basic
    @Column(name = "HORSEPOWER", nullable = false)
    public Integer getHorsePower() {
        return horsePower;
    }

    public void setHorsePower(Integer horsepower) {
        this.horsePower = horsepower;
    }

    @ManyToOne()
    @JoinColumn (name = "OWNER")
    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Car car = (Car) o;

        if (id != car.id) return false;
        if (horsePower != car.horsePower) return false;
        if (model != null ? !model.equals(car.model) : car.model != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (model != null ? model.hashCode() : 0);
        result = 31 * result + horsePower;
        return result;
    }
}