package com.github.toolsrepo.zuul.simple.config;

import com.github.toolsrepo.zuul.simple.service.HelloService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CommandLineAppStarterRunner implements CommandLineRunner {

    Logger logger = LoggerFactory.getLogger(CommandLineAppStarterRunner.class);


    private HelloService helloService;

    public CommandLineAppStarterRunner(HelloService helloService) {

        this.helloService = helloService;
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info(helloService.getHelloMessage());
    }
}
