package ma.enset.comptecqrses.commands.controllers;

import lombok.AllArgsConstructor;
import ma.enset.comptecqrses.commonapi.commands.CreateAccountCommand;
import ma.enset.comptecqrses.commonapi.commands.CreditAccountCommand;
import ma.enset.comptecqrses.commonapi.commands.DebitAccountCommand;
import ma.enset.comptecqrses.commonapi.dtos.CreateAccountRequestDTO;
import ma.enset.comptecqrses.commonapi.dtos.CreditAccountRequestDTO;
import ma.enset.comptecqrses.commonapi.dtos.DebitAccountRequestDTO;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@RestController
@RequestMapping(path = "/commands/account")
@AllArgsConstructor
public class AccountCommandController {
    private CommandGateway commandGateway;
    private EventStore eventStore;
    @PostMapping(path="/create")
    public CompletableFuture<String> createAccount(@RequestBody CreateAccountRequestDTO request){
        CompletableFuture<String> commandeResponse=commandGateway.send(new CreateAccountCommand(
                UUID.randomUUID().toString(),
                request.getInitialeBalance(),
                request.getCurrancy()
        ));
        return commandeResponse;
    }
    @PutMapping(path="/debit")
    public CompletableFuture<String> debitAccount(@RequestBody DebitAccountRequestDTO request){
        CompletableFuture<String> commandeResponse=commandGateway.send(new DebitAccountCommand(
                request.getId(),
                request.getAmount(),
                request.getCurrency()
        ));
        return commandeResponse;
    }
    @PutMapping(path="/credit")
    public CompletableFuture<String> creditAccount(@RequestBody CreditAccountRequestDTO request){
        CompletableFuture<String> commandeResponse=commandGateway.send(new CreditAccountCommand(
                request.getId(),
                request.getAmount(),
                request.getCurrency()
        ));
        return commandeResponse;
    }
    @GetMapping("/{AccountId}")
    public Stream showEvent(@PathVariable(name="AccountId") String id){
        return eventStore.readEvents(id).asStream();
    }
}

