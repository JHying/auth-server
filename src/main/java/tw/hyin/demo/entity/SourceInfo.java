package tw.hyin.demo.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "SourceInfo")
public class SourceInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "SOURCE_ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer sourceId;

    @Column(name = "SOURCE_ACCOUNT")
    private String sourceAccount;

    @Column(name = "SOURCE_DESC")
    private String sourceDesc;

}
