package org.launchcode.belaybuddy.models.data;

import javax.transaction.Transactional;;
import org.launchcode.belaybuddy.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by kalindapiper on 7/12/17.
 */

@Repository
@Transactional
public interface UserDao extends CrudRepository<User, Integer> {
}
