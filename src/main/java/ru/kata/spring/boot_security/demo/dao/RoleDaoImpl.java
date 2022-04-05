package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class RoleDaoImpl implements RoleDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(Role role) {
        Role fine = entityManager.merge(role);
        entityManager.persist(fine);
    }

    @Override
    public Role getRoleByName(Role role) {
        return entityManager.createQuery("select r from Role r", Role.class)
                .getResultStream()
                .filter(name -> name.getRole().equals(role.getRole()))
                .findAny()
                .orElse(null);
    }

    @Override
    public List<Role> getAllRoles() {
        List<Role> resultSet = entityManager.createQuery("SELECT r FROM Role r", Role.class).getResultList();
        return resultSet;
    }

}