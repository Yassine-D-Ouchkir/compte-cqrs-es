package ma.enset.comptecqrses.quary.repositories;

import ma.enset.comptecqrses.quary.entites.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account,String> {
}
