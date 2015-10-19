package org.fb.deviation;

import javafx.fxml.FXMLLoader;
import org.fb.deviation.fx.MainController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;

@Configuration
class ApplicationConfiguration {

    @Bean
    public MainController mainPaneController() throws IOException {
        return (MainController) loadController(MainController.path);
    }

    private Object loadController(String url) throws IOException {
        try (InputStream fxmlStream = getClass().getResourceAsStream(url)) {
            FXMLLoader loader = new FXMLLoader();
            loader.load(fxmlStream);
            return loader.getController();
        }
    }
}