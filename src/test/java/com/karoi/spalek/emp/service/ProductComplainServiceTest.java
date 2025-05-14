package com.karoi.spalek.emp.service;

import com.karoi.spalek.emp.context.RequestData;
import com.karoi.spalek.emp.dto.AddProductComplaintDto;
import com.karoi.spalek.emp.dto.ClaimantDto;
import com.karoi.spalek.emp.dto.EditProductComplaintDto;
import com.karoi.spalek.emp.dto.ProductComplaintDto;
import com.karoi.spalek.emp.exception.CountryNotFoundException;
import com.karoi.spalek.emp.exception.ProductComplaintNotFounException;
import com.karoi.spalek.emp.model.Claimant;
import com.karoi.spalek.emp.model.ProductComplaint;
import com.karoi.spalek.emp.repository.ClaimantRepository;
import com.karoi.spalek.emp.repository.ProductComplaintRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ProductComplainServiceTest {

    @InjectMocks
    private ProductComplainService productComplainService;
    @Mock
    private RequestData requestData;
    @Mock
    private ProductComplaintRepository productComplaintRepository;
    @Mock
    private ClaimantRepository claimantRepository;
    @Mock
    private IpService ipService;

    @Test
    void saveProductComplainTest() {
        //given
        var claimant = Claimant.builder()
                .email("email")
                .name("name")
                .surname("surname")
                .phoneNumber("phoneNumber")
                .identifier("identifier")
                .build();

        var productComplaint = ProductComplaint.builder()
                .identifier("identifier")
                .country("US")
                .content("content")
                .upDateCount(1)
                .claimant(claimant)
                .createDate(LocalDateTime.MIN)
                .build();

        given(requestData.getIpAddress()).willReturn("127.0.0.1");
        given(ipService.getCountry("127.0.0.1")).willReturn(Optional.of("US"));
        given(productComplaintRepository.findProductComplaintByIdentifier("identifier")).willReturn(Optional.empty());
        given(claimantRepository.findClaimantByIdentifier("identifier")).willReturn(Optional.empty());

        //when
        productComplainService.addProductComplaint(new AddProductComplaintDto("identifier", "content",
                new ClaimantDto("identifier", "name", "surname", "email", "phone")));

        //then
        verify(productComplaintRepository, times(1)).save(productComplaint);
        verify(claimantRepository, times(0)).save(any(Claimant.class));
    }

    @Test
    void saveProductComplainWrongIpTest() {
        //given
        given(requestData.getIpAddress()).willReturn("wrong ip");
        given(ipService.getCountry("wrong ip")).willReturn(Optional.empty());
        given(productComplaintRepository.findProductComplaintByIdentifier("identifier")).willReturn(Optional.of(new ProductComplaint()));

        //then
        assertThatThrownBy(() -> productComplainService.addProductComplaint(new AddProductComplaintDto("identifier", "content",
                new ClaimantDto("identifier", "name", "surname", "email", "phone"))))
                .isInstanceOf(CountryNotFoundException.class)
                .hasMessage("Incorrect user ip");
    }

    @Test
    void upDateProductComplainTest() {
        //given
        var claimant = Claimant.builder()
                .id(1L)
                .email("email")
                .name("name")
                .surname("surname")
                .phoneNumber("phoneNumber")
                .identifier("identifier")
                .build();

        var productComplaint = ProductComplaint.builder()
                .id(1L)
                .identifier("identifier")
                .country("country")
                .content("content")
                .upDateCount(1)
                .claimant(claimant)
                .createDate(LocalDateTime.MIN)
                .build();
        given(requestData.getIpAddress()).willReturn("127.0.0.1");
        given(ipService.getCountry("127.0.0.1")).willReturn(Optional.of("US"));
        given(productComplaintRepository.findProductComplaintByIdentifier("identifier")).willReturn(Optional.of(productComplaint));
        given(claimantRepository.findClaimantByIdentifier("identifier")).willReturn(Optional.of(claimant));

        //when
        productComplainService.addProductComplaint(new AddProductComplaintDto("identifier", "content2",
                new ClaimantDto("identifier", "name1", "surname1", "email1", "phone1")));

        //then
        verify(productComplaintRepository, times(1)).save(ProductComplaint.builder()
                .id(1L)
                .identifier("identifier")
                .country("country")
                .content("content")
                .claimant(Claimant.builder()
                        .id(1L)
                        .email("email1")
                        .name("name1")
                        .surname("surname1")
                        .phoneNumber("phoneNumber1")
                        .identifier("identifier")
                        .build())
                .upDateCount(2)
                .claimant(claimant)
                .createDate(LocalDateTime.MIN)
                .build());
    }

    @Test
    void editProductComplainTest() {
        var productComplaint = ProductComplaint.builder()
                .id(1L)
                .identifier("identifier")
                .country("country")
                .content("content")
                .upDateCount(1)
                .claimant(Claimant.builder()
                        .id(1L)
                        .email("email")
                        .name("name")
                        .surname("surname")
                        .phoneNumber("phoneNumber")
                        .identifier("identifier")
                        .build())
                .createDate(LocalDateTime.MIN)
                .build();
        given(productComplaintRepository.findProductComplaintByIdentifier("identifier")).willReturn(Optional.of(productComplaint));
        given(productComplaintRepository.save(any())).willReturn(new ProductComplaint());

        //when
        productComplainService.editProductComplaint(new EditProductComplaintDto("identifier", "newContent"));

        //then
        verify(productComplaintRepository, times(1)).findProductComplaintByIdentifier("identifier");
        verify(productComplaintRepository, times(1)).save(ProductComplaint.builder()
                .id(1L)
                .identifier("identifier")
                .country("country")
                .content("newContent")
                .upDateCount(2)
                .claimant(Claimant.builder()
                        .id(1L)
                        .email("email")
                        .name("name")
                        .surname("surname")
                        .phoneNumber("phoneNumber")
                        .identifier("identifier")
                        .build())
                .createDate(LocalDateTime.MIN)
                .build());
    }

    @Test
    void editProductComplainNotFoundTest() {
        //given
        given(productComplaintRepository.findProductComplaintByIdentifier("identifier")).willReturn(Optional.empty());

        //then
        assertThatThrownBy(() -> productComplainService.editProductComplaint(new EditProductComplaintDto("identifier", "newContent")))
                .isInstanceOf(ProductComplaintNotFounException.class)
                .hasMessage("Product complaint not found");
        verify(productComplaintRepository, times(1)).findProductComplaintByIdentifier("identifier");
    }

    @Test
    void getProductComplainTest() {
        //given
        var productComplaint = ProductComplaint.builder()
                .id(1L)
                .identifier("identifier")
                .country("country")
                .content("content")
                .upDateCount(1)
                .claimant(Claimant.builder()
                        .id(1L)
                        .email("email")
                        .name("name")
                        .surname("surname")
                        .phoneNumber("phoneNumber")
                        .identifier("identifier")
                        .build())
                .createDate(LocalDateTime.MIN)
                .build();
        given(productComplaintRepository.findProductComplaintByIdentifier("identifier")).willReturn(Optional.of(productComplaint));

        //when
        ProductComplaintDto productComplaintDto = productComplainService.getProductComplaint("identifier");

        //then
        verify(productComplaintRepository, times(1)).findProductComplaintByIdentifier("identifier");

        assertThat(productComplaintDto)
                .extracting(ProductComplaintDto::identifier,
                        ProductComplaintDto::content,
                        ProductComplaintDto::createDate,
                        ProductComplaintDto::upDateCount)
                .containsExactly("identifier", "content", LocalDateTime.MIN, 1);

        assertThat(productComplaintDto.claimant())
                .extracting(ClaimantDto::identifier,
                        ClaimantDto::name,
                        ClaimantDto::surname,
                        ClaimantDto::email,
                        ClaimantDto::phoneNumber)
                .containsExactly("identifier", "name", "surname", "email", "phoneNumber");
    }

    @Test
    void getProductComplainNotFoundTest() {
        //given
        given(productComplaintRepository.findProductComplaintByIdentifier("identifier")).willReturn(Optional.empty());

        //then
        assertThatThrownBy(() -> productComplainService.getProductComplaint("identifier"))
                .isInstanceOf(ProductComplaintNotFounException.class)
                .hasMessage("Product complaint not found");
        verify(productComplaintRepository, times(1)).findProductComplaintByIdentifier("identifier");
    }
}