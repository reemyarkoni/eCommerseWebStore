package main.Repositories;

import main.Beans.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Integer> {

    boolean existsByName(String name);

    boolean existsByEmail(String email);

    Optional<Company> findByEmailAndPassword(String email, String password);
}
