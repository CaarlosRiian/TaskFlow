package com.taskflowproject.taskflow.service;

import com.taskflowproject.taskflow.dto.CreationRoleDTO;
import com.taskflowproject.taskflow.dto.RoleDTO;
import com.taskflowproject.taskflow.model.Role;
import com.taskflowproject.taskflow.repository.RoleRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public RoleDTO create(CreationRoleDTO dto) {
        if (roleRepository.existsByName(dto.name())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Função já existe");
        }
        Role role = new Role();
        role.setName(dto.name());
        return toDTO(roleRepository.save(role));
    }

    public List<RoleDTO> listAll() {
        return roleRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public RoleDTO getById(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Função não encontrada"));
        return toDTO(role);
    }

    public RoleDTO updateRole(Long id, @Valid CreationRoleDTO dto) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Função não encontrada"));

        if (roleRepository.existsByName(dto.name()) && !role.getName().equals(dto.name())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Função já existe");
        }

        role.setName(dto.name());
        return toDTO(roleRepository.save(role));
    }

    public void delete(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Função não encontrada"));
        roleRepository.delete(role);
    }

    private RoleDTO toDTO(Role role) {
        return new RoleDTO(role.getRoleId(), role.getName());
    }
}
