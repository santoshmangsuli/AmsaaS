package com.ams.users.application.impl;

import com.ams.sharedkernel.domain.events.DomainEventPublisher;
import com.ams.users.application.api.ManageUser;
import com.ams.users.domain.model.Person;
import com.ams.users.domain.model.events.UserCreatedEvent;
import com.ams.users.domain.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service("ManageUserService")
public class ManageUserImpl implements ManageUser {

    @Autowired
    private PersonRepository personRepository;

    private DomainEventPublisher domainEventPublisher;

    @Override
    public void deleteUser(long userId) {
        this.personRepository.delete(userId);

    }

    @Override
    public List<Person> getAllUsers() {
        List<Person> users = this.personRepository.findAll();
        return users;

    }

    @Override
    public Person getUserDetails(long userId) {
        Person user = this.personRepository.findById(userId);
        return user;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void registerUser(Person user) {
        this.personRepository.createOrUpdate(user);
        this.domainEventPublisher.raiseEvent(new UserCreatedEvent(user));

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public void updateUserDetails(Person user) {
        this.personRepository.createOrUpdate(user);

    }

}
