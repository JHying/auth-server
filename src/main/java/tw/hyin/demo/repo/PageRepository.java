package tw.hyin.demo.repo;

import tw.hyin.demo.entity.PageInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * @author H-yin on 2021.
 */
@RepositoryRestResource(exported = false)
public interface PageRepository extends JpaRepository<PageInfo, String> {

    @Query(value =
            "select "
                    + "* "
                    + "from "
                    + "PageInfo p "
                    + "WHERE "
                    + "p.PAGE_PARENT IS null", nativeQuery = true)
    public List<PageInfo> getParentPages();

    @Query(value =
            "select "
                    + "* "
                    + "from "
                    + "PageInfo p "
                    + "WHERE "
                    + "p.PAGE_PARENT IS NOT null;", nativeQuery = true)
    public List<PageInfo> getChildPages();

    @Query(value =
            "select "
                    + "p "
                    + "from "
                    + "PageInfo p "
                    + "INNER JOIN "
                    + "RolePage rp "
                    + "ON "
                    + "p.pageKey = rp.pageKey "
                    + "WHERE "
                    + "p.pageParent IS null and p.pageActive = 'Y' "
                    + "and "
                    + "rp.roleKey IN :roles "
                    + "ORDER BY p.pageOrder ")
    public List<PageInfo> getMyParentPages(List<Integer> roles);

    @Query(value =
            "select "
                    + "p "
                    + "from "
                    + "PageInfo p "
                    + "INNER JOIN "
                    + "RolePage rp "
                    + "ON "
                    + "p.pageKey = rp.pageKey "
                    + "WHERE "
                    + "p.pageParent IS NOT null and p.pageActive = 'Y' "
                    + "and "
                    + "rp.roleKey IN :roles ")
    public List<PageInfo> getMyChildPages(List<Integer> roles);

    @Query(value =
            "select "
                    + " p "
                    + "from "
                    + " PageInfo p "
                    + "INNER JOIN "
                    + " RolePage rp "
                    + "ON "
                    + " p.pageKey = rp.pageKey "
                    + "WHERE "
                    + " rp.roleKey IN :roles and p.pageActive = 'Y' ")
    public List<PageInfo> getMyAuthPages(List<Integer> roles);

}
