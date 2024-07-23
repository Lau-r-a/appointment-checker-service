package com.appointmentchecker.service.drlib;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

@Component
public class DrLibController {

    public boolean isAvailable(DrLibParams params) {
        return isAvailable(
                params.getVisitMotiveIds(),
                params.getAgendaIds(),
                params.getPracticeIds(),
                params.getInsuranceSector(),
                params.isTelehealth(),
                5,
                new Date()
        );
    }

    public boolean isAvailable(long visitMotiveIds, int agendaIds, int practiceIds, String insuranceSector, boolean telehealth, int limt, Date startDate) {
        String url = buildUrl(visitMotiveIds, agendaIds, practiceIds, insuranceSector, telehealth, limt, startDate);

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.15; rv:128.0) Gecko/20100101 Firefox/128.0");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        DrLibResponse response =
                restTemplate.exchange(url, HttpMethod.GET, entity, new ParameterizedTypeReference<DrLibResponse>() {}).getBody();

        assert response != null;
        return response.total > 0;
    }

    private String buildUrl(long visitMotiveIds, int agendaIds, int practiceIds, String insuranceSector, boolean telehealth, int limt, Date startDate){
        String pattern = "yyyy-MM-dd";
        DateFormat df = new SimpleDateFormat(pattern);

        String baseUrl = "https://www.doctolib.de/availabilities.json?visit_motive_ids=%d&agenda_ids=%d&practice_ids=%d&insurance_sector=%s&telehealth=%b&limit=%d&start_date=%s";
        return String.format(baseUrl, visitMotiveIds, agendaIds, practiceIds, insuranceSector, telehealth, limt, df.format(startDate));
    }
}
