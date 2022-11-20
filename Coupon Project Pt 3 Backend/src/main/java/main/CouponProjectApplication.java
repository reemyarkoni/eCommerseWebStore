package main;

import main.Beans.*;
import main.Exceptions.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class CouponProjectApplication {


	public static void main(String[] args) throws IncorrectCredentialsException, NotFoundException {
		ConfigurableApplicationContext ctx = SpringApplication.run(CouponProjectApplication.class, args);
		try{
			Thread SessionHandler = new Thread(ctx.getBean(main.Services.SessionHandler.class));
			Thread DailyJob = new Thread(ctx.getBean(main.Services.DailyJob.class));
//			SessionHandler.start();
//			DailyJob.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Bean
	public Map<String, Session> sessionPack(){
		return new HashMap<String, Session>();
	}
}
