package br.com.jrsr.financeapi.application.controllers;

import br.com.jrsr.financeapi.application.dto.response.QueryTypeResponse;
import br.com.jrsr.financeapi.application.services.TypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/type")
@Tag(name = "Types", description = "Operations related to transaction types")
public class TypeController {

    private final TypeService service;

    public TypeController(TypeService service) {
        this.service = service;
    }

    @Operation(summary = "Retrieve all transaction types")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of transaction types retrieved successfully"),
    })
    @GetMapping
    public ResponseEntity<List<QueryTypeResponse>> queryAll() {
        List<QueryTypeResponse> list = service.getAll();
        return ResponseEntity.ok().body(list);
    }
}
