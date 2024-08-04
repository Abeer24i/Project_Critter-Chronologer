package com.udacity.jdnd.course3.critter.service;
import com.udacity.jdnd.course3.critter.entitie.Customer;
import com.udacity.jdnd.course3.critter.entitie.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class PetService {
    @Autowired
    private PetRepository petRepository;
    @Autowired
    private CustomerRepository customerRepository;
    public Pet savePet(Pet pet, Long ownerId) {
        Customer customer = customerRepository.getOne(ownerId);
        pet.setOwner(customer);
        pet = petRepository.save(pet);
        customer.insertPet(pet);
        customerRepository.save(customer);
        return petRepository.save(pet);
    }
    public List<Pet> getPets() {
        return petRepository.findAll();
    }
    public Pet getPetById(Long id) {
        return petRepository.findById(id).orElse(null);
    }
    public List<Pet> getPetsByOwner(Long ownerId) {
        return petRepository.findAllByOwnerId(ownerId);
    }
}