package com.example.phonecallapp.repository;


import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.util.List;


@Repository
//@EnableJpaRepositories
public interface
    CallRepo extends JpaRepository<CallEntity, Integer> {
    List<CallEntity> findByCallerPhoneNumber(Long string);
    List<CallEntity> findByCalledPhoneNumber(Long string);

//    List<CallDTO> CallDTOByCallerPhoneNumberAndCallDateAfter(Long callerPhoneNumber,Timestamp timestamp);
    List<CallEntity> findByCallerPhoneNumberAndCallDateAfter(Long callerPhoneNumber, OffsetDateTime offsetDateTime);

    @Transactional
    void deleteAllByCallDateIsBefore(OffsetDateTime date);
}

