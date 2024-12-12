package com.coupons.repositories;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.coupons.models.User;
import com.coupons.models.UserToken;

public interface TokenRepository extends JpaRepository<UserToken,Long>{

// @Query("SELECT t FROM UserToken t WHERE t.user.id = :id AND t.expired = false AND t.revoked = false")

@Query("SELECT t FROM UserToken t WHERE t.user.id = :id AND (t.expired = false AND t.revoked = false)")
List<UserToken> findAllValidTokenByUser(Long id);

    String findEmailByToken(String token);

}
