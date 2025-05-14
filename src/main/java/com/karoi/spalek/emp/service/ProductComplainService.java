package com.karoi.spalek.emp.service;

import com.karoi.spalek.emp.constant.MessageConstant;
import com.karoi.spalek.emp.context.RequestData;
import com.karoi.spalek.emp.dto.AddProductComplaintDto;
import com.karoi.spalek.emp.dto.ClaimantDto;
import com.karoi.spalek.emp.dto.EditProductComplaintDto;
import com.karoi.spalek.emp.dto.ProductComplaintDto;
import com.karoi.spalek.emp.exception.CountryNotFoundException;
import com.karoi.spalek.emp.exception.ProductComplaintNotFounException;
import com.karoi.spalek.emp.maper.ProductComplaintMapper;
import com.karoi.spalek.emp.model.Claimant;
import com.karoi.spalek.emp.model.ProductComplaint;
import com.karoi.spalek.emp.repository.ClaimantRepository;
import com.karoi.spalek.emp.repository.ProductComplaintRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductComplainService {

    private final RequestData requestData;
    private final ProductComplaintRepository productComplaintRepository;
    private final ClaimantRepository claimantRepository;
    private final IpService ipService;

    public void addProductComplaint(AddProductComplaintDto addProductComplaintDto) {
        productComplaintRepository.findProductComplaintByIdentifier(addProductComplaintDto.identifier())
                .map(this::upDateProductComplaint)
                .orElseGet(() -> saveProductComplaint(addProductComplaintDto));
    }

    public void editProductComplaint(EditProductComplaintDto editProductComplaintDto) {
        productComplaintRepository.findProductComplaintByIdentifier(editProductComplaintDto.identifier())
                .map((product) -> {
                    product.setContent(editProductComplaintDto.content());
                    return productComplaintRepository.save(product);
                })
                .orElseThrow(() -> new ProductComplaintNotFounException(MessageConstant.PRODUCT_COMPLAINT_NOT_FOUND));
    }

    public ProductComplaintDto getProductComplaint(String identifier) {
        return productComplaintRepository.findProductComplaintByIdentifier(identifier)
                .map(ProductComplaintMapper::mapProductComplaint)
                .orElseThrow(() -> new ProductComplaintNotFounException(MessageConstant.PRODUCT_COMPLAINT_NOT_FOUND));
    }

    private ProductComplaint upDateProductComplaint(ProductComplaint productComplaint) {
        int upDateCount = productComplaint.getUpDateCount();
        productComplaint.setUpDateCount(++upDateCount);
        return productComplaintRepository.save(productComplaint);
    }

    private ProductComplaint saveProductComplaint(AddProductComplaintDto addProductComplaintDto) {
        String country = ipService.getCountry(requestData.getIpAddress())
                .orElseThrow(() -> new CountryNotFoundException(MessageConstant.INCORRECT_USER_ID));

        ProductComplaint productComplaint = ProductComplaintMapper.mapProductComplaint(addProductComplaintDto, country);
        claimantRepository.findClaimantByIdentifier(addProductComplaintDto.claimant().identifier())
                .ifPresent(claimant -> productComplaint.setClaimant(updateClaimant(claimant, addProductComplaintDto.claimant())));
        return productComplaintRepository.save(productComplaint);
    }

    private Claimant updateClaimant(Claimant claimant, ClaimantDto claimantDto) {
        claimant.setName(claimantDto.name());
        claimant.setSurname(claimantDto.surname());
        claimant.setEmail(claimantDto.email());
        claimant.setPhoneNumber(claimantDto.phoneNumber());
        return claimant;
    }
}
