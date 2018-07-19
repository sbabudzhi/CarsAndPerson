package ru.babudzhi.model;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "person", schema = "cars")
public class Person {

    private Long id = null;
    private String name = null;
    private Date birthdate = null;
    private Set<Car> carSet = new HashSet<>();

    public Person(Long id, String name, Date birthdate, Set<Car> carSet) {
        this.id = id;
        this.name = name;
        this.birthdate = birthdate;
        this.carSet = carSet;
    }
    public Person(Long id, String name, Date birthdate) {
        this.id = id;
        this.name = name;
        this.birthdate = birthdate;
    }

    public Person(Long id) {
       this.id = id;
    }

    public Person() { }

    public Person(Long id, String name) {
        this.id = id;
        this.name = name;
        }

    @OneToMany(mappedBy = "owner")
    public Set<Car> getCarSet() {
        return carSet;
    }

    public void setCarSet(Set<Car> carSet) {
        this.carSet = carSet;
    }

    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "person_generator")
//    @SequenceGenerator(name="person_generator", sequenceName = "person_seq", allocationSize = 0)
    @Column(name = "ID", nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "NAME", nullable = false, length = 45)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "BIRTHDATE", nullable = false)
    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (id != person.id) return false;
        if (name != null ? !name.equals(person.name) : person.name != null) return false;
        if (birthdate != null ? !birthdate.equals(person.birthdate) : person.birthdate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (birthdate != null ? birthdate.hashCode() : 0);
        return result;
    }
}
