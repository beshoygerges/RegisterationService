package com.mobile.technologies.service;

import com.mobile.technologies.entity.Customer;
import java.io.FileNotFoundException;

public interface CustomerService {

  void register(String filePath) throws FileNotFoundException;

  void register(Customer customer);
}
