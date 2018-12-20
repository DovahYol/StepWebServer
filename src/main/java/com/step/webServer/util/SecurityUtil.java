package com.step.webServer.util;

import org.springframework.security.core.context.SecurityContextHolder;

public final class SecurityUtil {
    public static String currentUserName() {
        return (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
