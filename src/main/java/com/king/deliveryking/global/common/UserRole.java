package com.king.deliveryking.global.common;

public enum UserRole {
    CUSTOMER(Authority.CUSTOMER), // 고객
    OWNER(Authority.OWNER); // 사장님

    private final String authority;

    UserRole(String authority) {
        this.authority = authority;
    }

    public String getAuthority() {
        return this.authority;
    }

    // 권한을 내부 static class로 관리한다.
    public static class Authority {
        public static final String CUSTOMER = "ROLE_USER";
        public static final String OWNER = "ROLE_ADMIN";
    }

}