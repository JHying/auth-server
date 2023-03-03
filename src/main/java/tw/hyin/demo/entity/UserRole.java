/**
 *
 */
package tw.hyin.demo.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author YingHan 2021-12-21
 *
 */
@Data
@Getter
@Setter
@Entity
@NoArgsConstructor
@DynamicInsert
@Table(name = "UserRole")
public class UserRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UR_NO")
    private String urNo;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "ROLE_KEY", nullable = false)
    private Integer roleKey;

}
