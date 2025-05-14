package com.karoi.spalek.emp.controler;

import com.karoi.spalek.emp.dto.AddProductComplaintDto;
import com.karoi.spalek.emp.dto.EditProductComplaintDto;
import com.karoi.spalek.emp.dto.ProductComplaintDto;
import com.karoi.spalek.emp.dto.ValidationResponse;
import com.karoi.spalek.emp.service.ProductComplainService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/productComplaint")
@AllArgsConstructor
public class ProductComplaintController {

    private final ProductComplainService productComplainService;

    @GetMapping("/{idPproductComplaint}")
    @Operation(summary = "Get product complain", description = "Get product complain")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found product complain.",
                    content = @Content(schema = @Schema(implementation = ProductComplaintDto.class))),
            @ApiResponse(responseCode = "404", description = "Not found product complain.", content =
            @Content(schema = @Schema(implementation = ValidationResponse.class)))
    })
    public ResponseEntity<ProductComplaintDto> getProductComplaint(@PathVariable String idPproductComplaint) {
        return ResponseEntity.ok(productComplainService.getProductComplaint(idPproductComplaint));
    }

    @PostMapping()
    @Operation(summary = "Add product complain", description = "Add product complain")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Add content of product complain.",
                    content = @Content(schema = @Schema(implementation = ResponseEntity.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content =
            @Content(schema = @Schema(implementation = ValidationResponse. class)))
    })
    public ResponseEntity<Void> addProductComplaint(@Valid @RequestBody AddProductComplaintDto addProductComplaintDto) {
        productComplainService.addProductComplaint(addProductComplaintDto);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Edit product complain", description = "Edit product complain")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Edited content of product complain.",
                    content = @Content(schema = @Schema(implementation = ResponseEntity.class))),
            @ApiResponse(responseCode = "404", description = "Not Found product complain.", content =
            @Content(schema = @Schema(implementation = ValidationResponse. class)))
    })
    @PutMapping()
    public ResponseEntity<Void> editProductComplaint(@Valid @RequestBody EditProductComplaintDto editProductComplaintDto) {
        productComplainService.editProductComplaint(editProductComplaintDto);
        return ResponseEntity.ok().build();
    }
}
