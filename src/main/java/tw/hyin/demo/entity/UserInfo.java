package tw.hyin.demo.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * @author H-yin on 2021.
 */
@Data
@Getter
@Setter
@Entity
@NoArgsConstructor
@DynamicInsert
@Table(name = "UserInfo")
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "SOURCE_ID")
    private Integer sourceId;

    @Column(name = "USER_PASS")
    private String userPass;

    @Column(name = "USER_NAME")
    private String userName;

    @OneToMany(fetch = FetchType.LAZY)
    @Fetch(FetchMode.SUBSELECT)
    @JoinColumn(name = "USER_ID")
    private Set<UserRole> userRoles;

}
