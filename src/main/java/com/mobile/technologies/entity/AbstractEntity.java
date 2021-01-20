package com.mobile.technologies.entity;

import com.opencsv.bean.CsvBindByName;
import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Data
@MappedSuperclass
public class AbstractEntity implements Serializable {

  @Id
  @CsvBindByName(column = "ID_NUMBER")
  private String id;

  @CreationTimestamp
  private LocalDateTime createdAt;

  @UpdateTimestamp
  private LocalDateTime updatedAt;

}
