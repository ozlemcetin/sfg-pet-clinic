package guru.springframework.sfgpetclinic.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/*
MappedSuperclass establish this as a base class to JPA - We don't this specific class mapped to the database
 */
@MappedSuperclass
public class BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
    Getters and Setters
    */
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
