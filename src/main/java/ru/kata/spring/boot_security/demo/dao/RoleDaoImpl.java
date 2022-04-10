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
    public List<Role> getAllRoles() {
        return entityManager.createQuery("select r from Role r", Role.class).getResultList();
    }

    @Override
    public void save(Role role) {

        Role fine = entityManager.merge(role);
        entityManager.persist(fine);

    }

    @Override
    public Role getRoleByName(Role role) {
        Role role1 = null;
        List<Role> roleList = entityManager.createQuery("from Role").getResultList();
        for (Role r : roleList) {
            if (r.getRole().equals(role.getRole())) {
                role1 = r;
            }
        }
        return role1;

    }
}
