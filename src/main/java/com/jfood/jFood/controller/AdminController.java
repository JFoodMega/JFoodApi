package com.jfood.jFood.controller;

import com.jfood.jFood.dto.request.CreateClerkRequest;
import com.jfood.jFood.dto.request.UpdateClerkRequest;
import com.jfood.jFood.dto.response.UserResponse;
import com.jfood.jFood.service.AdminService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/clerks")
    public ResponseEntity<UserResponse> createClerk(@Valid @RequestBody CreateClerkRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(adminService.createClerk(request));
    }

    @PutMapping("/clerks/{id}")
    public ResponseEntity<UserResponse> updateClerk(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateClerkRequest request) {
        return ResponseEntity.ok(adminService.updateClerk(id, request));
    }

    @DeleteMapping("/clerks/{id}")
    public ResponseEntity<Void> deleteClerk(@PathVariable UUID id) {
        adminService.deleteClerk(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/clerks")
    public ResponseEntity<List<UserResponse>> getAllClerks() {
        return ResponseEntity.ok(adminService.getAllClerks());
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(adminService.getAllUsers());
    }
}

