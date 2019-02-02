package com.example.nekretnine.Repository;


import com.example.nekretnine.Model.LoginUserForm;
import com.example.nekretnine.Model.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class UserRepositoryCustomImpl implements  UserRepositoryCustom{

    @PersistenceContext
    EntityManager entityManager;


    public List<User> login(String username, String password) {
        Query query = entityManager.createNativeQuery("SELECT * FROM user" +
                "WHERE username="+username+"and password="+password, LoginUserForm.class);
        return query.getResultList();
    }
}
