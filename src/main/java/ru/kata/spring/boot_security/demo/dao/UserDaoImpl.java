package ru.kata.spring.boot_security.demo.dao;

import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
@Transactional
public class UserDaoImpl implements UserDao{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> allUsers() {
        List<User> resultList = entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
        return resultList;
    }

    @Override
    public User getById(long id) {

        try {
            Query query = entityManager.createQuery("select u from User u join fetch u.roles where u.id = :id");
            query.setParameter("id", id);
            return (User) query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public User getUserByEmail(String email) {

        try {
            Query query = entityManager.createQuery("select u from User u join fetch u.roles where u.email = :email");
            query.setParameter("email", email);
            return (User) query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public void save(User user) {
        entityManager.persist(user);
    }

    @Override
    public void updateUser(User newUser) {
        entityManager.merge(newUser);
    }

    @Override
    public void deleteUser(long id) {
        entityManager.remove(getById(id));
    }
}