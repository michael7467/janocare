package com.janocare.auth.infrastructure.config;

import jakarta.ws.rs.client.ClientRequestContext;
import jakarta.ws.rs.client.ClientRequestFilter;
import jakarta.ws.rs.ext.Provider;
import org.jboss.logging.Logger;

import java.io.IOException;

@Provider
public class RestClientLoggingFilter implements ClientRequestFilter {

    private static final Logger LOG =
            Logger.getLogger(RestClientLoggingFilter.class);

    @Override
    public void filter(ClientRequestContext requestContext) throws IOException {
        LOG.infof("REST CLIENT REQUEST | %s %s",
                requestContext.getMethod(),
                requestContext.getUri());
    }
}