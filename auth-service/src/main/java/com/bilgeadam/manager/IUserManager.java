package com.bilgeadam.manager;

import com.bilgeadam.dto.request.NewCreateUserRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@FeignClient(
        name = "auth-userprofile",
        url = "http://localhost:8080/api/v1/user-profile"
)
public interface IUserManager {
    @PostMapping("/create")
    public ResponseEntity<Boolean> createUser(@RequestBody NewCreateUserRequestDto dto);
    @GetMapping("/activate-status/{authId}")
    public ResponseEntity<Boolean> activateStatus(@PathVariable Long authId);
}

