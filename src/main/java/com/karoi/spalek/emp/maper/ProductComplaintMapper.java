package com.karoi.spalek.emp.maper;

import com.karoi.spalek.emp.dto.AddProductComplaintDto;
import com.karoi.spalek.emp.dto.ClaimantDto;
import com.karoi.spalek.emp.dto.ProductComplaintDto;
import com.karoi.spalek.emp.model.Claimant;
import com.karoi.spalek.emp.model.ProductComplaint;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductComplaintMapper {

    public static ProductComplaint mapProductComplaint(AddProductComplaintDto addProductComplaintDto, String country) {
        return ProductComplaint.builder()
                .content(addProductComplaintDto.content())
                .identifier(addProductComplaintDto.identifier())
                .claimant(mapToClaimant(addProductComplaintDto.claimant()))
                .country(country)
                .build();
    }

    public static ProductComplaintDto mapProductComplaint(ProductComplaint productComplaint) {
        return new ProductComplaintDto(
                productComplaint.getIdentifier(),
                productComplaint.getContent(),
                productComplaint.getCreateDate(),
                productComplaint.getUpDateCount(),
                mapToClaimantDto(productComplaint.getClaimant())
        );
    }

    public static ClaimantDto mapToClaimantDto(Claimant claimant) {
        return new ClaimantDto(
                claimant.getIdentifier(),
                claimant.getName(),
                claimant.getSurname(),
                claimant.getEmail(),
                claimant.getPhoneNumber()
        );
    }

    public static Claimant mapToClaimant(ClaimantDto claimant) {
        return Claimant.builder()
                .identifier(claimant.identifier())
                .name(claimant.name())
                .surname(claimant.surname())
                .email(claimant.email())
                .phoneNumber(claimant.phoneNumber())
                .build();
    }
}
