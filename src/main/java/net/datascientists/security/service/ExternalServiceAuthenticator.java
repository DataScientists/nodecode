package net.datascientists.security.service;

import net.datascientists.security.model.AuthenticationWithToken;

@FunctionalInterface
public interface ExternalServiceAuthenticator {

    AuthenticationWithToken authenticate(String username, String password);
}
