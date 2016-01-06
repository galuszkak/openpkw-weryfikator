package org.openpkw.weryfikator.rest.basicTests;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;

import org.junit.Test;
import org.openpkw.weryfikator.rest.Configuration;
import org.openpkw.weryfikator.rest.JAXRSTestBase;

public class When_sending_POST_request_to_echo_url extends JAXRSTestBase {

    @Test
    public void Should_return_the_same_content_that_wast_sent() {
        String testContent = "{\"test\":\"OpenPKW rules\"}";

        Client client = createClient();
        WebTarget target = client.target(Configuration.getHost() + "/test/echo");
        String response = target.request().post(Entity.json(testContent), String.class);

        assertThat(response, equalTo(testContent));
    }
}