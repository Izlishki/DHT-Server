package ru.izlishki.dhtserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import ru.izlishki.dhtserver.config.ServerInstance;

import java.io.IOException;

@SpringBootApplication
@EnableConfigurationProperties({ServerInstance.class})
public class App {

    public static void main(String[] args) throws IOException {
        ConfigurableApplicationContext context = SpringApplication.run(App.class, args);

    }
}
