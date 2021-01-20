package com.mobile.technologies.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mobile.technologies.constants.Gender;
import com.mobile.technologies.constants.SIMType;
import com.mobile.technologies.util.LocalDateSerializer;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@JsonInclude(Include.NON_NULL)
public class Customer extends AbstractEntity {

  @CsvBindByName(column = "NAME")
  @Column(nullable = false)
  private String name;

  @JsonSerialize(using = LocalDateSerializer.class)
  @CsvDate(value = "yyyy-MM-dd")
  @CsvBindByName(column = "DATE_OF_BIRTH")
  @Column(nullable = false)
  private LocalDate birthdate;

  @CsvBindByName(column = "MSISDN")
  @Column(nullable = false, unique = true)
  private String msisdn;

  @CsvBindByName(column = "ADDRESS")
  @Column(nullable = false)
  private String address;

  @CsvBindByName(column = "GENDER")
  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Gender gender;

  @CsvBindByName(column = "SIM_TYPE")
  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private SIMType simType;

  @Override
  public String toString() {
    return "Customer{" +
        "id='" + getId() + '\'' +
        ",  name='" + name + '\'' +
        ", birthdate=" + birthdate +
        ", simType=" + simType +
        ", msisdn='" + msisdn + '\'' +
        ", address='" + address + '\'' +
        ", gender=" + gender +
        '}';
  }
}
