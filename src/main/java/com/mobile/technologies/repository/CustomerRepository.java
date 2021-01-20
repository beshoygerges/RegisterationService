package com.mobile.technologies.repository;

import com.mobile.technologies.entity.Customer;

public interface CustomerRepository extends CrudRepository<Customer, String> {

  boolean existByMSISDN(String msisdn);

}
