package com.api.restmusicservice.config;

import java.io.IOException;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;


/**
 * Класс реализует фильтр для обработки CORS (Cross-Origin Resource Sharing).
 * Этот фильтр используется для управления настройками CORS для HTTP-запросов в Spring-приложении.
 *
 */
@Component
public class CORSFilter implements Filter {

    /**
     * Метод {@code doFilter} обрабатывает входящие запросы и устанавливает необходимые заголовки для CORS.
     *
     * @param req запрос от клиента
     * @param res ответ для клиента
     * @param chain цепочка фильтров, через которую проходит запрос
     * @throws IOException если происходит ошибка ввода-вывода
     * @throws ServletException если происходит ошибка на уровне сервлета
     */
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        response.setHeader("Access-Control-Allow-Origin", "*"); // доступ к API для всех //TODO сделать ограниченным
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
        chain.doFilter(req, res);
    }

    public void init(FilterConfig filterConfig) {}

    public void destroy() {}

}