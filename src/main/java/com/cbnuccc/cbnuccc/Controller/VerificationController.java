package com.cbnuccc.cbnuccc.Controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cbnuccc.cbnuccc.StatusCode;
import com.cbnuccc.cbnuccc.Service.VerificationService;

@RestController
public class VerificationController {
    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    VerificationService verificationService;

    // 1000 ms/s * 60 s/min = 60000 ms/min (1 min)
    @Scheduled(fixedRate = 1000 * 60)
    public void before() {
        // delete all expired emails. (pre-process)
        verificationService.deleteAllExpiredEmails();
    }

    @PostMapping("/verification")
    public ResponseEntity<?> sendEmailToVerify(@RequestParam("to") String to) {
        final String code = verificationService.makeCode();

        StatusCode err = verificationService.saveEmailVerification(to, code);
        if (err.checkIsError())
            return err.makeErrorResponseEntity();

        err = verificationService.sendMailCode(to, code);
        if (err.checkIsError())
            return err.makeErrorResponseEntity();

        return StatusCode.NO_ERROR.makeErrorResponseEntity();
    }

    @PostMapping("/verification/confirmation")
    public ResponseEntity<?> verifyCode(@RequestBody Map<String, String> body) {
        if (!(body.containsKey("email") && body.containsKey("code")))
            return StatusCode.NO_ENOUGH_ARGS.makeErrorResponseEntity();

        String email = body.get("email");
        String code = body.get("code");

        return verificationService.verifyCode(email, code).makeErrorResponseEntity();
    }
}
