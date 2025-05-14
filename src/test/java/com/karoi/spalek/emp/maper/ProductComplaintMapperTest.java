package com.karoi.spalek.emp.maper;

import com.karoi.spalek.emp.dto.AddProductComplaintDto;
import com.karoi.spalek.emp.dto.ClaimantDto;
import com.karoi.spalek.emp.dto.ProductComplaintDto;
import com.karoi.spalek.emp.model.Claimant;
import com.karoi.spalek.emp.model.ProductComplaint;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class ProductComplaintMapperTest {

    @Test
    void mapProductComplaintToProductComplaintDto() {
        //given
        ProductComplaint productComplaint = ProductComplaint.builder()
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

        //when
        ProductComplaintDto result = ProductComplaintMapper.mapProductComplaint(productComplaint);

        //then
        assertThat(result)
                .extracting(ProductComplaintDto::identifier,
                        ProductComplaintDto::content,
                        ProductComplaintDto::createDate,
                        ProductComplaintDto::upDateCount)
                .containsExactly("identifier", "content", LocalDateTime.MIN, 1);

        assertThat(result.claimant())
                .extracting(ClaimantDto::identifier,
                        ClaimantDto::name,
                        ClaimantDto::surname,
                        ClaimantDto::email,
                        ClaimantDto::phoneNumber)
                .containsExactly("identifier", "name", "surname", "email", "phoneNumber");
    }

    @Test
    void mapProductComplaintDtoToProductComplaint() {
        //given
        AddProductComplaintDto productComplaintDto = new AddProductComplaintDto("identifier",
                "content", new ClaimantDto("identifier", "name", "surname", "email", "phoneNumber"));

        //when
        ProductComplaint result = ProductComplaintMapper.mapProductComplaint(productComplaintDto, "country");

        //then
        assertThat(result)
                .extracting(ProductComplaint::getIdentifier,
                        ProductComplaint::getContent,
                        ProductComplaint::getUpDateCount,
                        ProductComplaint::getCountry)
                .containsExactly("identifier", "content", 0, "country");

        assertThat(result.getClaimant())
                .extracting(Claimant::getIdentifier,
                        Claimant::getName,
                        Claimant::getSurname,
                        Claimant::getEmail,
                        Claimant::getPhoneNumber)
                .containsExactly("identifier", "name", "surname", "email", "phoneNumber");
    }
}