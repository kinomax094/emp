package com.karoi.spalek.emp.service;

import com.karoi.spalek.emp.dto.IpResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@Slf4j
public class IpService {

    private final RestTemplate restTemplate;
    private final String apiCountryAddress;

    public IpService(RestTemplate restTemplate, @Value("${api.country.address}") String apiCountryAddress) {
        this.restTemplate = restTemplate;
        this.apiCountryAddress = apiCountryAddress;
    }

    public Optional<String> getCountry(String ip) {
        try {
            return Optional.ofNullable(restTemplate.getForObject(apiCountryAddress, IpResponse.class, ip).country());
        } catch (HttpClientErrorException e) {
            log.error("Error getting country from IP {}", ip, e);
            return Optional.empty();
        }
    }
}
