package br.com.jrsr.financeapi.application.controllers;

import br.com.jrsr.financeapi.application.dto.response.QueryTypeResponse;
import br.com.jrsr.financeapi.application.services.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/types")
public class TypeController {

    private final TypeService service;

    public TypeController(TypeService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<QueryTypeResponse>> queryAll() {
        List<QueryTypeResponse> list = service.getAll();
        return ResponseEntity.ok().body(list);
    }
}
