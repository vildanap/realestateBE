package com.example.nekretnine.Repository;

import com.example.nekretnine.Model.UserAdvert;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface UserAdvertRepository extends JpaRepository<UserAdvert, Long> {
    List<UserAdvert> findAllByUserId(Long userId);
    Long countUserAdvertByUserId(long userId);
    boolean existsUserAdvertByAdvertIdAndUserId(long advertId, long userId);
    UserAdvert findByAdvertIdAndUserId(long advertId, long userId);
    long deleteByAdvertId(long advertId);
    long deleteByUserId(long userId);
}
