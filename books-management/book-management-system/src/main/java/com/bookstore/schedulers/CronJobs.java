package com.bookstore.schedulers;

import com.bookstore.constants.EmailEvents;
import com.bookstore.dao.IBookManager;
import com.bookstore.dao.IBuyerMembershipManager;
import com.bookstore.model.Book;
import com.bookstore.model.BookRack;
import com.bookstore.model.BuyerMembershipHistory;
import com.bookstore.model.User;
import com.bookstore.service.MailService;
import com.bookstore.vo.EmailTableRow;
import com.bookstore.vo.EmailTableVo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CronJobs {

    private final IBuyerMembershipManager buyerMembershipManager;
    private final MailService mailService;
    private final IBookManager bookManager;
    private static final Logger logger = LogManager.getLogger(CronJobs.class);

    @Autowired
    public CronJobs(IBuyerMembershipManager buyerMembershipManager, MailService mailService, IBookManager bookManager) {
        this.buyerMembershipManager = buyerMembershipManager;
        this.mailService = mailService;
        this.bookManager = bookManager;
    }

    @Scheduled(cron = "0 12 * * *")
    public void sendPromotionNotificationForPremiumUsers() {
        List<BuyerMembershipHistory> allActiveBuyerMemberships = buyerMembershipManager.getAllActiveBuyerMemberships();
        if (CollectionUtils.isEmpty(allActiveBuyerMemberships)) {
            return;
        }
        EmailTableVo emailTableVo = new EmailTableVo("BookGenre", "BookNames");
        for (BuyerMembershipHistory buyerMembershipHistory : allActiveBuyerMemberships) {
            User buyer = buyerMembershipHistory.getUser();
            try {
                Set<BookRack> userFavouriteRacks = buyer.getGenresInterested();
                if (CollectionUtils.isEmpty(userFavouriteRacks)) {
                    continue;
                }
                emailTableVo.getDataItr().clear();
                for (BookRack bookRack : userFavouriteRacks) {
                    List<Book> userInterestedGenresBooks = bookManager.getAllBooksByCategory(bookRack.getRackName());
                    if (CollectionUtils.isEmpty(userInterestedGenresBooks)) {
                        continue;
                    }
                    Set<String> availableBooksOfGenre = userInterestedGenresBooks.stream().filter(b -> b.getAvailableStock() != 0L).map(b -> b.getBookName() + " " + b.getEdition() + " Edition").collect(Collectors.toSet());
                    emailTableVo.addTableRow(new EmailTableRow(bookRack.getRackName(), String.join(",", availableBooksOfGenre)));
                }
                if (!CollectionUtils.isEmpty(emailTableVo.getDataItr())) {
                    mailService.sendEmailWithTable(EmailEvents.BULK_EMAIL_PROMOTIONAL_NOTIFICATION, buyer, emailTableVo);
                }
            } catch (Exception e) {
                logger.error("Error while sending promotional notification for user " + buyer.getFirstName(), e);
            }
        }
    }
}
