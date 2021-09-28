package com.example.smartcity.Controller;

import com.example.smartcity.Service.impl.CrimeServiceImpl;
import com.example.smartcity.Service.impl.OfficerServiceImpl;
import com.example.smartcity.payload.ApiResponse;
import com.example.smartcity.payload.CrimeDTO;
import com.example.smartcity.payload.OfficerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/officer")
public class OfficerController {

    @Autowired
    private OfficerServiceImpl officerService;

    @GetMapping
    public ResponseEntity<?> getAll(){
        ApiResponse allOfficers = officerService.getAllOfficers();
        return ResponseEntity.status(allOfficers.isSuccess()?200:404).body(allOfficers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOfficerById(@PathVariable UUID id){
        ApiResponse officer = officerService.getOfficerById(id);
        return ResponseEntity.status(officer.isSuccess()?200:404).body(officer);
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody OfficerDTO officerDTO){
        ApiResponse officer = officerService.addOfficer(officerDTO);
        return ResponseEntity.status(officer.isSuccess()?201:400).body(officer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable UUID id,@RequestBody OfficerDTO officerDTO){
        ApiResponse officer = officerService.editOfficer(id,officerDTO);
        return ResponseEntity.status(officer.isSuccess()?201:400).body(officer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id){
        ApiResponse officer = officerService.deleteOfficer(id);
        return ResponseEntity.status(officer.isSuccess()?200:404).body(officer);
    }
}
