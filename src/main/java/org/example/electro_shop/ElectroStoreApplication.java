package org.example.electro_shop;

import javafx.application.Application;
import javafx.stage.Stage;
import org.example.electro_shop.service.FormService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

@SpringBootApplication
public class ElectroStoreApplication extends Application {

    private ConfigurableApplicationContext applicationContext;
    private Stage primaryStage;

    public static void main(String[] args) {

        launch(args);
    }

    @Override
    public void init() {

        String[] args = getParameters().getRaw().toArray(new String[0]);
        this.applicationContext = SpringApplication.run(ElectroStoreApplication.class, args);


        ((GenericApplicationContext) applicationContext)
                .getBeanFactory()
                .registerSingleton("javafxApp", this);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        FormService formService = applicationContext.getBean(FormService.class);
        formService.setPrimaryStage(primaryStage);
        formService.loadLoginForm();
    }


    public Stage getPrimaryStage() {
        return primaryStage;
    }
}
