package eu.wed.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "IMAGE")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, unique = true)
    private Long id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Lob
    @Column(name = "PHOTO", columnDefinition = "MEDIUMBLOB")
    private byte[] photo;

    @Column(name = "ORDER_NUMBER")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long order;

    protected Image() {
        //for Hibernate
    }

    public Image(String name, byte[] photo) {
        this.name = name;
        this.photo = photo;
    }
}
