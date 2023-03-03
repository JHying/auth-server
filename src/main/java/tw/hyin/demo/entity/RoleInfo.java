package tw.hyin.demo.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author H-yin on 2021.
 */
@Data
@Getter
@Setter
@Entity
@NoArgsConstructor
@DynamicInsert//解決 not null 欄位沒給值時，不會自動塞 default 的問題
@Table(name = "RoleInfo")
public class RoleInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ROLE_KEY", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roleKey;

    @Column(name = "SOURCE_ID")
    private Integer sourceId;

    @Column(name = "ROLE_NAME")
    private String roleName;

    @Column(name = "ROLE_ACTIVE")
    private String roleActive;

}
