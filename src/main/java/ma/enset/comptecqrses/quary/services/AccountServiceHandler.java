package ma.enset.comptecqrses.quary.services;

import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.enset.comptecqrses.commonapi.events.AccountActivatedEvent;
import ma.enset.comptecqrses.commonapi.events.AccountCreatedEvent;
import ma.enset.comptecqrses.commonapi.events.AccountCreditedEvent;
import ma.enset.comptecqrses.commonapi.events.AccountDebitedEvent;
import ma.enset.comptecqrses.commonapi.queries.GetAccountQuery;
import ma.enset.comptecqrses.commonapi.queries.GetAllAccountQuery;
import ma.enset.comptecqrses.quary.entites.Account;
import ma.enset.comptecqrses.quary.entites.Operation;
import ma.enset.comptecqrses.quary.enums.OperationType;
import ma.enset.comptecqrses.quary.repositories.AccountRepository;
import ma.enset.comptecqrses.quary.repositories.OperationRepository;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service @AllArgsConstructor @Slf4j @Transactional
public class AccountServiceHandler {
    private AccountRepository accountRepository;
    private OperationRepository operationRepository;
    @EventHandler
    public void on(AccountCreatedEvent event){
        log.info("************************************************");
        log.info("AccountCreatedEvent : Received");
        Account account=new Account();
        account.setId(event.getId());
        account.setBalance(event.getBalance());
        account.setCurrency(event.getCurrency());
        account.setStatus(event.getStatus());
        accountRepository.save(account);
    }
    @EventHandler
    public void on(AccountActivatedEvent event){
        log.info("************************************************");
        log.info("AccountActivatedEvent : Received");
        Account account=accountRepository.findById(event.getId()).get();
        account.setStatus(event.getStatus());
        accountRepository.save(account);
    }
    @EventHandler
    public void on(AccountDebitedEvent event){
        Account account=accountRepository.findById(event.getId()).get();
        Operation operation=new Operation();
        operation.setDate(new Date());
        operation.setType(OperationType.DEBIT);
        operation.setAmount(event.getAmount());
        operationRepository.save(operation);
        account.setBalance(account.getBalance()-event.getAmount());
        accountRepository.save(account);
    }

    @EventHandler
    public void on(AccountCreditedEvent event){
        Account account=accountRepository.findById(event.getId()).get();
        Operation operation=new Operation();
        operation.setDate(new Date());
        operation.setType(OperationType.CREDIT);
        operation.setAmount(event.getAmount());
        operationRepository.save(operation);
        account.setBalance(account.getBalance()+event.getAmount());
        accountRepository.save(account);
    }
    @QueryHandler
    public List<Account> on(GetAllAccountQuery query){
        return accountRepository.findAll();
    }
    @QueryHandler
    public Account on(GetAccountQuery query){
        return accountRepository.findById(query.getId()).get();
    }

}
