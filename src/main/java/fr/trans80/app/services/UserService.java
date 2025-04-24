package fr.trans80.app.services;

import fr.trans80.app.exceptions.NotFoundException;
import fr.trans80.app.models.UserFavorites;
import fr.trans80.app.models.UserHistory;
import fr.trans80.app.repositories.UserFavoriteRepository;
import fr.trans80.app.repositories.UserHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserService {

    private final UserHistoryRepository userHistoryRepository;
    private final UserFavoriteRepository userFavoriteRepository;

    public List<UserHistory> getUserHistoryByUserId(String userId) throws NotFoundException {
        List<UserHistory> histories = this.userHistoryRepository.findAll().stream()
                .filter(history -> history.getUser_id().equals(userId))
                .toList();

        if (histories.isEmpty()) {
            throw new NotFoundException("History not found", "No history found for user with ID: " + userId);
        }

        return histories;
    }

    public UserHistory addUserHistory(UserHistory userHistory) {
        return this.userHistoryRepository.save(userHistory);
    }

    public void deleteUserHistory(Long id) throws NotFoundException {
        if (!userHistoryRepository.existsById(id)) {
            throw new NotFoundException("History not found", "No history found with ID: " + id);
        }
        userHistoryRepository.deleteById(id);
    }

    public List<UserFavorites> getUserFavoritesByUserId(String userId) throws NotFoundException {
        List<UserFavorites> favorites = this.userFavoriteRepository.findAll().stream()
                .filter(favorite -> favorite.getUser_id().equals(userId))
                .toList();

        if (favorites.isEmpty()) {
            throw new NotFoundException("Favorites not found", "No favorites found for user with ID: " + userId);
        }

        return favorites;
    }

    public UserFavorites addUserFavorite(UserFavorites userFavorite) {
        return this.userFavoriteRepository.save(userFavorite);
    }

    public void deleteUserFavorite(Long id) throws NotFoundException {
        if (!userFavoriteRepository.existsById(id)) {
            throw new NotFoundException("Favorite not found", "No favorite found with ID: " + id);
        }
        userFavoriteRepository.deleteById(id);
    }
}
