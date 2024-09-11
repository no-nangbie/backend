package com.nonangbie.config;

import com.nonangbie.auth.filter.JwtAuthenticationFilter;
import com.nonangbie.auth.filter.JwtVerificationFilter;
import com.nonangbie.auth.handler.MemberAccessDeniedHandler;
import com.nonangbie.auth.handler.MemberAuthenticationEntryPoint;
import com.nonangbie.auth.handler.MemberAuthenticationFailureHandler;
import com.nonangbie.auth.handler.MemberAuthenticationSuccessHandler;
import com.nonangbie.auth.jwt.JwtTokenizer;
import com.nonangbie.auth.utils.CustomAuthorityUtils;
import com.nonangbie.member.repository.MemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * authenticationEntryPoint와 accessDeniedHandler 추가
 */
@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfiguration {
    private final JwtTokenizer jwtTokenizer;
    private final CustomAuthorityUtils authorityUtils;
    private final MemberRepository memberRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    public SecurityConfiguration(JwtTokenizer jwtTokenizer,
                                 CustomAuthorityUtils authorityUtils, MemberRepository memberRepository, RedisTemplate<String, Object> redisTemplate) { //
        this.jwtTokenizer = jwtTokenizer;
        this.authorityUtils = authorityUtils;
        this.memberRepository = memberRepository;
        this.redisTemplate = redisTemplate;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .headers().frameOptions().disable() // H2 콘솔 사용을 위한 설정
                .and()
                .csrf().disable() // CSRF 비활성화 (개발 환경에서 주로 사용)
                .cors(withDefaults()) // CORS 설정 기본값 적용
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // JWT 사용으로 세션을 사용하지 않음
                .and()
                .formLogin().disable() // 폼 로그인을 비활성화
                .httpBasic().disable() // HTTP 기본 인증 비활성화
                .exceptionHandling()
                .authenticationEntryPoint(new MemberAuthenticationEntryPoint()) // 인증 실패 처리
                .accessDeniedHandler(new MemberAccessDeniedHandler()) // 접근 거부 처리
                .and()
                .apply(new CustomFilterConfigurer()) // JWT 필터 적용
                .and()
                .authorizeHttpRequests(authorize -> authorize
                        .antMatchers(HttpMethod.POST, "/auth/logout").hasAnyRole("USER", "ADMIN")
                        .anyRequest().permitAll() // 모든 요청을 허용 (추가 보안 필요 시 제한 가능)
                );
        return http.build();
    }
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//            .headers().frameOptions().sameOrigin()
//            .and()
//            .csrf().disable()
//            .cors(withDefaults())
//            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//            .and()
//            .formLogin().disable()
//            .httpBasic().disable()
//            .exceptionHandling()
//            .authenticationEntryPoint(new MemberAuthenticationEntryPoint())  // 추가
//            .accessDeniedHandler(new MemberAccessDeniedHandler())            // 추가
//            .and()
//            .apply(new CustomFilterConfigurer())
//            .and()
//            .authorizeHttpRequests(authorize -> authorize
//                    .antMatchers(HttpMethod.POST, "/*/members").permitAll()
//                    .antMatchers(HttpMethod.PATCH, "/*/members/**").hasRole("USER")
//                    .antMatchers(HttpMethod.GET, "/*/members").hasRole("ADMIN")
////                    .mvcMatchers(HttpMethod.GET, "/*/members").hasRole("ADMIN")
//                    .antMatchers(HttpMethod.GET, "/*/members/**").hasAnyRole("USER", "ADMIN")
//                    .antMatchers(HttpMethod.DELETE, "/*/members/**").hasRole("USER")
//                    .antMatchers(HttpMethod.POST, "/*/coffees").hasRole("ADMIN")
//                    .antMatchers(HttpMethod.PATCH, "/*/coffees/**").hasRole("ADMIN")
//                    .antMatchers(HttpMethod.GET, "/*/coffees/**").hasAnyRole("USER", "ADMIN")
//                    .antMatchers(HttpMethod.GET, "/*/coffees").permitAll()
//                    .antMatchers(HttpMethod.DELETE, "/*/coffees").hasRole("ADMIN")
//                    .antMatchers(HttpMethod.POST, "/*/orders").hasRole("USER")
//                    .antMatchers(HttpMethod.PATCH, "/*/orders").hasAnyRole("USER", "ADMIN")
//                    .antMatchers(HttpMethod.GET, "/*/orders/**").hasAnyRole("USER", "ADMIN")
//                    .antMatchers(HttpMethod.DELETE, "/*/orders").hasRole("USER")
//                    .anyRequest().permitAll()
//            );
//        return http.build();
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000", "http://127.0.0.1:3000"));  // 모든 오리진 허용
        configuration.setAllowedMethods(Arrays.asList("*"));  // 허용되는 HTTP 메서드
        configuration.setAllowedHeaders(Arrays.asList("*"));  // 허용되는 헤더g
        configuration.setExposedHeaders(Arrays.asList("Authorization", "memberId"));  // 노출할 헤더 추가
        configuration.setAllowCredentials(true);  // 인증 관련 정보를 허용

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


    public class CustomFilterConfigurer extends AbstractHttpConfigurer<CustomFilterConfigurer, HttpSecurity> {
        @Override
        public void configure(HttpSecurity builder) throws Exception {
            AuthenticationManager authenticationManager =
                    builder.getSharedObject(AuthenticationManager.class);

            JwtAuthenticationFilter jwtAuthenticationFilter =
                    new JwtAuthenticationFilter(authenticationManager,jwtTokenizer,memberRepository,passwordEncoder());
            jwtAuthenticationFilter.setFilterProcessesUrl("/login");
            jwtAuthenticationFilter.setAuthenticationSuccessHandler(
                    new MemberAuthenticationSuccessHandler(memberRepository));
            jwtAuthenticationFilter.setAuthenticationFailureHandler(
                    new MemberAuthenticationFailureHandler());

            JwtVerificationFilter jwtVerificationFilter =
                    new JwtVerificationFilter(jwtTokenizer,authorityUtils,redisTemplate);

            builder
                    .addFilter(jwtAuthenticationFilter)
                    .addFilterAfter(jwtVerificationFilter, JwtAuthenticationFilter.class);
        }

    }
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class).build();
    }
}
