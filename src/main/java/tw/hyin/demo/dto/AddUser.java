package tw.hyin.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author H-yin on 2020.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddUser implements Serializable {
	
	private static final long serialVersionUID = 1L;

    private String userId;
    private String userPass;
    private String userName;
    private Integer age;

}
