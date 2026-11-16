
package com.skillgap.skill_ai.Repository;

import com.skillgap.skill_ai.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
