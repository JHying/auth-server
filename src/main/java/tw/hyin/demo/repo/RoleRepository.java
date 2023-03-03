package tw.hyin.demo.repo;

import java.util.List;

import tw.hyin.demo.entity.PageInfo;
import tw.hyin.demo.entity.RoleInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import tw.hyin.demo.dto.i.IRolePageList;

/**
 * @author H-yin on 2021.
 */
@RepositoryRestResource(exported = false)
public interface RoleRepository extends JpaRepository<PageInfo, String> {

	/**
	 * 
	 * @author YingHan
	 * @since 2021-12-22
	 * 
	 * @Description 取得角色權限對應表 (自定義輸出結果：需使用 interface 建構 get method for all attributes)
	 */
	//You need to define an interface that defines a getter method for each attribute your projection shall contain.
	//At runtime, Spring Data JPA then generates a class that implements that interface.
	@Query(value = "select  " +
			"  rps.ROLE_KEY as roleId,  " +
			"  pi2.PAGE_URL as pageUrl  " +
			"from  " +
			" (select  " +
			"   ri.ROLE_KEY,  " +
			"   rp.PAGE_KEY  " +
			" from  " +
			"   RoleInfo ri  " +
			" inner join RolePage rp ON ri.ROLE_KEY = rp.ROLE_KEY " +
			" WHERE  " +
			"   ri.ROLE_ACTIVE = 'Y') AS rps  " +
			" left join PageInfo pi2 on rps.PAGE_KEY = pi2.PAGE_KEY " +
			" where pi2.PAGE_ACTIVE = 'Y';", nativeQuery = true)
	public List<IRolePageList> getRolePageList();

	@Query(value =
			"select "
					+ " r "
					+ "from "
					+ " RoleInfo r "
					+ "INNER JOIN "
					+ " UserRole ur "
					+ "ON "
					+ " r.roleKey = ur.roleKey "
					+ "WHERE "
					+ " ur.userId = :userId and r.roleActive = 'Y' ")
	public List<RoleInfo> getMyRoles(String userId);
	
}
