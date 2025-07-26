package com.example.life.controllers;

import com.example.life.model.Pet;
import com.example.life.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pets")
@CrossOrigin(origins = "http://localhost:5173")
public class PetController {

    @Autowired
    private PetService service;

    // GET: List all pets
    @GetMapping
    public List<Pet> all() {
        return service.getAll();
    }

    // POST: Add new pet
    @PostMapping
    public Pet save(@RequestBody Pet pet) {
        return service.save(pet);
    }

    // PUT: Adopt a pet by ID
    @PutMapping("/{id}/adopt")
    public Pet adopt(@PathVariable Long id) {
        return service.adopt(id);
    }

    @PutMapping("/{id}")
    public Pet update(@PathVariable Long id, @RequestBody Pet pet) {
        return service.update(id, pet);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
    @PutMapping("/adopt/{id}")
    public ResponseEntity<Pet> adoptPet(@PathVariable Long id, @RequestParam String userEmail) {
        try {
            Pet adopted = service.adoptWithEmail(id, userEmail);
            return ResponseEntity.ok(adopted);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

}
