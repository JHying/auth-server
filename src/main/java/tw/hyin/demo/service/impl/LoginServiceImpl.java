package tw.hyin.demo.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import tw.hyin.demo.config.KeyConfig;
import tw.hyin.demo.dto.SideNavObj;
import tw.hyin.demo.entity.PageInfo;
import tw.hyin.demo.entity.RoleInfo;
import tw.hyin.demo.entity.UserInfo;
import tw.hyin.demo.dto.LoginInfo;
import tw.hyin.demo.dto.LoginReq;
import tw.hyin.demo.repo.PageRepository;
import tw.hyin.demo.repo.RoleRepository;
import tw.hyin.demo.repo.UserRepository;
import tw.hyin.demo.service.LoginService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tw.hyin.java.utils.security.AESUtil;
import tw.hyin.java.utils.security.RSAUtil;

/**
 * @author YingHan 2021-11-02
 */
@Service
@Transactional
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final UserRepository userRepo;
    private final PageRepository pageRepo;
    private final RoleRepository roleRepo;

    @Override
    public boolean validate(LoginReq loginReq) throws Exception {
        Optional<UserInfo> userInfo = userRepo.findById(loginReq.getUserId());
        if (userInfo.isEmpty())
            return false;
        UserInfo user = userInfo.get();
        String passInDB = AESUtil.decrypt(user.getUserPass().getBytes(), KeyConfig.secretKey);
        //前端使用公鑰加密訊息傳送，後端使用私鑰解密
        String decryptPW = RSAUtil.decrypt(loginReq.getUserPW().getBytes(), KeyConfig.privateKey);
        //比對密碼及sourceId(專案或場域)
        return passInDB.equals(decryptPW) && loginReq.getSourceId().equals(user.getSourceId());
    }

    @Override
    public LoginInfo getLoginInfo(LoginReq loginReq) {
        Optional<UserInfo> userInfo = userRepo.findById(loginReq.getUserId());
        if (userInfo.isEmpty())
            return null;
        UserInfo user = userInfo.get();
        List<Integer> roles = roleRepo.getMyRoles(user.getUserId())
                .stream().map(RoleInfo::getRoleKey).collect(Collectors.toList());
        return LoginInfo.builder().userId(user.getUserId())
                .userName(user.getUserName())
                .roles(roles)
                .sideNavObjs(this.getMySideNav(roles))
                .build();
    }

    @Override
    public List<SideNavObj> getAllSideNav() {
        List<PageInfo> parentPages = this.pageRepo.getParentPages();
        List<PageInfo> childPages = this.pageRepo.getChildPages();
        List<SideNavObj> sideNavObjs = new ArrayList<>();
        for (PageInfo parentPage : parentPages) {
            SideNavObj sideNavObj = new SideNavObj();
            sideNavObj.setPageIcon(parentPage.getPageIcon());
            sideNavObj.setPageName(parentPage.getPageName());
            sideNavObj.setPageUrl(parentPage.getPageUrl());
            sideNavObj.setChildPages(childPages.stream().filter(
                    pageInfo -> pageInfo.getPageParent().equals(parentPage.getPageKey())).collect(Collectors.toList()));
            sideNavObjs.add(sideNavObj);
        }
        return sideNavObjs;
    }

    @Override
    public List<SideNavObj> getMySideNav(List<Integer> role) {
        List<PageInfo> parentPages = this.pageRepo.getMyParentPages(role);
        List<PageInfo> childPages = this.pageRepo.getMyChildPages(role);
        List<SideNavObj> sideNavObjs = new ArrayList<>();
        for (PageInfo parentPage : parentPages) {
            SideNavObj sideNavObj = new SideNavObj();
            sideNavObj.setPageIcon(parentPage.getPageIcon());
            sideNavObj.setPageName(parentPage.getPageName());
            sideNavObj.setPageUrl(parentPage.getPageUrl());
            sideNavObj.setChildPages(childPages.stream().filter(
                    pageInfo -> pageInfo.getPageParent().equals(parentPage.getPageKey())).collect(Collectors.toList()));
            sideNavObjs.add(sideNavObj);
        }
        return sideNavObjs;
    }

}
