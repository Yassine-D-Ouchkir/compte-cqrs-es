package ma.enset.comptecqrses.commonapi.events;

import lombok.Getter;
import ma.enset.comptecqrses.commonapi.enums.AccountStatus;

public class AccountCreatedEvent extends BaseEvent<String>{
    @Getter private double balance;
    @Getter private String currency;
    @Getter private AccountStatus status;
    public AccountCreatedEvent(String id, double balance, String currency, AccountStatus status) {
        super(id);
        this.balance = balance;
        this.currency = currency;
        this.status = status;
    }
}
