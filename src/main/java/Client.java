import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.util.*;
import java.io.IOException;

public class Client {
    public static final String SERVICE_URL = "https://raw.githubusercontent.com/netology-code/jd-homeworks/master/http/task1/cats";
    public static ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) throws IOException {
        try (CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(5000)
                        .setSocketTimeout(5000)
                        .setRedirectsEnabled(false)
                        .build())
                .build()) {
            HttpGet httpGet = new HttpGet(SERVICE_URL);
            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                List<Fact> facts = mapper.readValue(
                        response.getEntity().getContent(),
                        new TypeReference<>() {
                        }
                );
                facts.stream().filter(item -> item.getUpvotes() != 0).forEach(System.out::println);

            }
        }
    }
}

