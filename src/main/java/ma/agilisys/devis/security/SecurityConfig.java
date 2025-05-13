package ma.agilisys.devis.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Collections;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final KeycloakRoleConverter keycloakRoleConverter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration cors1 = new CorsConfiguration();
                    cors1.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
                    cors1.setAllowedMethods(Collections.singletonList("*"));
                    cors1.setAllowedHeaders(Collections.singletonList("*"));
                    cors1.setExposedHeaders(Collections.singletonList("Authorization"));
                    return cors1;
                }))
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers(HttpMethod.GET, "/api/clients/**").hasAnyAuthority("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/clients").hasAnyAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/clients/**").hasAnyAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/clients/**").hasAnyAuthority("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/contacts").hasAnyAuthority("ADMIN", "USER")
                        .requestMatchers(HttpMethod.GET, "/api/contacts/client/**").hasAnyAuthority("ADMIN", "USER")
                        .requestMatchers(HttpMethod.GET, "/api/v1/devis/**").hasAnyAuthority("ADMIN", "USER")
                        .requestMatchers(HttpMethod.POST, "/api/v1/devis").hasAnyAuthority("ADMIN", "USER")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/devis/**").hasAnyAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/devis/**").hasAnyAuthority("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/v1/devis/**/duplicate").hasAnyAuthority("ADMIN", "USER")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/devis/**/validate").hasAnyAuthority("ADMIN")
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html"
                        ).permitAll()
                        .anyRequest().authenticated())
                //  .oauth2ResourceServer(rs -> rs.jwt(Customizer.withDefaults()));
                .oauth2ResourceServer(rs -> rs.jwt(jwt ->
                        jwt.jwtAuthenticationConverter(keycloakRoleConverter)));
        return http.build();
    }
}
