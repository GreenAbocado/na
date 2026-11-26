package com.na.project.global.init;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

// 로컬 테스트 데이터 삽입용

@Component
@Profile("local")
public class LocalDataInitializer implements CommandLineRunner {
	@Override
	public void run(String... args) throws Exception {

	}
}
