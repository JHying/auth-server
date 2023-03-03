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
@Table(name = "RolePage")
public class RolePage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RP_NO")
    private String rpNo;

    @Column(name = "ROLE_KEY", nullable = false)
    private Integer roleKey;

    @Column(name = "PAGE_KEY", nullable = false)
    private Integer pageKey;

}
