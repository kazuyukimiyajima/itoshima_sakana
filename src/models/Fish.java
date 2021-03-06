package models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Table(name = "Fishes")
@NamedQueries({
    @NamedQuery(
            name = "getAllFishes",
            query = "SELECT f FROM Fish AS f ORDER BY f.id DESC"
            ),
    @NamedQuery(
            name = "getFishesCount",
            query = "SELECT COUNT(f) FROM Fish AS f"
            ),
    @NamedQuery(
            name = "getMyAllFishes",
            query = "SELECT f FROM Fish AS f WHERE f.user = :user ORDER BY f.id DESC"
            ),
    @NamedQuery(
            name = "getMyFishesCount",
            query = "SELECT COUNT(f) FROM Fish AS f WHERE f.user = :user"
            ),
})
@Entity
public class Fish {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Column(name = "name", nullable = false)
    private String name;


    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    @Column(name = "created_at", nullable = false)
    private Timestamp created_at;


    @Column(name = "updated_at", nullable = false)
    private Timestamp updated_at;



    public Integer getId(){
        return id;
    }

    public void setId(Integer id){
        this.id = id;
    }



    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }


    public User getUser(){
        return user;
    }
    public void setUser(User user){
        this.user = user;
    }


    public Timestamp getCreated_at(){
        return created_at;
    }

    public void setCreated_at(Timestamp created_at){
        this.created_at = created_at;
    }



    public Timestamp getUpdated_at(){
        return updated_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }

    }


