package com.example.demo.web.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
public class CorrelationIdFilter implements Filter {
  private static final String CORRELATION_ID_HEADER_NAME = "X-Correlation-Id";
  private static final String MDC_CORRELATION_ID_KEY = "correlationId";

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    HttpServletRequest httpRequest = (HttpServletRequest) request;
    String correlationId = httpRequest.getHeader(CORRELATION_ID_HEADER_NAME);
    if (correlationId == null || correlationId.isEmpty()) {
      correlationId = UUID.randomUUID().toString();
    }
    MDC.put(MDC_CORRELATION_ID_KEY, correlationId);
    try {
      chain.doFilter(request, response);
    } finally {
      MDC.remove(MDC_CORRELATION_ID_KEY);
    }
  }
}
