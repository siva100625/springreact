package com.example.life.service;

import com.example.life.model.Pet;
import com.example.life.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PetService {

    @Autowired
    private PetRepository repo;

    @Autowired
    private JavaMailSender mailSender;

    public List<Pet> getAll() {
        return repo.findAll();
    }

    public Pet save(Pet p) {
        return repo.save(p);
    }

    // Original adopt for internal use
    public Pet adopt(Long id) {
        Pet p = repo.findById(id).orElseThrow();
        p.setAdopted(true);
        return repo.save(p);
    }

    // Enhanced adopt with email
    public Pet adoptWithEmail(Long id, String userEmail) {
        Pet pet = repo.findById(id).orElseThrow();

        if (pet.isAdopted()) {
            throw new IllegalStateException("Pet already adopted");
        }

        pet.setAdopted(true);
        Pet savedPet = repo.save(pet);

        sendAdoptionEmail(userEmail, savedPet);
        return savedPet;
    }

    private void sendAdoptionEmail(String toEmail, Pet pet) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Adoption Confirmation - " + pet.getName());
        message.setText("Hi,You've successfully adopted " + pet.getName() +
                " (" + pet.getSpecies() + "). Thank you for giving them a new home!Regards,PetCare Team");

        mailSender.send(message);
    }

    public Pet update(Long id, Pet updatedPet) {
        Pet pet = repo.findById(id).orElseThrow();
        pet.setName(updatedPet.getName());
        pet.setSpecies(updatedPet.getSpecies());
        pet.setAdopted(updatedPet.isAdopted());
        return repo.save(pet);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
