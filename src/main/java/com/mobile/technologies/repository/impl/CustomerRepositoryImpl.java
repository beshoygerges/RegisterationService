package com.mobile.technologies.repository.impl;

import com.mobile.technologies.entity.Customer;
import com.mobile.technologies.repository.CustomerRepository;
import java.util.List;
import java.util.Optional;
import javax.persistence.NoResultException;
import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

@AllArgsConstructor
@Repository
public class CustomerRepositoryImpl implements CustomerRepository {

  private final SessionFactory sessionFactory;

  @Override
  public Optional<Customer> findById(String id) {
    Query<Customer> query = getSession().createQuery("FROM Customer c where c.id=:id");
    query.setParameter("id", id);
    try {
      return Optional.of(query.getSingleResult());
    } catch (NoResultException e) {
      return Optional.empty();
    }
  }

  @Override
  public List<Customer> findAll() {
    return getSession().createQuery("FROM Customer").getResultList();
  }

  @Override
  public void save(Customer customer) {
    getSession().persist(customer);
  }

  private Session getSession() {
    return sessionFactory.getCurrentSession();
  }

  @Override
  public boolean existByMSISDN(String msisdn) {
    Query<Customer> query = getSession()
        .createQuery("FROM Customer c WHERE c.msisdn=:msisdn");
    query.setParameter("msisdn", msisdn);
    try {
      query.getSingleResult();
      return true;
    } catch (NoResultException e) {
      return false;
    }
  }

  @Override
  public boolean existById(String id) {
    return findById(id).isPresent();
  }
}
