package tw.hyin.demo.config;

import tw.hyin.demo.service.AuthorityService;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import tw.hyin.java.utils.Log;

import javax.annotation.PostConstruct;

/**
 * @author JHying(Rita) on 2023.
 * @description
 */
@Profile("!dev")
@Component
public class RolePagesInitConfig {

    private final AuthorityService authorityService;
    private final RedisTemplate<String, Object> redisTemplate;

    public RolePagesInitConfig(AuthorityService authorityService, RedisTemplate<String, Object> redisTemplate) {
        this.authorityService = authorityService;
        this.redisTemplate = redisTemplate;
    }

    @PostConstruct
    public void initRolePages() {
        this.authorityService.saveRoleResource(redisTemplate);
        Log.info("role resource initialize to Redis Complete.");
    }

}
