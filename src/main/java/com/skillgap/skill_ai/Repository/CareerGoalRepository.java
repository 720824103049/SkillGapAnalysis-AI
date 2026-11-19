
package com.skillgap.skill_ai.Repository;

import com.skillgap.skill_ai.entity.CareerGoal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CareerGoalRepository extends JpaRepository<CareerGoal, Long> {

}
