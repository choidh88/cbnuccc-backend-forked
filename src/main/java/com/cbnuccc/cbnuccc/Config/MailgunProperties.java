package com.cbnuccc.cbnuccc.Config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@ConfigurationProperties(prefix = "mailgun")
@RequiredArgsConstructor
@Getter
public class MailgunProperties {
    private final String key;
    private final String domain;
}
