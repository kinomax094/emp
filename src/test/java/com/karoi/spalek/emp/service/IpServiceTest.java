package com.karoi.spalek.emp.service;

import com.karoi.spalek.emp.dto.IpResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class IpServiceTest {

    private IpService ipService;

    @Mock
    private RestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        this.ipService = new IpService(restTemplate, "https://api.country.is/{id}");
    }

    @Test
    void getIpTest() {
        //given
        given(restTemplate.getForObject("https://api.country.is/{id}", IpResponse.class, "ip"))
                .willReturn(new IpResponse("ip", "country"));

        //when
        Optional<String> ip = ipService.getCountry("ip");

        //then
        Assertions.assertTrue(ip.isPresent());
        Assertions.assertEquals("country", ip.get());
    }

    @Test
    void getIpTestNotFound() {
        //given
        given(restTemplate.getForObject("https://api.country.is/{id}", IpResponse.class, "ip"))
                .willThrow(new HttpClientErrorException(HttpStatus.FOUND));

        //when
        Optional<String> ip = ipService.getCountry("ip");

        //then
        Assertions.assertFalse(ip.isPresent());
    }
}