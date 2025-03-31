package fr.trans80.app.services;

import fr.trans80.app.exceptions.NotFoundException;
import fr.trans80.app.models.UserHistory;
import fr.trans80.app.repositories.UserHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserHistoryService {

    private final UserHistoryRepository userHistoryRepository;

    private long lastId = 0;

    public List<UserHistory> getUserHistories() {
        return this.userHistoryRepository.findAll();
    }

    public UserHistory updateUserHistory(UserHistory user) throws NotFoundException {
        if (user.getId() == null) {
            user = this.userHistoryRepository.save(user);
            return user;
        } else {
            UserHistory finalUserTest = user;
            Optional<UserHistory> found = this.getUserHistories()
                    .stream()
                    .filter(userKnown -> userKnown.getId().compareTo(finalUserTest.getId()) == 0)
                    .findFirst();
            if (found.isEmpty()) {
                log.error("UserTest with id {} not found", user.getId());
                throw new NotFoundException("Can't update user", "Can't find UserTest with id : " + user.getId());
            }

            found.get().setTrips_id(user.getTrips_id());
            this.userHistoryRepository.save(found.get());
            return found.get();
        }
    }

    public void deleteUserHistory(UserHistory user) throws NotFoundException {
        if (user.getId() == null) {
            log.error("UserTest with id null not found");
            throw new NotFoundException("Can't delete user", "Can't delete UserTest with id null");
        }

        Optional<UserHistory> found = this.getUserHistories()
                .stream()
                .filter(userKnown -> userKnown.getId().compareTo(user.getId()) == 0)
                .findFirst();
        if (found.isEmpty()) {
            log.error("UserTest with id {} not found", user.getId());
            throw new NotFoundException("Can't delete user", "Can't find UserTest with id : " + user.getId());
        }

        this.userHistoryRepository.delete(found.get());
    }
}
