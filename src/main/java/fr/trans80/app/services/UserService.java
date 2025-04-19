package fr.trans80.app.services;

import fr.trans80.app.models.UserFavorites;
import fr.trans80.app.models.UserHistory;
import fr.trans80.app.repositories.UserFavoriteRepository;
import fr.trans80.app.repositories.UserHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserService {

    private final UserHistoryRepository userHistoryRepository;
    private final UserFavoriteRepository userFavoriteRepository;

    public List<UserHistory> getUserHistoryByUserId(String userId) {
        return this.userHistoryRepository.findAll().stream()
                .filter(history -> history.getUser_id().equals(userId))
                .toList();
    }

    public UserHistory addUserHistory(UserHistory userHistory) {
        return this.userHistoryRepository.save(userHistory);
    }

    public void deleteUserHistory(Long id) {
        Optional<UserHistory> userHistory = userHistoryRepository.findById(id);
        userHistoryRepository.deleteById(id);
    }

    public List<UserFavorites> getUserFavoritesByUserId(String userId) {
        return this.userFavoriteRepository.findAll().stream()
                .filter(favorite -> favorite.getUser_id().equals(userId))
                .toList();
    }

    public UserFavorites addUserFavorite(UserFavorites userFavorite) {
        return this.userFavoriteRepository.save(userFavorite);
    }

    public void deleteUserFavorite(Long id) {
        Optional<UserFavorites> userFavorite = userFavoriteRepository.findById(id);
        userFavoriteRepository.deleteById(id);
    }
}
