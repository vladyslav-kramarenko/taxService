package com.my.kramarenko.taxService.web.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * Encoding filter.
 * 
 * @author Vlad Kramarenko
 */
public class EncodingFilter implements Filter {

	private static final Logger LOG = Logger.getLogger(EncodingFilter.class);

	private String encoding;

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		LOG.trace("Filter starts");
		
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		LOG.trace("Request uri --> " + httpRequest.getRequestURI());


		String requestEncoding = request.getCharacterEncoding();
		if (requestEncoding == null) {
			LOG.debug("Request encoding = null, set encoding --> " + encoding);
			request.setCharacterEncoding(encoding);
		}
		
		LOG.trace("Filter finished");
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
		LOG.trace("Filter initialization starts");
		encoding = fConfig.getInitParameter("encoding");
		LOG.debug("Encoding from web.xml --> " + encoding);
		LOG.trace("Filter initialization finished");
	}

}