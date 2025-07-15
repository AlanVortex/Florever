package utez.edu.mx.florever.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class DBConection {
    @Value("${db.url}")
    private String DB_URL;

    @Value("${db.username}")
    private String DB_USERNAME;

    @Value("${db.password}")
    private String DB_PASSWORD;

    @Bean
    public DataSource getConnection() {
        try {
            DriverManagerDataSource configuration = new DriverManagerDataSource();
            configuration.setUrl(DB_URL);
            configuration.setUsername(DB_USERNAME);
            configuration.setPassword(DB_PASSWORD);
            configuration.setDriverClassName("com.mysql.cj.jdbc.Driver");
            return configuration;
        } catch (Exception ex) {
            System.out.println("Error al conectar la base de datos");
            ex.printStackTrace();
            return null;
        }
    }
}