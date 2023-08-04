package com.example.phonecallapp.shedLock;

import com.example.phonecallapp.repository.CallRepo;
//import net.javacrumbs.shedlock.core.SchedulerLock;
//import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.config.ScheduledTask;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Component
public class TaskSheduler {
    @Autowired
    CallRepo data;
    @Scheduled(cron = "0 0/1 * * * ?")
    @SchedulerLock(name = "fstJob", lockAtLeastFor = "PT5M", lockAtMostFor = "PT14M")
    public void ScheduledTask(){

        data.deleteAllByCallDateIsBefore(OffsetDateTime.now().minusMonths(1));
    }
}
