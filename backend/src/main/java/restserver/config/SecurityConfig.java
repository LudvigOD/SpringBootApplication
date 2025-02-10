package restserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  /*
   * It took a long time to figure out how to get this to work. There is now a
   * user and admin. This is set up for testing and should not be used in real
   * applications.
   */

  // @Bean
  // public UserDetailsService users() {
  // UserBuilder users = User.withDefaultPasswordEncoder();
  // UserDetails user = users.username("user").password("password").roles("USER")
  // .build();
  // UserDetails admin = users.username("admin").password("password")
  // .roles("ADMIN", "USER").build();

  // // We can set up a database for users and roles like this:
  // // JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
  // // users.createUser(user);
  // // users.createUser(admin);
  // // return users;

  // // Or we can set up users and roles in memory like this:
  // return new InMemoryUserDetailsManager(user, admin);
  // }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http)
      throws Exception {

    // The config below is a mess. I don't know what I'm doing. I just copied
    // and pasted from the internet until it worked. I'm sure there is a better
    // way to do this. This is my understanding so far:

    // CORS is Cross-Origin Resource Sharing. It is a security feature that
    // prevents a web page from one domain from accessing resources on another
    // domain. For example, if you have a web page on localhost:8080, it will
    // not be able to access resources on localhost:3000. This is a problem
    // if we have a frontend and backend on different ports.

    // CSRF is Cross-Site Request Forgery. It is a security feature that
    // prevents a malicious website from sending requests to another website
    // that the user is logged into. For example, if you are logged into
    // facebook.com and you visit a malicious website, the malicious website
    // will not be able to send requests to facebook.com on your behalf.
    // This is needed, I think, by the h2-console.

    // The authorizeHttpRequests anyRequest().permitAll() should permit all
    // requests to all endpoints, i.e. no authorization at all. So, in real use
    // it should be removed. Right now, we can leave it on. So, the other
    // authorizeHttpRequests permit statements are not really needed.

    // Just FYI, a lot of resources I found on the internet used older versions
    // of Spring Security. The syntax has changed a bit. Methods often use
    // lambdas now instead of chaining on chaining on chainihg...
    // For example, instead of this:
    // http.cors().disable()
    // we now use this:
    // http.cors(cors -> cors.disable())
    http.cors(cors -> cors.disable())
        // .cors(cors -> cors.configurationSource(request -> {
        // CorsConfiguration config = new CorsConfiguration();
        // config.setAllowedOrigins(
        // Arrays.asList("http://localhost:8080", "http://localhost:3000")
        // );
        // config.setAllowedMethods(
        // Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS")
        // );
        // return config;
        // }))
        .authorizeHttpRequests(
            auth -> auth //
                // For matchers, the newer suggested way is to use
                // .requestMatchers("pattern") and let Spring work out what kind
                // of matcher it is. But, I couldn't get that to work. So, I'm
                // using the older way, which is to explicitly specify the type
                // of matcher.
                // .requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**"))
                // .permitAll()
                // .requestMatchers(AntPathRequestMatcher.antMatcher("/admin/**"))
                // .hasRole("ADMIN") //
                .anyRequest().permitAll()) //
        // .headers(headers -> headers.frameOptions(opt -> opt.disable()))
        // .csrf(
        // csrf -> csrf.ignoringRequestMatchers(
        // AntPathRequestMatcher.antMatcher("/h2-console/**"))) //
        .csrf(csrf -> csrf.disable()) //
    // .formLogin(Customizer.withDefaults())
    ;
    return http.build();
  }

}
