package web.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import web.model.Role;
import web.model.User;

import java.util.List;

public interface RoleDao {

    public List<Role> getAllRoles();

    public Role getRoleByName(String name);

    public void saveRole(Role role);

}
