package ir.ie.mizdooni.repositories;

import ir.ie.mizdooni.models.ManagerUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagerRepository extends JpaRepository<ManagerUser, String> {
    ManagerUser findByUsername(String username);
    ManagerUser findByEmail(String email);
}