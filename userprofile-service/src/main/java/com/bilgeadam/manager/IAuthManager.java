package com.bilgeadam.manager;


import com.bilgeadam.dto.request.UpdateEmailOrUsernameRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@FeignClient(
        name = "userprofile-auth",
        url = "http://localhost:8090/api/v1/auth"
)
public interface IAuthManager {
    @PutMapping("/update")
    public ResponseEntity<Boolean> updateUserAuth(@RequestBody UpdateEmailOrUsernameRequestDto dto);
}
