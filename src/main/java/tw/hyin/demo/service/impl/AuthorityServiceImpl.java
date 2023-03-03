package tw.hyin.demo.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import tw.hyin.demo.config.RedisConfig;
import tw.hyin.demo.dto.LoginInfo;
import tw.hyin.demo.entity.PageInfo;
import tw.hyin.demo.dto.i.IRolePageList;
import tw.hyin.demo.repo.PageRepository;
import tw.hyin.demo.repo.RoleRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tw.hyin.demo.service.AuthorityService;

/**
 * @author YingHan 2021-11-02
 */
@Service
@Transactional
@RequiredArgsConstructor
public class AuthorityServiceImpl implements AuthorityService {

    private final RoleRepository roleRepo;
    private final PageRepository pageRepo;

    @Override
    public List<String> getMyAuthUrls(LoginInfo loginInfo) {
        return pageRepo.getMyAuthPages(loginInfo.getRoles())
                .stream().map(PageInfo::getPageUrl).collect(Collectors.toList());
    }

    @Override
    public void saveRoleResource(RedisTemplate<String, Object> redisTemplate) {
        // 清除原本的
        if (Boolean.TRUE.equals(redisTemplate.hasKey(RedisConfig.RedisConstants.ROLE_RESOURCE.getValue()))) {
            redisTemplate.delete(RedisConfig.RedisConstants.ROLE_RESOURCE.getValue());
        }
        // 取得所有頁面表
        List<PageInfo> pageList = pageRepo.findAll();
        // 取得對應表
        List<IRolePageList> rolePageList = roleRepo.getRolePageList();
        // 轉移成map (key=pageUrl, value=roleIds)
        Map<String, List<Integer>> pageRolesMap = new TreeMap<>();
        Optional.ofNullable(pageList).orElse(new ArrayList<PageInfo>()).forEach(page -> {

            List<IRolePageList> pageRoles = Optional.ofNullable(rolePageList).orElse(new ArrayList<>())
                    .stream().filter(rolePage -> rolePage.getPageUrl().equals(page.getPageUrl()))
                    .collect(Collectors.toList());

            List<Integer> roles = Optional.ofNullable(pageRoles).orElse(new ArrayList<>()).stream()
                    .map(IRolePageList::getRoleKey).collect(Collectors.toList());

            pageRolesMap.put(page.getPageUrl(), roles);
        });
        // 存到redis
        redisTemplate.opsForHash().putAll(RedisConfig.RedisConstants.ROLE_RESOURCE.getValue(), pageRolesMap);
    }

    @Override
    public Map<String, List<Integer>> getRoleResource() {
        // 取得所有頁面表
        List<PageInfo> pageList = pageRepo.findAll();
        // 取得對應表
        List<IRolePageList> rolePageList = roleRepo.getRolePageList();
        // 轉移成map (key=pageUrl, value=roleIds)
        Map<String, List<Integer>> pageRolesMap = new TreeMap<>();
        Optional.ofNullable(pageList).orElse(new ArrayList<PageInfo>()).forEach(page -> {

            List<IRolePageList> pageRoles = Optional.ofNullable(rolePageList).orElse(new ArrayList<>())
                    .stream().filter(rolePage -> rolePage.getPageUrl().equals(page.getPageUrl()))
                    .collect(Collectors.toList());

            List<Integer> roles = Optional.ofNullable(pageRoles).orElse(new ArrayList<>()).stream()
                    .map(IRolePageList::getRoleKey).collect(Collectors.toList());

            pageRolesMap.put(page.getPageUrl(), roles);
        });
        return pageRolesMap;
    }

}
