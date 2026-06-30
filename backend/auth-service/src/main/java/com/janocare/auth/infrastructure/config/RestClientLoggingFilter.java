package com.janocare.auth.infrastructure.config;

import jakarta.ws.rs.client.ClientRequestContext;
import jakarta.ws.rs.client.ClientRequestFilter;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;

@Provider
public class RestClientLoggingFilter implements ClientRequestFilter {

    @Override
    public void filter(ClientRequestContext requestContext) throws IOException {

        System.out.println("=================================");
        System.out.println("REST CLIENT REQUEST");
        System.out.println("METHOD : " + requestContext.getMethod());
        System.out.println("URL    : " + requestContext.getUri());
        System.out.println("=================================");
    }
}