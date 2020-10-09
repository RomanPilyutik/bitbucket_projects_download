package org.example;

import org.apache.commons.codec.binary.Base64;
import org.example.dto.RepoList;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;

public class BitBucketClient {

    private final RestTemplate restTemplate;

    public BitBucketClient() {
        this.restTemplate = new RestTemplate();
    }

    public RepoList getBitBucketRepoList(final String project) {
        final ResponseEntity<RepoList> responseEntity = restTemplate.exchange
                ("http://bitbucket_url/rest/api/1.0/projects/" + project + "/repos", HttpMethod.GET,
                        new HttpEntity<>(createHeaders("gl_username", "gl_password)")), RepoList.class);

        return responseEntity.getBody();
    }

    HttpHeaders createHeaders(final String username, final String password){
        return new HttpHeaders() {{
            String auth = username + ":" + password;
            byte[] encodedAuth = Base64.encodeBase64(
                    auth.getBytes(StandardCharsets.US_ASCII) );
            String authHeader = "Basic " + new String( encodedAuth );
            set( "Authorization", authHeader );
        }};
    }
}
