package com.example.smartcity.Controller;

import com.example.smartcity.Service.impl.WitnessServiceImpl;
import com.example.smartcity.payload.ApiResponse;
import com.example.smartcity.payload.WitnessDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/witness")
public class WitnessController {

    @Autowired
    private WitnessServiceImpl witnessService;

    @GetMapping
    public ResponseEntity<?> getAll(){
        ApiResponse witness = witnessService.getAllWitnesses();
        return ResponseEntity.status(witness.isSuccess()?200:404).body(witness);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id){
        ApiResponse witness = witnessService.getWitnessById(id);
        return ResponseEntity.status(witness.isSuccess()?200:404).body(witness);
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody WitnessDTO witnessDTO){
        ApiResponse witness = witnessService.addWitness(witnessDTO);
        return ResponseEntity.status(witness.isSuccess()?201:400).body(witness);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable UUID id,@RequestBody WitnessDTO witnessDTO){
        ApiResponse witness = witnessService.editWitness(id,witnessDTO);
        return ResponseEntity.status(witness.isSuccess()?201:400).body(witness);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id){
        ApiResponse witness = witnessService.deleteWitness(id);
        return ResponseEntity.status(witness.isSuccess()?200:404).body(witness);
    }
}
