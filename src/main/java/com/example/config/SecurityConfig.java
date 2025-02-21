package com.example.config;

import jakarta.annotation.security.DeclareRoles;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.security.enterprise.authentication.mechanism.http.BasicAuthenticationMechanismDefinition;
import jakarta.security.enterprise.identitystore.DatabaseIdentityStoreDefinition;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;

@BasicAuthenticationMechanismDefinition(realmName = "myRealm")
@DatabaseIdentityStoreDefinition(
        dataSourceLookup = "java:app/MyDataSource",
        callerQuery = "SELECT password FROM users WHERE email = ?",
        groupsQuery = "SELECT role FROM user_roles WHERE email = ?",
        hashAlgorithm = Pbkdf2PasswordHash.class
)
@DeclareRoles({"USER", "ADMIN"})
@ApplicationScoped
public class SecurityConfig {
    // Lớp này thiết lập cơ chế xác thực & phân quyền dựa trên cơ sở dữ liệu.
}
