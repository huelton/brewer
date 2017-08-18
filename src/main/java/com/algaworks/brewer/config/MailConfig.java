package com.algaworks.brewer.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.algaworks.brewer.mail.Mailer;

@Configuration
@ComponentScan(basePackageClasses = Mailer.class)
@PropertySource("classpath:env/mail-${ambiente:local}.properties")
@PropertySource(value = "file:\\${USERPROFILE}\\.brewer-mail.properties", ignoreResourceNotFound = true) // ${USERPROFILE} é a pasta home do Usuário, 		          
public class MailConfig {
	
	@Autowired
	private Environment env;

	@Bean
	public JavaMailSender mailSender(){
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost("smtp.sendgrid.net");
		mailSender.setPort(587);
		mailSender.setUsername(env.getProperty("mail.username"));
		mailSender.setPassword(env.getProperty("mail.password"));
		
		Properties pros = new Properties();
		pros.put("mail.transport.protocol", "smtp");
		pros.put("mail.smtp.auth", true);
		pros.put("mail.smtp.starttls.anable", true);
		pros.put("mail.debug", false);
		pros.put("mail.smtp.connectiontimeout", 10000); //milisegundos
		
		mailSender.setJavaMailProperties(pros);
		
		return mailSender;
	}
}
