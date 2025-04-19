package fr.trans80.app.controllers;

import fr.trans80.app.models.UserHistory;
import fr.trans80.app.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.List;

@RestController
@RequestMapping("/user_history")
@RequiredArgsConstructor
public class UserHistoryController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserHistory>> getUserHistory(@AuthenticationPrincipal Jwt principal) {
        String userId = principal.getSubject();
        List<UserHistory> history = userService.getUserHistoryByUserId(userId);
        return ResponseEntity.ok(history);
    }

    @PostMapping
    public ResponseEntity<UserHistory> addUserHistory(@RequestBody UserHistory userHistory, @AuthenticationPrincipal Jwt principal) {
        userHistory.setUser_id(principal.getSubject());
        UserHistory savedHistory = userService.addUserHistory(userHistory);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedHistory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserHistory(@PathVariable("id") Long id, @AuthenticationPrincipal Jwt principal) {
        userService.deleteUserHistory(id);
        return ResponseEntity.noContent().build();
    }

}
