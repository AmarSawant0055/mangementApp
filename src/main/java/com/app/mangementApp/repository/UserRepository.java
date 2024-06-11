package com.app.mangementApp.repository;

import com.app.mangementApp.constants.UserAccountStatusTypes;
import com.app.mangementApp.modal.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    @Query("select yur from User yur where yur.emailAdd=?1")
    User getUserByEmail(String email);

    @Query("select yur from User yur where yur.accountStatus=com.app.mangementApp.constants.UserAccountStatusTypes.PENDING")
    List<User> getAllPendingUsers();

    @Query("select yur from User yur")
    List<User> findAllUsersByRole();

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query("update User yur set yur.accountStatus=com.app.mangementApp.constants.UserAccountStatusTypes.APPROVED where yur.emailAdd=?1")
    Integer approvePendingUser(String emailAdd);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query("update User yur set yur.accountStatus=com.app.mangementApp.constants.UserAccountStatusTypes.DECLINED where yur.emailAdd=?1")
    Integer declinePendingUser(String emailAdd);

    @Query("select yur from User yur where yur.accountStatus=?1")
    List<User> getAllUserByStatus(UserAccountStatusTypes accountStatus);

    User findByid(Long id);

    Optional<User> findByEmailAdd(String emailAdd);

}
