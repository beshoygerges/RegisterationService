package com.mobile.technologies.service.impl;

import com.mobile.technologies.annotations.Loggable;
import com.mobile.technologies.constants.AppConstants;
import com.mobile.technologies.constants.Status;
import com.mobile.technologies.entity.Customer;
import com.mobile.technologies.exception.EmptyFieldsException;
import com.mobile.technologies.exception.InvalidAddressException;
import com.mobile.technologies.exception.InvalidBirthdateException;
import com.mobile.technologies.exception.InvalidIDException;
import com.mobile.technologies.exception.InvalidMSISDNException;
import com.mobile.technologies.exception.InvalidNameException;
import com.mobile.technologies.exception.MSISDNAlreadyExistException;
import com.mobile.technologies.log.LogEvent;
import com.mobile.technologies.repository.CustomerRepository;
import com.mobile.technologies.service.CustomerService;
import com.mobile.technologies.util.JSONUtil;
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@AllArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {

  private final CustomerRepository customerRepository;

  @Loggable
  @Transactional
  @Override
  public void register(String filePath) throws FileNotFoundException {
    List<Customer> customers = loadCustomersCSVFile(filePath);
    customers.forEach(this::register);
  }

  private List<Customer> loadCustomersCSVFile(String filePath) throws FileNotFoundException {
    return new CsvToBeanBuilder(new FileReader(filePath))
        .withType(Customer.class)
        .build()
        .parse();
  }

  @Override
  public void register(Customer customer) {
    final LogEvent logEvent = new LogEvent(customer);
    try {
      validate(customer);
      save(customer);
      sendSms(customer.getMsisdn());
      writeFile(customer);
    } catch (RuntimeException e) {
      logEvent.setStatus(Status.FAILED);
      logEvent.setReason(e.getMessage());
    } finally {
      log.info("\n" + JSONUtil.toJson(logEvent));
    }

  }

  @SneakyThrows
  private void writeFile(Customer customer) {
    Files.createDirectories(Paths.get("customers"));
    Files.write(Paths.get("customers/" + customer.getMsisdn() + ".txt"),
        JSONUtil.toJson(customer).getBytes());
  }

  private void validate(Customer customer) {
    if (hasEmptyFields(customer)) {
      throw new EmptyFieldsException("Request has empty fields");
    }

    if (!isValidName(customer.getName())) {
      throw new InvalidNameException("Invalid customer name");
    }

    if (!isValidBirthDate(customer.getBirthdate())) {
      throw new InvalidBirthdateException("Invalid birthdate");
    }

    if (!isValidAddress(customer.getAddress())) {
      throw new InvalidAddressException("Invalid Address");
    }

    if (!isValidIdNumber(customer.getId())) {
      throw new InvalidIDException("Invalid ID number");
    }

    if (isIdNumberExist(customer.getId())) {
      throw new InvalidIDException("ID is already exist");
    }

    if (!isValidMSISDN(customer.getMsisdn())) {
      throw new InvalidMSISDNException("Invalid MSISDN");
    }

    if (isMSISDNExist(customer.getMsisdn())) {
      throw new MSISDNAlreadyExistException("MSISDN is already exist");
    }

  }

  private boolean isIdNumberExist(String id) {
    return customerRepository.existById(id);
  }

  private boolean isMSISDNExist(String msisdn) {
    return customerRepository.existByMSISDN(msisdn);
  }

  private boolean isValidMSISDN(String msisdn) {
    return Pattern.matches(AppConstants.MSISDN_REGEX, msisdn);
  }

  private boolean isValidIdNumber(String id) {
    return Pattern.matches(AppConstants.ID_REGEX, id);
  }

  private boolean isValidAddress(String address) {
    return address.length() >= 20;
  }

  private boolean isValidBirthDate(LocalDate birthdate) {
    return birthdate.isBefore(LocalDate.now());
  }

  private boolean hasEmptyFields(Customer customer) {
    if (StringUtils.isEmpty(customer.getId())) {
      return true;
    }

    if (StringUtils.isEmpty(customer.getMsisdn())) {
      return true;
    }

    if (StringUtils.isEmpty(customer.getAddress())) {
      return true;
    }

    if (Objects.isNull(customer.getGender())) {
      return true;
    }

    if (Objects.isNull(customer.getSimType())) {
      return true;
    }

    return Objects.isNull(customer.getBirthdate());
  }

  private boolean isValidName(String name) {
    return Pattern.matches(AppConstants.NAME_REGEX, name);
  }

  private void save(Customer customer) {
    customerRepository.save(customer);
  }

  private void sendSms(String msisdn) {
    log.info("SMS was sent to " + msisdn);
  }


}
