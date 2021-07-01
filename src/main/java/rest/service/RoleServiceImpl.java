package rest.service;


import org.springframework.stereotype.Service;
import rest.dao.RoleRepository;
import rest.model.Role;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService{
    private RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
}
