package com.kosmin.project.data_relation.aspect;

import com.kosmin.project.data_relation.model.Response;
import com.kosmin.project.data_relation.util.ResponseEntityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class DataRelationServiceHandler {

  @Around("execution(* com.kosmin.project.data_relation.service.DataRelationService.*(..))")
  public Object handleServiceMethod(ProceedingJoinPoint joinPoint) throws Throwable {
    try {
      return joinPoint.proceed();
    } catch (Exception e) {
      return handleException(e);
    }
  }

  private ResponseEntity<Response> handleException(Exception e) {
    log.error(e.getMessage());
    return ResponseEntityUtil.internalServerErrorResponse(e.getMessage());
  }
}
