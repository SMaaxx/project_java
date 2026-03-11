package com.example.BSS.repository;

import com.example.BSS.entity.EventRegistration;
import com.example.BSS.entity.EventRegistrationId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

//import java.util.List;

@Repository
public interface EventRegistrationRepository extends JpaRepository<EventRegistration, EventRegistrationId> {

//    List<EventRegistration> findByEventId(Long eventId);
//    List<EventRegistration> findByUserId(Long userId);
    boolean existsByEventIdAndUserId(Long eventId, Long userId);

    @Modifying
    @Transactional
    @Query("DELETE FROM EventRegistration er WHERE er.event.id = :eventId AND er.user.id = :userId")
    void deleteByEventIdAndUserId(@Param("eventId") Long eventId, @Param("userId") Long userId);

    long countByEventId(Long eventId);
}