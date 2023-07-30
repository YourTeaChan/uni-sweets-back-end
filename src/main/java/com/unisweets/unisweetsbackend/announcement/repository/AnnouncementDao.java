package com.unisweets.unisweetsbackend.announcement.repository;

import com.unisweets.unisweetsbackend.announcement.AnnouncementState;
import com.unisweets.unisweetsbackend.announcement.filter.AnnouncementClientFilter;
import com.unisweets.unisweetsbackend.announcement.filter.AnnouncementFilter;
import com.unisweets.unisweetsbackend.announcement.filter.TaskFilter;
import com.unisweets.unisweetsbackend.announcement.model.Announcement;
import com.unisweets.unisweetsbackend.announcement.model.Offer;
import com.unisweets.unisweetsbackend.user.model.UserClient;
import com.unisweets.unisweetsbackend.user.model.UserPastry;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class AnnouncementDao {
    private final EntityManager entityManager;

    public List<Announcement> getAllAnnouncementByFilter(AnnouncementFilter announcementFilter, UserPastry userPastry) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Announcement> criteriaQuery = criteriaBuilder.createQuery(Announcement.class);
        Root<Announcement> root = criteriaQuery.from(Announcement.class);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(
                criteriaBuilder.and(
                        criteriaBuilder.or(
                                criteriaBuilder.equal(root.get("state"), AnnouncementState.CREATED),
                                criteriaBuilder.equal(root.get("state"), AnnouncementState.TAKEN)
                        )
                )
        );

        if (announcementFilter.getDessertTypes().size() > 0) {
            predicates.add(root.get("dessertType").in(announcementFilter.getDessertTypes()));
        }

        if (announcementFilter.getLocations().size() > 0) {
            predicates.add(root.get("location").in(announcementFilter.getLocations()));
        }

        if (announcementFilter.getDate().equals("Сьогодні")) {
            Instant today = Instant.now().truncatedTo(ChronoUnit.DAYS);
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("creationDate"), today));
        }

        if (announcementFilter.getDate().equals("Останні 7 днів")) {
            Instant weekBefore = Instant.now().minus(7, ChronoUnit.DAYS);
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("creationDate"), weekBefore));
        }

        if (announcementFilter.getIsQuick()) {
            predicates.add(criteriaBuilder.equal(root.get("isQuick"), true));
        }

        if (announcementFilter.getFromFavorites()) {
            predicates.add(criteriaBuilder.isMember(userPastry, root.get("creator").get("favorites")));
        }
        criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));
        criteriaQuery.orderBy(
                announcementFilter.getSortingOrder().equals("Спочатку нові") ?
                        criteriaBuilder.desc(root.get("creationDate")) :
                        criteriaBuilder.asc(root.get("creationDate"))
        );
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    public List<Announcement> getAllTasksByFilter(TaskFilter taskFilter, UserPastry userPastry) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Announcement> criteriaQuery = criteriaBuilder.createQuery(Announcement.class);
        Root<Announcement> root = criteriaQuery.from(Announcement.class);
        List<Predicate> predicates = new ArrayList<>();

        if (taskFilter.getInProgress()) {
            predicates.add(
                    criteriaBuilder.and(
                            criteriaBuilder.equal(root.get("state"), AnnouncementState.IN_PROGRESS),
                            criteriaBuilder.equal(root.get("pastry"), userPastry)
                    )
            );
        }

        if (taskFilter.getWaiting()) {
            Join<Announcement, Offer> offerJoin = root.join("offers");
            predicates.add(
                    criteriaBuilder.and(
                            criteriaBuilder.isNull(root.get("pastry")),
                            criteriaBuilder.equal(offerJoin.get("userPastry"), userPastry)
                    )
            );
        }

        if (taskFilter.getDone()) {
            predicates.add(
                    criteriaBuilder.and(
                            criteriaBuilder.equal(root.get("state"), AnnouncementState.DONE),
                            criteriaBuilder.equal(root.get("pastry"), userPastry)
                    )
            );
        }

        if (taskFilter.getClosed()) {
            predicates.add(
                    criteriaBuilder.and(
                            criteriaBuilder.equal(root.get("state"), AnnouncementState.CLOSED),
                            criteriaBuilder.equal(root.get("pastry"), userPastry)
                    )
            );
        }
        criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    public List<Announcement> getAllClientAnnouncements(AnnouncementClientFilter announcementClientFilter, UserClient userClient) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Announcement> criteriaQuery = criteriaBuilder.createQuery(Announcement.class);
        Root<Announcement> root = criteriaQuery.from(Announcement.class);
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(criteriaBuilder.equal(root.get("creator"), userClient));
        if (announcementClientFilter.getWaiting()) {
            predicates.add(
                    criteriaBuilder.or(
                            criteriaBuilder.equal(root.get("state"), AnnouncementState.CREATED),
                            criteriaBuilder.equal(root.get("state"), AnnouncementState.TAKEN)
                    )
            );
        }

        if (announcementClientFilter.getInProgress()) {
            predicates.add(
                    criteriaBuilder.equal(root.get("state"), AnnouncementState.IN_PROGRESS)
            );
        }

        if (announcementClientFilter.getDelivery()) {
            predicates.add(
                    criteriaBuilder.equal(root.get("state"), AnnouncementState.DONE)
            );
        }

        if (announcementClientFilter.getClosed()) {
            predicates.add(
                    criteriaBuilder.equal(root.get("state"), AnnouncementState.CLOSED)
            );
        }
        criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));
        criteriaQuery.orderBy(criteriaBuilder.desc(root.get("id")));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    public Integer getUserPastryClosedAnnouncementCount(UserPastry userPastry) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Announcement> criteriaQuery = criteriaBuilder.createQuery(Announcement.class);
        Root<Announcement> root = criteriaQuery.from(Announcement.class);
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(criteriaBuilder.equal(root.get("pastry"), userPastry));
        predicates.add(criteriaBuilder.equal(root.get("state"), AnnouncementState.CLOSED));
        criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));
        criteriaQuery.orderBy(criteriaBuilder.desc(root.get("id")));
        return entityManager.createQuery(criteriaQuery).getResultList().size();
    }

    public Integer getUserClientClosedAnnouncementCount(UserClient userClient) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Announcement> criteriaQuery = criteriaBuilder.createQuery(Announcement.class);
        Root<Announcement> root = criteriaQuery.from(Announcement.class);
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(criteriaBuilder.equal(root.get("creator"), userClient));
        predicates.add(criteriaBuilder.equal(root.get("state"), AnnouncementState.CLOSED));
        criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));
        criteriaQuery.orderBy(criteriaBuilder.desc(root.get("id")));
        return entityManager.createQuery(criteriaQuery).getResultList().size();
    }
}
