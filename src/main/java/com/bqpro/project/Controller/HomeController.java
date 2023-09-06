package com.bqpro.project.Controller;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public void redirectToIndex(HttpServletResponse response) throws IOException {
        // Redirigir a la página index.html
        response.sendRedirect("/swagger-ui/index.html");
    }
}
