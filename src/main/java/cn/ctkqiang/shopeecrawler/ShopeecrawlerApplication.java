package cn.ctkqiang.shopeecrawler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import cn.ctkqiang.shopeecrawler.Constants.Names;

@SpringBootApplication
public class ShopeecrawlerApplication {

	private static final String APPLICATION_NAME = Names.APPLICATION_NAME;
	private static final Logger logger = LoggerFactory.getLogger(ShopeecrawlerApplication.class);
	private static final SpringApplication APP = new SpringApplication(ShopeecrawlerApplication.class);

	public static void main(String[] args) {
		ShopeecrawlerApplication.APP.run(args);
	}

	@Bean
	public CommandLineRunner commandLineRunner() {
		return args -> {
			logger.info(String.format("%s 应用程序启动成功！", ShopeecrawlerApplication.APPLICATION_NAME));
		};
	}
}
