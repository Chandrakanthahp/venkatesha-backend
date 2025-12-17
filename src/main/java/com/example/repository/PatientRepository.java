package com.example.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.entity.Patients;

import jakarta.transaction.Transactional;

public interface PatientRepository extends JpaRepository<Patients, Long> {
	List<Patients> findAllByOrderByOrderIndexAsc();
	
	@Modifying
    @Transactional
    @Query("UPDATE Patients p SET p.orderIndex = NULL")
    void resetAllOrderIndex();
	
	@Query("""
	        SELECT p FROM Patients p
	        WHERE DATE(p.aptAt) = :date
	        ORDER BY p.id ASC
	    """)
	    List<Patients> findByCreatedDate(@Param("date") LocalDate date);

    @Query("""
        SELECT p FROM Patients p
        WHERE DATE(p.aptAt) = :date
        ORDER BY p.id ASC
    """)
	void clearOrderIndexForOtherDates(LocalDate date);
	
	List<Patients> findByAptAt(LocalDate date);

	@Query("""
			SELECT p FROM Patients p
			WHERE DATE(p.aptAt) = :date
			ORDER BY p.id ASC
			""")
			List<Patients> findByDateOrderByIdAsc(@Param("date") String date);
	@Modifying
    @Transactional
    @Query("""
        UPDATE Patients p
        SET p.orderIndex = :orderIndex
        WHERE p.id = :id
    """)
    void updateOrderIndex(@Param("id") Long id,
                          @Param("orderIndex") Integer orderIndex);

	@Modifying
	@Query("UPDATE Patients p SET p.aptAt = :aptAt WHERE p.id = :id")
	void updateAppointmentDate(@Param("id") Long id,
	                           @Param("aptAt") LocalDate aptAt);



    // No additional code needed for basic CRUD
}
