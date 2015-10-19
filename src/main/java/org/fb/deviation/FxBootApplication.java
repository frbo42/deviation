package org.fb.deviation;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.fb.deviation.fx.MainController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.util.Arrays;

/**
 * Created by frank on 06.09.15.
 */

@SpringBootApplication
public class FxBootApplication extends Application {

    private static String[] args;

    public static void main(String[] args) throws IOException {
        FxBootApplication.args = args;
        launch(args);

    }

    private static void log(ApplicationContext ctx) {
        System.out.println("Let's inspect the beans provided by Spring Boot:");

        String[] beanNames = ctx.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (String beanName : beanNames) {
            System.out.println(beanName);
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        ApplicationContext ctx = SpringApplication.run(FxBootApplication.class, args);
        log(ctx);

        MainController mainController = ctx.getBean(MainController.class);

        Scene scene = new Scene(mainController.getRoot());
        scene.getStylesheets().add("/css/application.css");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
