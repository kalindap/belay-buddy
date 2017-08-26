package org.launchcode.belaybuddy.data;

import org.launchcode.belaybuddy.models.Meetup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * Created by kalindapiper on 8/16/17.
 */
@Repository
@Transactional
public interface MeetupRepository extends JpaRepository<Meetup, Long> {
}
