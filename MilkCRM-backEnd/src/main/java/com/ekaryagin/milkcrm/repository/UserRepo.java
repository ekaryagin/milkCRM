package com.ekaryagin.milkcrm.repository;

import com.ekaryagin.milkcrm.entity.Region;
import com.ekaryagin.milkcrm.entity.employee.Role;
import com.ekaryagin.milkcrm.entity.employee.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepo extends JpaRepository<User, Long> {

    List<User> findAllByRole(Role role);

    List<User> findAllByRegions(Region region);

    List<User> findAllByRegionsAndRole(Region region, Role role);

    User findById(long id);

    User findByNic(String nic);
}
