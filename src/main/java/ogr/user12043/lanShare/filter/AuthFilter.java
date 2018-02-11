package ogr.user12043.lanShare.filter;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by user12043 on 2/8/18
 * part of project lanShare
 */

@WebFilter(filterName = "AuthFilter", urlPatterns = {"/*"})
public class AuthFilter extends HttpFilter {
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        // TODO: Add authentication here
        chain.doFilter(request, response);
    }

    public void destroy() {

    }
}
