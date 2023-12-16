package ma.enset.comptecqrses.quary.repositories;

import ma.enset.comptecqrses.quary.entites.Account;
import ma.enset.comptecqrses.quary.entites.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationRepository extends JpaRepository<Operation,Long> {
}
