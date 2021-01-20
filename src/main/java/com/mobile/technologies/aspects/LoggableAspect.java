package com.mobile.technologies.aspects;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Aspect
public class LoggableAspect {

  @Around(" @annotation(com.mobile.technologies.annotations.Loggable)")
  public void validateAspect(final ProceedingJoinPoint pjp) throws Throwable {
    final Date start = new Date();
    try {
      pjp.proceed();
    } finally {
      final Date end = new Date();
      log.info(calculateDuration(start, end));
    }

  }

  private String calculateDuration(final Date start, final Date end) {
    final long timeElapsed = end.getTime() - start.getTime();
    final long minutes = MILLISECONDS.toMinutes(timeElapsed);
    final long seconds = MILLISECONDS.toSeconds(timeElapsed) % 60;
    final long milliseconds = timeElapsed % 1000;
    final String duration = String.format("%02d:%02d.%03d", minutes, seconds, milliseconds);
    return " duration is " + duration;
  }

}
