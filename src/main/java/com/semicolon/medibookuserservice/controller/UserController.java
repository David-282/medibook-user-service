package com.semicolon.medibookuserservice.controller;

import com.semicolon.medibookuserservice.dto.request.UpdateUserRequest;
import com.semicolon.medibookuserservice.dto.request.UpdateUserStatusRequest;
import com.semicolon.medibookuserservice.dto.response.UserResponse;
import com.semicolon.medibookuserservice.enums.Role;
import com.semicolon.medibookuserservice.enums.UserStatus;
import com.semicolon.medibookuserservice.service.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;

//    @PostMapping("/create")
//    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody CreateUserRequest request) {
//        UserResponse response = userService.createUser(request);
//        return ResponseEntity.status(HttpStatus.CREATED).body(response);
//    }


    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable UUID id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

//    @GetMapping("/get-by-email")
//    public ResponseEntity<UserResponse> getUserByEmail(@RequestParam String email) {
//        return ResponseEntity.ok(userService.getUserByEmail(email));
//    }

    @PatchMapping("/update-info/{id}")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable UUID id,
            @RequestBody UpdateUserRequest request) {
        return ResponseEntity.ok(userService.updateUser(id, request));
    }

    @PatchMapping("/update-status/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponse> updateUserStatus(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateUserStatusRequest request) {
        return ResponseEntity.ok(userService.updateUserStatus(id, request));
    }


//    @GetMapping("/get-by-role")
//    public ResponseEntity<List<UserResponse>> getUsersByRole(@RequestParam Role role) {
//        return ResponseEntity.ok(userService.getUsersByRole(role));
//    }

//    @GetMapping("/get-by-status")
//    public ResponseEntity<List<UserResponse>> getUsersByStatus(@RequestParam UserStatus status) {
//        return ResponseEntity.ok(userService.getUsersByStatus(status));
//    }

//    @GetMapping("/get-all-users")
//    public ResponseEntity<List<User>> getAllUsers(){
//        return ResponseEntity.ok(userService.getAllUsers());
//    }

    @DeleteMapping("/disable-user/{id}")
    public ResponseEntity<UserResponse> disableUser(@PathVariable UUID id) {

        return ResponseEntity.ok(userService.disableUser(id));
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getUsers(
            @RequestParam(required = false) String email,
            @RequestParam(required = false) Role role,
            @RequestParam(required = false) UserStatus status) {

        if (email != null) return ResponseEntity.ok(List.of(userService.getUserByEmail(email)));
        if (role != null) return ResponseEntity.ok(userService.getUsersByRole(role));
        if (status != null) return ResponseEntity.ok(userService.getUsersByStatus(status));

        return ResponseEntity.ok(userService.getAllUsers());
    }
}
