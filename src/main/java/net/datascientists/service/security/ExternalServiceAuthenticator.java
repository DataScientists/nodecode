package net.datascientists.service.security;

@FunctionalInterface
public interface ExternalServiceAuthenticator {

    AuthenticationWithToken authenticate(String username, String password);
}
