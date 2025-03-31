package fr.trans80.app.controllers;

import fr.trans80.app.exceptions.NotFoundException;
import fr.trans80.app.models.UserHistory;
import fr.trans80.app.services.UserHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user_history")
@RequiredArgsConstructor
public class UserHistoryController {

    private final UserHistoryService userService;

    @GetMapping
    public List<UserHistory> getAllUserHistories() {
        return this.userService.getUserHistories();
    }

    @PostMapping
    public ResponseEntity<UserHistory> addUserHistory(@RequestBody UserHistory user) {
        try {
            return new ResponseEntity<>(this.userService.updateUserHistory(user), HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteUserHistory(@RequestBody UserHistory user) {
        try {
            this.userService.deleteUserHistory(user);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
