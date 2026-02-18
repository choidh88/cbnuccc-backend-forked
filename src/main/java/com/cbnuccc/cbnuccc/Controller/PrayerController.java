package com.cbnuccc.cbnuccc.Controller;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cbnuccc.cbnuccc.Dto.PrayerDto;
import com.cbnuccc.cbnuccc.Service.PrayerService;
import com.cbnuccc.cbnuccc.Service.UserService;
import com.cbnuccc.cbnuccc.Util.LogHeader;
import com.cbnuccc.cbnuccc.Util.StatusCode;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class PrayerController {
    private final UserService userService;
    private final PrayerService prayerService;

    @GetMapping("/prayer")
    public ResponseEntity<?> getPrayers(Authentication authentication) {
        return StatusCode.NO_ERROR.makeErrorResponseEntity();
    }

    @GetMapping("/prayer/{uuid}")
    public ResponseEntity<?> getPrayersByUuid(Authentication authentication, @PathVariable("uuid") UUID uuid) {
        return StatusCode.NO_ERROR.makeErrorResponseEntity();
    }

    // create a prayer
    @PostMapping("/prayer")
    public ResponseEntity<?> createPrayer(Authentication authentication, @RequestBody PrayerDto prayerDto) {
        UUID uuid = userService.getUuidFromAuth(authentication);
        StatusCode result = prayerService.createPrayer(prayerDto, uuid);
        return result.makeErrorResponseEntityAndPrintLog(LogHeader.CREATE_PRAYER, uuid);
    }
}
