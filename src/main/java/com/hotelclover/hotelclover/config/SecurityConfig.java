package com.hotelclover.hotelclover.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.hotelclover.hotelclover.Models.MGestionDeClientes.Usuario;
import com.hotelclover.hotelclover.Repositories.MGestionDeClientes.ClientesRepository;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, ClientesRepository clientesRepository) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/css/**", "/js/**", "/images/**", "/api/clientes/registro-cliente-form",
                                "/api/clientes/registro-cliente")
                        .permitAll()
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/api/clientes/login")
                        .loginProcessingUrl("/api/clientes/login")
                        .successHandler(customSuccessHandler(clientesRepository)) // ← Aquí se activa el handler
                                                                                  // personalizado
                        .failureUrl("/api/clientes/login?error=true")
                        .permitAll())
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/api/clientes/login?logout=true")
                        .permitAll());

        return http.build();
    }

    @Bean
    public AuthenticationSuccessHandler customSuccessHandler(ClientesRepository clientesRepository) {
        return (request, response, authentication) -> {
            String email = authentication.getName();
            Usuario cliente = clientesRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

            request.getSession().setAttribute("cliente", cliente);

            switch (cliente.getTipoUsuario()) {
                case ADMINISTRADOR:
                case RECEPCIONISTA:
                    response.sendRedirect("/dashboardAdministrativo");
                    break;
                case CLIENTE:
                    response.sendRedirect("/dashboard");
                    break;
                default:
                    response.sendRedirect("/api/clientes/login?error=tipo");
            }
        };
    }
}