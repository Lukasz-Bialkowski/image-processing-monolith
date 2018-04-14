package uni.master.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ImageDAO {

    @Id @GeneratedValue
    @Getter @Setter Long id;

    @Getter @Setter String name;

    @Lob @Column(nullable = false)
    @Getter @Setter byte[] image;
}
