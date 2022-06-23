package rs.vegait.wolly.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

@RequiredArgsConstructor
public class JwtConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    private final TokenProvider tokenProvider;
    private final HandlerExceptionResolver handlerExceptionResolver;

    @Override
    public void configure(HttpSecurity http) {
        JwtRequestFilter customFilter = new JwtRequestFilter(tokenProvider, handlerExceptionResolver);
        http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
