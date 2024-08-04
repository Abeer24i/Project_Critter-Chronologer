package com.udacity.jdnd.course3.critter.pet;
import com.udacity.jdnd.course3.critter.entitie.Pet;
import com.udacity.jdnd.course3.critter.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;
/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {
    @Autowired
    private PetService petService;
    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet pet = convertPetDTOToEntity(petDTO);
        Pet savedPet = petService.savePet(pet, petDTO.getOwnerId());
        return convertEntityToPetDTO(savedPet);
    }
    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        Pet pet = petService.getPetById(petId);
        return convertEntityToPetDTO(pet);
    }
    @GetMapping
    public List<PetDTO> getPets(){
        List<Pet> pets = petService.getPets();
        return pets.stream().map(this::convertEntityToPetDTO).collect(Collectors.toList());
    }
    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        List<Pet> pets = petService.getPetsByOwner(ownerId);
        return pets.stream().map(this::convertEntityToPetDTO).collect(Collectors.toList());
    }
    public PetDTO convertEntityToPetDTO(Pet pet) {
        PetDTO petDTO = new PetDTO();
        Long petId = pet.getId();
        petDTO.setId(petId != null ? petId : -1L);
        petDTO.setType(pet.getType());
        petDTO.setName(pet.getName());
        Long ownerId = (pet.getOwner() != null) ? pet.getOwner().getId() : null;
        petDTO.setOwnerId(ownerId != null ? ownerId : -1L);
        petDTO.setBirthDate(pet.getBirthDate());
        petDTO.setNotes(pet.getNotes());
        return petDTO;
    }
    private Pet convertPetDTOToEntity(PetDTO petDTO) {
        Pet pet = new Pet();
        pet.setName(petDTO.getName());
        pet.setBirthDate(petDTO.getBirthDate());
        pet.setNotes(petDTO.getNotes());
        pet.setType(petDTO.getType());
        return pet;
    }
}