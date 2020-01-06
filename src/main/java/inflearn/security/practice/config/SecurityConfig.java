package inflearn.security.practice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	// WebSecurityConfigurerAdapter 상속받는 순간 SpringBoot가 제공해주는 SpringbootAutoConfiguration은 사용 안됨
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()// 인증을 어떻게 보안을 걸 것이냐에 대한 설정
			.antMatchers("/","/create").permitAll()
			.antMatchers("/admin/**").hasRole("ADMIN")
			.anyRequest().authenticated() //여기서 걸리면 filter bean이 가로채서 form로그인 설정페이지로 보낸다.
			.and()
		.formLogin()
			.loginPage("/login") //로그인 페이지 이동
			.permitAll() //formLogin에 대한 인증 허용
			.and()
		.logout()
			.logoutSuccessUrl("/")
			.permitAll();//logout에 대한 인증 허용
	}
	
	/*// 아무런 인코딩을 하지 않는 방법 절대 사용 x
	 * @Bean public PasswordEncoder passwordEncoder() { return
	 * NoOpPasswordEncoder.getInstance(); }
	 */
	
	// passwordEncoder를 사용하지 않으면 에러 발생
	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
}
