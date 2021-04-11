package ru.reybos.aeon.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.reybos.aeon.model.Person;
import ru.reybos.aeon.repository.PersonRepository;

import java.util.Optional;

import static java.util.Collections.emptyList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private PersonRepository personRepository;

    public UserDetailsServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<Person> userOpt = personRepository.findByLogin(login);
        if (userOpt.isEmpty()) {
            throw new UsernameNotFoundException(login);
        }
        Person user = userOpt.get();
        return new User(user.getLogin(), user.getPassword(), emptyList());
    }
}
