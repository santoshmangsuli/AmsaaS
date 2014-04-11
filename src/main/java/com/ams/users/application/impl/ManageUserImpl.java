package com.ams.users.application.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ams.users.application.api.ManageUser;
import com.ams.users.domain.model.Person;
import com.ams.users.domain.repository.PersonRepository;

@Transactional
@Service("ManageUserService")
public class ManageUserImpl implements ManageUser
{

	@Autowired
	private PersonRepository	personRepository;

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void registerUser(Person user)
	{
		this.personRepository.createOrUpdate(user);

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.REPEATABLE_READ)
	public void updateUserDetails(Person user)
	{
		this.personRepository.createOrUpdate(user);

	}

	@Override
	public void deleteUser(long userId)
	{
		this.personRepository.delete(userId);

	}

	@Override
	public Person getUserDetails(long userId)
	{
		Person user = this.personRepository.findById(userId);
		return user;
	}

	@Override
	public List<Person> getAllUsers()
	{
		List<Person> users = this.personRepository.findAll();
		return users;

	}

}
