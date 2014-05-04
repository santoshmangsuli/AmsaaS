package com.ams.users.ports.adapter.persistance;

import com.ams.sharedkernel.domain.repository.Page;
import com.ams.users.domain.model.Person;
import com.ams.users.domain.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository("PersonRepository")
public class PersonRepositoryImpl implements PersonRepository {
    @Autowired
    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;

    public Long create(Person person) {
        entityManager.persist(person);
        return person.getPersnId();
    }

    @Override
    public Long createOrUpdate(Person persn) {
        if (persn.getPersnId() == null) {
            entityManager.persist(persn);
        } else {
            entityManager.merge(persn);
        }

        return persn.getPersnId();
    }

    @Override
    public void delete(Long personId) {
        Person p = entityManager.find(Person.class, personId);
        entityManager.remove(p);
    }

    @Override
    public List<Person> findAll() {
        TypedQuery<Person> query = entityManager.createQuery("select P from Person P", Person.class);
        return query.getResultList();
    }

    @Override
    public List<Person> findAllByCriteria(String... criterias) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Person> findAllByIds(long[] userIds) {
        return null;
    }

    @Override
    public Person findById(Long personId) {
        return entityManager.find(Person.class, personId);
    }

    @Override
    public Page<Person> findNextPageData(Page<Person> page) {
        // TODO Auto-generated method stub
        return null;
    }

    public Long update(Person person) {
        entityManager.merge(person);
        return person.getPersnId();
    }

}
