package io.github.mkhl28mi.memo_service.domain.user.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.mkhl28mi.memo_service.domain.user.entity.User;

public interface UserRepository extends JpaRepository<User, UUID> {
	
	public List<User> findByUsernameContainingOrFullNameContaining(String username, String fullName);

}
