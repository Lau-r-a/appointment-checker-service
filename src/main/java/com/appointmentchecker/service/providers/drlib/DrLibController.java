package com.appointmentchecker.service.providers.drlib;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

@Slf4j
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
        //use latest Firefox LTS Windows User Agent, because no sane person would like to block it
        headers.set("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:128.0) Gecko/20100101 Firefox/128.0");

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

    public DrLibParams filterDrLibParamsFromResponse(String url, DrLibInfoResponse infoResponse) {

        UriComponents uri = UriComponentsBuilder.fromUriString(url).build();
        MultiValueMap<String, String> parameters = uri.getQueryParams();

        String motiveId = parameters.getFirst("motiveIds%5B%5D");
        String practitionerId = parameters.getFirst("practitionerId");
        String pid = parameters.getFirst("pid").replaceFirst("^practice-", "");
        String insuranceSector = parameters.getFirst("insuranceSector");
        boolean telehealth = Boolean.parseBoolean(parameters.get("telehealth").getFirst());

        DrLibAgenda agenda = infoResponse.getAgendaList().stream()
                .filter(drLibAgenda -> drLibAgenda.getPractitionerId() == (practitionerId != null ? Integer.parseInt(practitionerId) : drLibAgenda.getPractitionerId()))
                .filter(drLibAgenda -> drLibAgenda.getPracticeId() == (pid != null ? Integer.parseInt(pid) : drLibAgenda.getPracticeId()))
                .filter(drLibAgenda -> drLibAgenda.getVisitMotiveIds().stream().mapToLong(i -> i).filter(i -> i == Integer.parseInt(motiveId)).findAny().isPresent())
                .filter(drLibAgenda -> !drLibAgenda.isBookingDisabled())
                .findAny().get();

        return new DrLibParams(Integer.parseInt(motiveId), agenda.getId(), Integer.parseInt(pid), insuranceSector, telehealth);
    }

    public DrLibInfoResponse requestDrLibInfoFromPublicUrl(String url) {
        UriComponents uri = UriComponentsBuilder.fromUriString(url).build();

        String slug = uri.getPathSegments().get(2);
        String infoUrl = String.format("https://www.doctolib.de/online_booking/api/slot_selection_funnel/v1/info.json?profile_slug=%s", slug);

        log.info("Deserialized to DrLibInfoResponse Object from {}", infoUrl);

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForEntity(infoUrl, DrLibInfoResponse.class).getBody();
    }
}
