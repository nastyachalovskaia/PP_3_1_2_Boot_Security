package web.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import web.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    EntityManager entityManager;

    PasswordEncoder passwordEncoder;

    @Autowired
    public UserDaoImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getAllUsers() {
        return entityManager.createQuery("select u from User u", User.class).getResultList();
    }

    public User getUserById(Long id) {
        return entityManager.find(User.class, id);
    }

    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        entityManager.persist(user);
    }

    public void updateUser(Long id, User updatedUser) {
        User userToBeUpdated = getUserById(id);
        userToBeUpdated.setName(updatedUser.getName());
        userToBeUpdated.setLastName(updatedUser.getLastName());
        userToBeUpdated.setAge(updatedUser.getAge());
        userToBeUpdated.setLogin(updatedUser.getLogin());
        userToBeUpdated.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        userToBeUpdated.setRoles(updatedUser.getRoles());
        entityManager.merge(userToBeUpdated);
    }

    public void deleteUser(Long id) {
        entityManager.createQuery("delete from User u where u.id=:id")
                .setParameter("id", id)
                .executeUpdate();
    }

    public User getUserByLogin(String login) {
        return entityManager.createQuery("select u from User u where u.login=:login", User.class)
                .setParameter("login", login)
                .getSingleResult();
    }

}
