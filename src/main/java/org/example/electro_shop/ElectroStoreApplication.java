package org.example.electro_shop;

import javafx.application.Application;
import javafx.stage.Stage;
import org.example.electro_shop.service.FormService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ElectroStoreApplication extends Application {
    public static ConfigurableApplicationContext applicationContext;
    public static Stage primaryStage;

    public static void main(String[] args) {
        applicationContext = SpringApplication.run(ElectroStoreApplication.class, args);
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        ElectroStoreApplication.primaryStage = primaryStage;
        FormService formService = applicationContext.getBean(FormService.class);
        formService.loadLoginForm();
    }
}
