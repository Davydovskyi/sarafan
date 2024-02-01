package edu.jcourse.sarafan.config;

import edu.jcourse.sarafan.dto.UserDto;
import edu.jcourse.sarafan.entity.Gender;
import edu.jcourse.sarafan.entity.Role;
import edu.jcourse.sarafan.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.AnonymousConfigurer;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.SecurityFilterChain;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final UserService userService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .anonymous(AnonymousConfigurer::disable)
                .authorizeHttpRequests(urlConfig -> urlConfig
                        .requestMatchers("/", "/login**", "/css/**", "/js/**", "/error**").permitAll()
                        .anyRequest().authenticated())
                .oauth2Login(oauth2Login -> oauth2Login
                        .userInfoEndpoint(userInfo ->
                                userInfo.oidcUserService(oidcUserService())))
                .logout(logout -> logout
                        .logoutSuccessUrl("/").permitAll());
        return http.build();
    }

    private OAuth2UserService<OidcUserRequest, OidcUser> oidcUserService() {
        return request -> {
            String id = request.getIdToken().getSubject();

            UserDto userDto = userService.findById(id)
                    .orElseGet(() -> userService.create(createUserDto(request.getIdToken())));

            OidcUserInfo userInfo = new OidcUserInfo(Map.of("user", userDto));

            return new DefaultOidcUser(Collections.singleton(userDto.getRole()), request.getIdToken(), userInfo);
        };
    }

    private UserDto createUserDto(OidcIdToken token) {
        return UserDto.builder()
                .id(token.getSubject())
                .email(token.getEmail())
                .name(token.getFullName())
                .gender(Gender.find(token.getGender()))
                .locale(token.getLocale())
                .role(Role.USER)
                .userPic(token.getPicture())
                .lastVisit(LocalDateTime.now())
                .build();
    }
}