package com.bookstore.schedulers;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CronJobs {

    @Scheduled(cron = "")
    public void sendPromotionNotificationForPremiumUsers(){

    }
}
