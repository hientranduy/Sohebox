package com.hientran.sohebox.configuration;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import lombok.RequiredArgsConstructor;

/**
 * Mails properties
 *
 * @author hientran
 */
@Configuration
@PropertySource("classpath:mail.properties")
@RequiredArgsConstructor
public class MailProperties {

	@Value("${spring.mail.host}")
	private String mailHost;

	@Value("${spring.mail.port}")
	private String mailPort;

	@Value("${spring.mail.username}")
	private String mailUsername;

	@Value("${spring.mail.password}")
	private String mailPassword;

	@Value("${spring.mail.transport.protocol}")
	private String protocol;

	@Value("${spring.mail.properties.mail.smtp.auth}")
	private String auth;

	@Value("${spring.mail.properties.mail.smtp.starttls.enable}")
	private String starttlsEnable;

	@Value("${spring.mail.debug}")
	private String mailDebug;

	/**
	 * 
	 * Bean gmailSender
	 *
	 * @return
	 */
	@Bean(name = "gmailSender")
	public JavaMailSender getJavaMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost(mailHost);
		mailSender.setPort(Integer.valueOf(mailPort));
		mailSender.setUsername(mailUsername);
		mailSender.setPassword(mailPassword);

		Properties props = mailSender.getJavaMailProperties();
		props.put("mail.transport.protocol", protocol);
		props.put("mail.smtp.auth", auth);
		props.put("mail.smtp.starttls.enable", starttlsEnable);
		props.put("mail.debug", mailDebug);

		return mailSender;
	}
}
