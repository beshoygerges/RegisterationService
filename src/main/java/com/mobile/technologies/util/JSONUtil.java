package com.mobile.technologies.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class JSONUtil {

  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  private JSONUtil() {

  }

  public static String toJson(Object object) {
    try {
      return OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(object);
    } catch (JsonProcessingException e) {
      return "";
    }
  }

}
