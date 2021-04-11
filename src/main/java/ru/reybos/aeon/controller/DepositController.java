package ru.reybos.aeon.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.reybos.aeon.model.Deposit;
import ru.reybos.aeon.model.Person;
import ru.reybos.aeon.service.DepositService;
import ru.reybos.aeon.service.PersonService;

import java.util.Optional;

@RestController
@RequestMapping("/payment")
public class DepositController {
    private PersonService personService;
    private DepositService depositService;

    public DepositController(PersonService personService, DepositService depositService) {
        this.personService = personService;
        this.depositService = depositService;
    }

    @PutMapping("/")
    public ResponseEntity<Void> update() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String login = auth.getName();
        synchronized (this) {
            Optional<Person> personOpt = personService.findByLogin(login);
            if (personOpt.isEmpty()) {
                throw new UsernameNotFoundException("Пользователь не найден");
            }
            Person person = personOpt.get();
            depositService.reduceBalance(person.getDeposit().getId(), Deposit.REDUCE_VALUE);
        }
        return ResponseEntity.ok().build();
    }
}
