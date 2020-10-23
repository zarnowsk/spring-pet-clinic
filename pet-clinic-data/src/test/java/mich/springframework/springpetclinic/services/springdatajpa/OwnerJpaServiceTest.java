package mich.springframework.springpetclinic.services.springdatajpa;

import mich.springframework.springpetclinic.model.Owner;
import mich.springframework.springpetclinic.repositories.OwnerRepository;
import mich.springframework.springpetclinic.repositories.PetRepository;
import mich.springframework.springpetclinic.repositories.PetTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OwnerJpaServiceTest {

    final String lastName = "Smith";
    final Long id = 1L;
    Owner mockOwner;

    @Mock
    OwnerRepository ownerRepository;
    @Mock
    PetRepository petRepository;
    @Mock
    PetTypeRepository petTypeRepository;

    @InjectMocks
    OwnerJpaService service;

    @BeforeEach
    void setUp() {
        mockOwner = Owner.builder().id(id).lastName(lastName).build();
    }

    @Test
    void findByLastName() {
        Owner returnedOwner = Owner.builder().id(id).lastName(lastName).build();
        when(ownerRepository.findByLastName(any())).thenReturn(returnedOwner);

        Owner smith = service.findByLastName(lastName);

        assertEquals(lastName, smith.getLastName());
        verify(ownerRepository).findByLastName(any());
    }

    @Test
    void findAll() {
        Set<Owner> mockOwners = new HashSet<>();
        mockOwners.add(Owner.builder().id(1L).build());
        mockOwners.add(Owner.builder().id(2L).build());

        when(ownerRepository.findAll()).thenReturn(mockOwners);

        Set<Owner> owners = service.findAll();

        assertEquals(2, owners.size());
    }

    @Test
    void findById() {
        when(ownerRepository.findById(anyLong())).thenReturn(Optional.of(mockOwner));

        Owner owner = service.findById(id);

        assertEquals(id, owner.getId());
    }

    @Test
    void save() {
        when(ownerRepository.save(any())).thenReturn(mockOwner);

        Owner owner = service.save(Owner.builder().id(id).build());

        assertEquals(id, owner.getId());
    }

    @Test
    void delete() {
        service.delete(mockOwner);

        verify(ownerRepository).delete(any());
    }

    @Test
    void deleteById() {
        service.deleteById(id);

        verify(ownerRepository).deleteById(anyLong());
    }
}