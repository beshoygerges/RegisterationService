package com.mobile.technologies.log;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mobile.technologies.constants.Status;
import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class LogEvent implements Serializable {

  private Object request;

  private Status status = Status.SUCCESS;

  private String reason = "Request processed successfully";

  public LogEvent(Object request) {
    this.request = request;
  }
}
