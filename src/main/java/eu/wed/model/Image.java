package eu.wed.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import java.util.Base64;

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

    @Column(name = "order_number")
    public Long order_number;

    protected Image() {
        //for Hibernate
    }

    public Image(String name, byte[] photo) {
        this.name = name;
        this.photo = photo;
    }

    public String generateBase64Image() {
        return Base64.getMimeEncoder().encodeToString(this.photo);
    }
}
