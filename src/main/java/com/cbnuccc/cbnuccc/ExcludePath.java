package com.cbnuccc.cbnuccc;

import org.springframework.http.HttpMethod;

public record ExcludePath(HttpMethod method, String uriPattern) {
}