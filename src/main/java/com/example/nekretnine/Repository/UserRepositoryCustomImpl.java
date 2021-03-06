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
        Query query = entityManager.createNativeQuery("select * from user_ where username='" + username + "' and password='" + password
                + "'",User.class);
        System.out.println(query.getResultList().get(0).toString());
        return query.getResultList();
    }

    public List<User> findByUsername(String username) {
        Query query = entityManager.createNativeQuery("select * from user_ where username='" + username + "'",User.class);
        System.out.println(query.getResultList().get(0).toString());
        return query.getResultList();
    }

    public List<User> findUserById (Long id){
        Query query = entityManager.createNativeQuery("select * from user_ where id="+ id,User.class);
        System.out.println(query.getResultList().get(0).toString());
        return query.getResultList();
}

}
