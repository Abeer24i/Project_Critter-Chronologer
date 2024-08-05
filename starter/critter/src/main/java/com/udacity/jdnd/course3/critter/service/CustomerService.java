package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entitie.Customer;
import com.udacity.jdnd.course3.critter.entitie.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PetRepository petRepository;

    @Transactional
    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id).orElse(null);
    }

    @Transactional
    public Customer getCustomerByPet(Long petId) {
        Pet pet = petRepository.findById(petId).orElse(null);
        if (pet != null && pet.getOwner() != null) {
            return customerRepository.findById(pet.getOwner().getId()).orElse(null);
        }
        return null;
    }
}
