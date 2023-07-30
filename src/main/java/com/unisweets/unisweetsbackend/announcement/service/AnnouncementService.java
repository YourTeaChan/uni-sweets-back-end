package com.unisweets.unisweetsbackend.announcement.service;

import com.unisweets.unisweetsbackend.announcement.AnnouncementState;
import com.unisweets.unisweetsbackend.announcement.dto.AnnouncementDto;
import com.unisweets.unisweetsbackend.announcement.filter.AnnouncementClientFilter;
import com.unisweets.unisweetsbackend.announcement.filter.AnnouncementFilterDto;
import com.unisweets.unisweetsbackend.announcement.filter.TaskFilter;
import com.unisweets.unisweetsbackend.announcement.mapper.AnnouncementFilterMapper;
import com.unisweets.unisweetsbackend.announcement.mapper.AnnouncementMapper;
import com.unisweets.unisweetsbackend.announcement.model.Announcement;
import com.unisweets.unisweetsbackend.announcement.model.Offer;
import com.unisweets.unisweetsbackend.announcement.repository.AnnouncementDao;
import com.unisweets.unisweetsbackend.announcement.repository.AnnouncementRepository;
import com.unisweets.unisweetsbackend.announcement.repository.OfferRepository;
import com.unisweets.unisweetsbackend.comment.CommentDto;
import com.unisweets.unisweetsbackend.comment.model.Comment;
import com.unisweets.unisweetsbackend.comment.service.CommentService;
import com.unisweets.unisweetsbackend.notification.service.NotificationService;
import com.unisweets.unisweetsbackend.user.UserRole;
import com.unisweets.unisweetsbackend.user.model.User;
import com.unisweets.unisweetsbackend.user.model.UserClient;
import com.unisweets.unisweetsbackend.user.model.UserPastry;
import com.unisweets.unisweetsbackend.user.service.UserClientService;
import com.unisweets.unisweetsbackend.user.service.UserPastryService;
import com.unisweets.unisweetsbackend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnnouncementService {
    private final AnnouncementRepository announcementRepository;
    private final AnnouncementMapper announcementMapper;
    private final AnnouncementDao announcementDao;
    private final AnnouncementFilterMapper announcementFilterMapper;
    private final UserPastryService userPastryService;
    private final UserClientService userClientService;
    private final OfferRepository offerRepository;
    private final OfferService offerService;
    private final NotificationService notificationService;
    private final UserService userService;
    private final CommentService commentService;

    public List<Announcement> getAllAnnouncements() {
        return announcementRepository.findAll();
    }

    public List<Announcement> getAllAnnouncements(AnnouncementFilterDto announcementFilterDto, String username) {
        UserPastry userPastry = userPastryService.getUserPastryByUsername(username);
        return announcementDao
                .getAllAnnouncementByFilter(
                        announcementFilterMapper.mapToEntity(announcementFilterDto),
                        userPastry
                )
                .stream()
                .peek(announcement -> {
                    if (announcement.getCreator().getFavorites().contains(userPastry)) {
                        announcement.setNotifyFavorites(true);
                    }
                })
                .toList();
    }

    public List<Announcement> getAllTasksByFilter(TaskFilter taskFilter, String username) {
        UserPastry userPastry = userPastryService.getUserPastryByUsername(username);
        return announcementDao
                .getAllTasksByFilter(taskFilter, userPastry)
                .stream()
                .peek(announcement -> {
                    if (announcement.getCreator().getFavorites().contains(userPastry)) {
                        announcement.setNotifyFavorites(true);
                    }
                })
                .sorted(Comparator.comparing(Announcement::getId).reversed())
                .toList();
    }

    public Announcement createAnnouncement(AnnouncementDto announcementDto) {
        Announcement announcement = announcementMapper.mapToEntity(announcementDto);
        if (announcement.getNotifyFavorites()) {
            UserClient creator = announcement.getCreator();
            creator.getFavorites().forEach(userPastry -> {
                String text = creator.getFirstName() + " " + creator.getLastName() + " створив нове оголошення";
                notificationService.createNotification(userPastry, creator, text);
            });
        }
        return announcementRepository.save(announcement);
    }

    public Announcement getAnnouncementById(Long id) {
        return announcementRepository.findById(id).orElseThrow();
    }

    public Announcement getAnnouncementById(String username, Long id) {
        User user = userService.getUserByUsername(username);
        if (user.getUserRole().equals(UserRole.ROLE_PASTRY)) {
            UserPastry userPastry = userPastryService.getUserPastryByUsername(username);
            return announcementRepository
                    .findById(id)
                    .map(announcement -> {
                        if (announcement.getCreator().getFavorites().contains(userPastry)) {
                            announcement.setNotifyFavorites(true);
                        }
                        announcement.setOffers(
                                announcement.getOffers()
                                        .stream()
                                        .filter(offer -> offer.getUserPastry().equals(userPastry))
                                        .sorted(Comparator.comparing(Offer::getId).reversed())
                                        .toList()
                        );
                        return announcement;
                    })
                    .orElseThrow();
        } else {
            return announcementRepository
                    .findById(id)
                    .map(announcement -> {
                        announcement.setOffers(
                                announcement.getOffers()
                                        .stream()
                                        .sorted(Comparator.comparing(Offer::getId).reversed())
                                        .toList()
                        );
                        return announcement;
                    })
                    .orElseThrow();
        }
    }

    public Announcement createOffer(Long announcementId, String pastryUsername, Integer price) {
        Announcement announcement = getAnnouncementById(announcementId);
        UserPastry userPastry = userPastryService.getUserPastryByUsername(pastryUsername);
        announcement.addOffer(offerRepository.save(new Offer(userPastry, announcement, price)));
        String text = userPastry.getFirstName() + " " + userPastry.getLastName() + " відгукнувся(лась) на ваше оголошення";
        notificationService.createNotification(announcement.getCreator(), userPastry, text);
        announcement = announcementRepository.save(announcement);
        announcement.setOffers(
                announcement.getOffers()
                        .stream()
                        .filter(offer -> offer.getUserPastry().equals(userPastry))
                        .sorted(Comparator.comparing(Offer::getId).reversed())
                        .toList()
        );
        return announcement;
    }

    public Announcement chooseOffer(Long announcementId, Long offerId) {
        Announcement announcement = getAnnouncementById(announcementId);
        Offer offer = offerService.getOfferById(offerId);
        UserPastry userPastry = offer.getUserPastry();
        announcement.setPastry(userPastry);
        announcement.setState(AnnouncementState.TAKEN);
        announcement.setFinalOffer(offer);
        UserClient creator = announcement.getCreator();
        String text = creator.getFirstName() + " " + creator.getLastName() + " обрав(ла) вас для виконання замовлення";
        notificationService.createNotification(userPastry, creator, text);
        return announcementRepository.save(announcement);
    }

    public Announcement payForAnnouncement(Long announcementId) {
        Announcement announcement = getAnnouncementById(announcementId);
        announcement.setPaid(true);
        announcement.setState(AnnouncementState.IN_PROGRESS);

        UserClient creator = announcement.getCreator();
        String text = creator.getFirstName() + " " + creator.getLastName() + " оплатив(ла) виконання замовлення";
        notificationService.createNotification(announcement.getPastry(), creator, text);
        return announcementRepository.save(announcement);
    }

    public Announcement finishAnnouncement(Long announcementId) {
        Announcement announcement = getAnnouncementById(announcementId);
        announcement.setState(AnnouncementState.DONE);

        UserPastry userPastry = announcement.getPastry();
        String text = userPastry.getFirstName() + " " + userPastry.getLastName() + " завершив(ла) виконання вашого замовлення";
        notificationService.createNotification(announcement.getCreator(), userPastry, text);
        return announcementRepository.save(announcement);
    }

    public Announcement closeAnnouncement(CommentDto commentDto, Long announcementId) {
        Announcement announcement = getAnnouncementById(announcementId);
        UserClient creator = announcement.getCreator();
        if (commentDto.getRating() != null && commentDto.getRating() > 0) {
            Comment comment = commentService.createComment(commentDto);
            announcement.getPastry().addComment(comment);
            String text = creator.getFirstName() + " " + creator.getLastName() + " залишив(ла) відгук про виконане замовлення";
            notificationService.createNotification(announcement.getPastry(), creator, text);
        }
        announcement.setState(AnnouncementState.CLOSED);
        String text = creator.getFirstName() + " " + creator.getLastName() + " закрив(ла) замовлення";
        notificationService.createNotification(announcement.getPastry(), creator, text);
        return announcementRepository.save(announcement);
    }

    public List<Announcement> getAllClientAnnouncements(String username, AnnouncementClientFilter announcementClientFilter) {
        UserClient userClient = userClientService.getUserClientByUsername(username);
        return announcementDao.getAllClientAnnouncements(announcementClientFilter, userClient);

    }

    public Announcement deleteOffer(Long announcementId, Long offerId) {
        Announcement announcement = getAnnouncementById(announcementId);
        Offer offer = offerService.getOfferById(offerId);
        announcement.removeOffer(offer);
        if (announcement.getOffers().isEmpty()) {
            announcement.setState(AnnouncementState.CREATED);
        }
        UserPastry userPastry = offer.getUserPastry();
        UserClient creator = announcement.getCreator();
        String text = userPastry.getFirstName() + " " + userPastry.getLastName() + " скасував(ла) свою пропозицію";
        notificationService.createNotification(creator, userPastry, text);
        return announcementRepository.save(announcement);
    }

    public Integer getUserPastryClosedAnnouncementCount(String username) {
        return announcementDao.getUserPastryClosedAnnouncementCount(userPastryService.getUserPastryByUsername(username));
    }

    public Integer getUserClientClosedAnnouncementCount(String username) {
        return announcementDao.getUserClientClosedAnnouncementCount(userClientService.getUserClientByUsername(username));
    }
}
