package ma.enset.comptecqrses.quary.Controllers;

import lombok.AllArgsConstructor;
import ma.enset.comptecqrses.commonapi.queries.GetAccountQuery;
import ma.enset.comptecqrses.commonapi.queries.GetAllAccountQuery;
import ma.enset.comptecqrses.quary.entites.Account;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/query/accounts")
@AllArgsConstructor
public class AccountQueryController {
    private QueryGateway queryGateway;
    @GetMapping("/allAccounts")
    public List<Account> accountList(){
        return queryGateway.query(new GetAllAccountQuery(), ResponseTypes.multipleInstancesOf(Account.class)).join();
    }
    @GetMapping("/{id}")
    public Account getAccount(@PathVariable(name = "id") String id){
        return queryGateway.query(new GetAccountQuery(id),ResponseTypes.instanceOf(Account.class)).join();
    }

}
