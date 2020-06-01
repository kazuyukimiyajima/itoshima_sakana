package models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Table(name = "CookFishes")
@Entity
public class CookFish {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //fish クラスの魚のID
    @ManyToOne
    @JoinColumn(name = "fish_id", nullable = false)
    private Fish fish;

    //料理名
    @Column(name = "title", length = 255, nullable = false)
    private String title;

    //料理の特徴
    @Lob
    @Column(name = "content", nullable = false)
    private String content;

    //登録
    @Column(name = "created_at", nullable = false)
    private Timestamp created_at;

    //更新
    @Column(name = "updated_at", nullable = false)
    private Timestamp updated_at;


    //ID
    public Integer getId(){
        return id;
    }
    public void setId(Integer id){
        this.id = id;
    }


    //fishクラスの魚ID
    public Fish getFish(){
        return fish;
    }
    public void setFish(Fish fish){
        this.fish = fish;
    }


    //料理
    public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        this.title = title;
    }


    //料理の特徴
    public String getContent(){
        return content;
    }
    public void setContent(String content){
        this.content = content;
    }


    //登録
    public Timestamp getCreated_at(){
        return created_at;
    }
    public void setCreated_at(Timestamp created_at){
        this.created_at = created_at;
    }


    //更新
    public Timestamp getUpdated_at(){
        return updated_at;
    }
    public void setUpdated_at(Timestamp updated_at){
        this.updated_at = updated_at;
    }

}
