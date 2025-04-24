package fr.trans80.app.controllers;

import fr.trans80.app.exceptions.NotFoundException;
import fr.trans80.app.models.UserFavorites;
import fr.trans80.app.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.List;

@RestController
@RequestMapping("/user_favorite")
@RequiredArgsConstructor
public class UserFavoriteController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserFavorites>> getUserFavorites(@AuthenticationPrincipal Jwt principal) throws NotFoundException {
        String userId = principal.getSubject();
        List<UserFavorites> favorites = userService.getUserFavoritesByUserId(userId);
        return ResponseEntity.ok(favorites);
    }

    @PostMapping
    public ResponseEntity<UserFavorites> addUserFavorite(@RequestBody UserFavorites userFavorite, @AuthenticationPrincipal Jwt principal) {
        userFavorite.setUser_id(principal.getSubject());
        UserFavorites savedFavorite = userService.addUserFavorite(userFavorite);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedFavorite);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserFavorite(@PathVariable("id") Long id) throws NotFoundException {
        userService.deleteUserFavorite(id);
        return ResponseEntity.noContent().build();
    }
}
