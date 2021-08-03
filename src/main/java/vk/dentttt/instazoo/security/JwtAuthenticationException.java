package vk.dentttt.instazoo.security;

public class JwtAuthenticationException extends RuntimeException {
    public JwtAuthenticationException(String message) {

    }

    public JwtAuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}
