package com.feefo.note_web_app_web_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NoteWebAppWebServiceApplication {

//	@Autowired
//	private UserApplicationService userApplicationService;

	public static void main(String[] args) {
		SpringApplication.run(NoteWebAppWebServiceApplication.class, args);
	}

//	@PostConstruct
//	void init() {
//
//		User user = User.builder().name("john").password("1234").notes(List.of()).build();
//
//		userApplicationService.register(user);
//	}

}
