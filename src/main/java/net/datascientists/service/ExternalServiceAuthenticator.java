package net.datascientists.service;

@FunctionalInterface
public interface ExternalServiceAuthenticator {

    AuthenticationWithToken authenticate(String username, String password);
}
