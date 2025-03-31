package fr.trans80.app;

import fr.trans80.app.services.GtfsService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Trans80Server {

    @Autowired
    private GtfsService gtfsService;

    public static void main(String[] args) {
        SpringApplication.run(Trans80Server.class, args);
    }

}
