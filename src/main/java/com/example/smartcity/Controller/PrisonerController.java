package com.example.smartcity.Controller;

import com.example.smartcity.Service.impl.OfficerServiceImpl;
import com.example.smartcity.Service.impl.PrisonerServiceImpl;
import com.example.smartcity.payload.ApiResponse;
import com.example.smartcity.payload.OfficerDTO;
import com.example.smartcity.payload.PrisonerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/prisoner")
public class PrisonerController {

    @Autowired
    private PrisonerServiceImpl prisonerService;

    @GetMapping
    public ResponseEntity<?> getAll(){
        ApiResponse prisoners = prisonerService.getAllArrestedPeople();
        return ResponseEntity.status(prisoners.isSuccess()?200:404).body(prisoners);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id){
        ApiResponse prisoner = prisonerService.getPrisonerById(id);
        return ResponseEntity.status(prisoner.isSuccess()?200:404).body(prisoner);
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody PrisonerDTO prisonerDTO){
        ApiResponse prisoner = prisonerService.addPrisoner(prisonerDTO);
        return ResponseEntity.status(prisoner.isSuccess()?201:400).body(prisoner);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable UUID id,@RequestBody PrisonerDTO prisonerDTO){
        ApiResponse prisoner = prisonerService.editPrisoner(id,prisonerDTO);
        return ResponseEntity.status(prisoner.isSuccess()?201:400).body(prisoner);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id){
        ApiResponse prisoner = prisonerService.deletePrisoner(id);
        return ResponseEntity.status(prisoner.isSuccess()?200:404).body(prisoner);
    }
}
