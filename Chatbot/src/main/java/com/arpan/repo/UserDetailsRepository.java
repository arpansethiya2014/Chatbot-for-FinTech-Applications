package com.arpan.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.arpan.entity.UserDetails;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetails, Long> {

	boolean existsByMobile(String mobile);

	boolean existsByEmail(String email);

	Optional<UserDetails> findByMobile(String mobile);

	Optional<UserDetails> findByNameAndPassword(String username, String password);

	Optional<UserDetails> findByLoginKey(String loginKey);

}
