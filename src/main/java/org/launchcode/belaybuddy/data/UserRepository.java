package org.launchcode.belaybuddy.data;

import org.launchcode.belaybuddy.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by kalindapiper on 8/2/17.
 */
@Repository("userRepository")
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
