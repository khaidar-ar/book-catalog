package com.project.book_catalog.security.util;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import jakarta.servlet.http.HttpServletRequest;

public class SkipPathRequestMathcer implements RequestMatcher {

    private OrRequestMatcher skip;

    private OrRequestMatcher proccess;

    
    public SkipPathRequestMathcer(List<String> pathToSkip, List<String> pathToProccess) {
            List<RequestMatcher> match = pathToSkip.stream().map(p->new AntPathRequestMatcher(p)).collect(Collectors.toList());
            this.skip = new OrRequestMatcher(match);
            List<RequestMatcher> matchProccess = pathToProccess.stream().map(p->new AntPathRequestMatcher(p)).collect(Collectors.toList());
            this.proccess = new OrRequestMatcher(matchProccess);

    }

    @Override
    public boolean matches(HttpServletRequest request) {
        if(skip.matches(request)) return false;
        return proccess.matches(request);
    }

}
