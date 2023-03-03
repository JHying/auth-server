package tw.hyin.demo.controller;

import lombok.SneakyThrows;
import tw.hyin.demo.dto.LoginInfo;
import tw.hyin.demo.dto.LoginReq;
import tw.hyin.demo.service.AuthorityService;
import tw.hyin.demo.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tw.hyin.java.utils.Log;
import tw.hyin.java.utils.http.BaseController;
import tw.hyin.java.utils.http.ResponseObj;

import javax.validation.Valid;
import java.util.concurrent.TimeUnit;

@RestController
@ResponseBody
public class LoginController extends BaseController {

    private final LoginService loginService;
    private final AuthorityService authorityService;
    private final RedisTemplate<String, Object> redisTemplate;

    @Value("${spring.redis.data.expireTime}")
    private String expireTime;

    @Autowired
    public LoginController(LoginService loginService,
                           AuthorityService authorityService, RedisTemplate<String, Object> redisTemplate) {
        this.loginService = loginService;
        this.authorityService = authorityService;
        this.redisTemplate = redisTemplate;
    }

    @GetMapping(value = "/test", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseObj<ResponseObj.RspMsg>> test() {
        return super.sendSuccessRsp(ResponseObj.RspMsg.SUCCESS);
    }

    @SneakyThrows
    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseObj<LoginInfo>> userLogin(@Valid @RequestBody LoginReq loginReq) {
        if (loginService.validate(loginReq)) {
            LoginInfo loginInfo = loginService.getLoginInfo(loginReq);
            loginInfo.setUrls(authorityService.getMyAuthUrls(loginInfo));
            // 將 login 資料寫入 redis (存活 1 小時)
            this.redisTemplate.opsForValue().set(loginReq.getUserId(), loginInfo,
                    Integer.parseInt(expireTime), TimeUnit.MINUTES);
            Log.info(loginInfo.getUserId() + " login.");
            return super.sendSuccessRsp(loginInfo);
        } else {
            return sendDenyRsp();
        }
    }

    @GetMapping(value = "/logout/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseObj<ResponseObj.RspMsg>> userLogout(@PathVariable("userId") String userId) {
        Log.info(userId + " logout.");
        this.redisTemplate.delete(userId);
        return super.sendSuccessRsp(ResponseObj.RspMsg.SUCCESS);
    }

//    @ApiOperation(value = "側導覽列")
//    @PostMapping(value = "/sidenav", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<ResponseObj<List<SideNavObj>>> mySideNav(@RequestBody LoginInfo loginInfo) {
//        return super.sendSuccessRsp(loginService.getMySideNav(loginInfo.getRoles()));
//    }

}
