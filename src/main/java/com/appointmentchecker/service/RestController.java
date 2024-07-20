package com.appointmentchecker.service;

import com.appointmentchecker.service.drlib.DrLibRequestController;
import com.appointmentchecker.service.drlib.DrLibResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Date;


@org.springframework.web.bind.annotation.RestController
public class RestController {

    @GetMapping("/")
    @Bean
    public String index(DrLibRequestController drlibcontroller) throws JsonProcessingException {

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.15; rv:128.0) Gecko/20100101 Firefox/128.0");

        HttpEntity<String> entity = new HttpEntity<String>(headers);

        DrLibResponse response =
                restTemplate.exchange(url, HttpMethod.GET, entity, new ParameterizedTypeReference<DrLibResponse>() {}).getBody();


        assert response != null;
        return Boolean.toString(drlibcontroller.isAvailable(2208180, 352260, 138926, "public", false, 5, new Date()));
    }

}
