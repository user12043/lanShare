package ogr.user12043.lanShare.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * Created by user12043 on 2/8/18
 * part of project lanShare
 */

public class AuthFilter implements Filter {
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // TODO: Add authentication here
        chain.doFilter(request, response);
    }

    public void destroy() {

    }
}
