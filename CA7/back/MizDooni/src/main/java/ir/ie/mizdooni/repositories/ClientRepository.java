package ir.ie.mizdooni.repositories;

import ir.ie.mizdooni.models.ClientUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<ClientUser, String> {
    ClientUser findByUsername(String username);
    ClientUser findByEmail(String email);
}
