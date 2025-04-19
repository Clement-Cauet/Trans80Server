package fr.trans80.app.repositories;

import fr.trans80.app.models.UserFavorites;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserFavoriteRepository extends JpaRepository<UserFavorites, Long> {
}
