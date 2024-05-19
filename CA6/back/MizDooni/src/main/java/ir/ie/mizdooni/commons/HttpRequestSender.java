package ir.ie.mizdooni.commons;

import org.springframework.web.client.RestTemplate;

public class HttpRequestSender {
    public static String sendGetRequest(String url) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, String.class);
    }
}
