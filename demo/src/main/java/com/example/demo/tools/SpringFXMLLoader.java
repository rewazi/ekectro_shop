package com.example.demo.tools;

import javafx.fxml.FXMLLoader;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class SpringFXMLLoader {

    private final ApplicationContext applicationContext;

    public SpringFXMLLoader(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public FXMLLoader load(String fxmlPath) {
        // Попытка получить URL ресурса по указанному пути
        java.net.URL resource = getClass().getResource(fxmlPath);
        if (resource == null) {
            throw new IllegalStateException("FXML-файл не найден по пути: " + fxmlPath);
        }
        FXMLLoader fxmlLoader = new FXMLLoader(resource);
        fxmlLoader.setControllerFactory(applicationContext::getBean);
        return fxmlLoader;
    }
}
