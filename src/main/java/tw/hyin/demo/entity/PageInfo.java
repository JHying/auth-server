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
@DynamicInsert
@Table(name = "PageInfo")
public class PageInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "PAGE_KEY", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pageKey;

    @Column(name = "SOURCE_ID")
    private Integer sourceId;

    @Column(name = "PAGE_NAME")
    private String pageName;

    @Column(name = "PAGE_ICON")
    private String pageIcon;

    @Column(name = "PAGE_URL")
    private String pageUrl;

    @Column(name = "PAGE_PARENT")
    private Integer pageParent;

    @Column(name = "PAGE_ACTIVE")
    private String pageActive;

    @Column(name = "PAGE_ORDER")
    private String pageOrder;

}
