package ru.babudzhi.model;

import javax.persistence.*;

@Entity
@Table(name = "CAR")
public class Car {
    private Long id;
    private String model;
    private int horsePower;
    private Person owner;

    public Car() {
    }

    public Car(Long id, String model, int horsePower, Person owner) {
        this.id = id;
        this.model = model;
        this.horsePower = horsePower;
        this.owner = owner;
    }

    public Car(String model, int horsePower, Person owner) {
        this.model = model;
        this.horsePower = horsePower;
        this.owner = owner;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "person_generator")
    @SequenceGenerator(name="person_generator", sequenceName = " SYSTEM_SEQUENCE_1F4537B9_7184_4519_BD9A_D8745A8B4CB6")
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
    public int getHorsePower() {
        return horsePower;
    }

    public void setHorsePower(int horsepower) {
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
