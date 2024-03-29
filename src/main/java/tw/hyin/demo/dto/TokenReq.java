package tw.hyin.demo.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author H-yin on 2020.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TokenReq implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
    @JsonProperty("authUser")
    private String authUser;

    @JsonProperty("authPass")
    private String authPass;

    @JsonProperty("userId")
    private String userId;

}
