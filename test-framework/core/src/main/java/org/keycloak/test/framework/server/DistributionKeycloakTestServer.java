package org.keycloak.test.framework.server;

import io.quarkus.maven.dependency.Dependency;
import org.keycloak.it.utils.RawKeycloakDistribution;

import java.util.List;
import java.util.Set;

public class DistributionKeycloakTestServer implements KeycloakTestServer {

    private static final boolean DEBUG = false;
    private static final boolean MANUAL_STOP = true;
    private static final boolean ENABLE_TLS = false;
    private static final boolean RE_CREATE = false;
    private static final boolean REMOVE_BUILD_OPTIONS_AFTER_BUILD = false;
    private static final int REQUEST_PORT = 8080;

    private RawKeycloakDistribution keycloak;

    @Override
    public void start(List<String> rawOptions, Set<Dependency> dependencies) {
        keycloak = new RawKeycloakDistribution(DEBUG, MANUAL_STOP, ENABLE_TLS, RE_CREATE, REMOVE_BUILD_OPTIONS_AFTER_BUILD, REQUEST_PORT);

        for (Dependency dependency : dependencies) {
            keycloak.copyProvider(dependency.getGroupId(), dependency.getArtifactId());
        }

        keycloak.run(rawOptions).assertStartedDevMode();
    }

    @Override
    public void stop() {
        keycloak.stop();
    }

    @Override
    public String getBaseUrl() {
        return "http://localhost:8080";
    }

}
