package br.com.igorrodrigues.cattlefarm;

import java.beans.PropertyVetoException;

import javax.sql.DataSource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@SpringBootApplication
public class CattlefarmApplication {

	public static void main(String[] args) {
		SpringApplication.run(CattlefarmApplication.class, args);
	}
	
	@Bean
	public DataSource getDataSource() throws PropertyVetoException {
		ComboPooledDataSource dataSource = new ComboPooledDataSource();
		dataSource.setDriverClass("com.mysql.jdbc.Driver");
		dataSource.setUser("root");
		dataSource.setPassword("");
		dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/cattlefarm");
		
		dataSource.setMinPoolSize(5);
		dataSource.setMaxPoolSize(20);
		dataSource.setNumHelperThreads(5);
		dataSource.setIdleConnectionTestPeriod(1);
		
		return dataSource;
	}
}
