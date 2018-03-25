package pl.piomin.services.gateway.filter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by anhtrang on 24/2/18.
 */
@Component
public class OauthProxyFilter extends ZuulFilter{

    Logger logger = Logger.getLogger(OauthProxyFilter.class);


    @Autowired
    private RestTemplate restTemplate;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {

         RequestContext requestContext =  RequestContext.getCurrentContext();
         requestContext.addZuulRequestHeader("Authorization", "Bearer" + getAccessToken());

        return null;
    }

    private String getAccessToken(){

        restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor("customer-integration-system", "1234567890"));


        MultiValueMap<String,String> headers =new LinkedMultiValueMap<>();
        headers.put("Content-Type", Arrays.asList("application/x-www-form-urlencoded"));

        Map<String,String> params =new HashMap<>();
        params.put("grant_type", "client_credentials");


        RequestEntity<Map<String,String>> requestEntity = new RequestEntity<>(params, headers, HttpMethod.POST,URI.create("http://AUTH-SERVER/auu/oauth/token"));

        try {

            ResponseEntity<String> exchange = restTemplate.exchange(requestEntity, String.class);

            logger.info(exchange.getBody());

            ObjectMapper objectMapper = new ObjectMapper();

            Map<String, Object>  jsonMap = objectMapper.readValue(exchange.getBody(), new TypeReference<Map<String, Object>>(){} );
            return  (String)jsonMap.get("access_token");
        } catch (IOException e) {
            logger.error(e.getMessage());
            return  null;
        }

    }
}
