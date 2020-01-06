# practice-spring-security
스프링 시큐리티 인프런 강의 복습입니다.

## security 및 thymeleaf 등 의존성 추가
	
## SecurityConfig라는 WebSecurityConfigurerAdapter를 상속받는 환경설정 클래스 생성
	 * WebSecurityConfigurerAdapter 상속받는 순간 WebSecurityConfigurerAdapter타입의 빈을 등록했기 때문에 SpringBoot가 제공해주는 SpringbootAutoConfiguration은 사용 안됨
	 
## configure 메소드 오버라이딩
	   http.authorizeRequests()
			.antMatchers("/","/hello").permitAll()
			.anyRequest().authenticated()
			.and()
		.formLogin()
			.and()
		.httpBasic();
	-> 예제에서는 /, /hello 에 대한 요청은 모두 허용하고 그 외 다른 요청은 인증이 필요함, formLogin을 사용하고 httpBasic도 사용할 것이다. 
	-> accept 값에 html이 있으니 자동으로 formlogin 화면이  보일 것이다. 
	 * WebSecurityConfigurerAdapter의 디폴트 설정 http
			.authorizeRequests()
				.anyRequest().authenticated()
				.and()
			.formLogin().and()
			.httpBasic();
			
## UserDetailService 커스터마이징
	 * https://docs.spring.io/spring-security/site/docs/current/reference/htmlsingle/#jc-authentication-userdetailsservice
     * 보통 서비스 클래스 계층에서 UserDetailsService 인터페이스를 구현한다. 사실 위치는 상관없고, UserDetailsService 타입이 빈으로 등록되기만 한다.
	 - loadUserByUsername: 로그인 form에서 입력된 username을 확인하고 같이 입력된 password가 일치할 경우 성공 아니면 실패 처리
	 @Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<Account> byUsername = accountRepository.findByUsername(username);
		Account account = byUsername.orElseThrow(() -> new UsernameNotFoundException(username)); // byUsername 실제 데이터가 없으면 UsernameNotFoundException, 있으면 실제 account값을 return 
		
		return new User(account.getUsername(), account.getPassword(), authorities()); // 스프링 시큐리티가 기본적으로 제공하는 UserDetails 타입의 객체 , UserDetails타입으로 변환해준다. 
	}

	private Collection<? extends GrantedAuthority> authorities() {
		return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
	}
	* 단 password 인코딩이 안되어있을 경우 There is no PasswordEncoder mapped for the id "null" 라는 에러가 뜬다. 
	
## PasswordEncoder 설정 및 사용
	 - password에 아무런 인코딩이 되어 있지 않다면 심각한 문제가 생길 수 있다. 반드시 인코딩 필요 
	 * https://docs.spring.io/spring-security/site/docs/current/reference/htmlsingle/#core-services-password-encoding
	  * 스프링 시큐리티가 제공하는 아무런 인코딩을 하지 않는 방법으로 "noop" 이 존재, 절대 권하지 x 
	 - PasswordEncoder passwordEncoder =
    PasswordEncoderFactories.createDelegatingPasswordEncoder(); 을 권장

    * 이 외에 csrf, oauth 인증방식 등 아직 많은 것을 추가해야함.
	
	* 보통 환경설정 파일에 @EnbaleWebSecurity 을 선언 
	 -> 스프링 부트가 제공하는 스프링 시큐리티 자동 설정은 무시함, @EnbaleWebSecurity 사용하지 않고 WebSecurityConfigurationAdapter를 상속받으면 자동설정 + 커스터마이징 기능이 됨. 강사님은 @EnbaleWebSecurity을 선호
