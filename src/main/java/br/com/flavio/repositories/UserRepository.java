package br.com.flavio.repositories;

import br.com.flavio.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

     @Query("SELECT u FROM USER WHERE u.userName =: userName")
     User findByUserName(@Param("userName") String userName);
}
