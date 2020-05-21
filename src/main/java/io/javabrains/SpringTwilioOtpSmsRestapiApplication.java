package io.javabrains;

import java.security.NoSuchAlgorithmException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;

import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.shared.transport.jersey.EurekaJerseyClientImpl.EurekaJerseyClientBuilder;

@SpringBootApplication
@RefreshScope
public class SpringTwilioOtpSmsRestapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringTwilioOtpSmsRestapiApplication.class, args);
	}
	
	 @Bean
		public DiscoveryClient.DiscoveryClientOptionalArgs discoveryClientOptionalArgs() throws NoSuchAlgorithmException {
		    DiscoveryClient.DiscoveryClientOptionalArgs args = new DiscoveryClient.DiscoveryClientOptionalArgs();
		    System.setProperty("javax.net.ssl.keyStore", "src/main/resources/otp-service-cert.jks");
		    System.setProperty("javax.net.ssl.keyStorePassword", "India330$$");
		    System.setProperty("javax.net.ssl.trustStore", "src/main/resources/otp-service-cert.jks");
		    System.setProperty("javax.net.ssl.trustStorePassword", "India330$$");
		    EurekaJerseyClientBuilder builder = new EurekaJerseyClientBuilder();
		    builder.withClientName("otp-service-cert");
		    builder.withSystemSSLConfiguration();
		    builder.withMaxTotalConnections(10);
		    builder.withMaxConnectionsPerHost(10);
		    args.setEurekaJerseyClient(builder.build());
		    return args;
		} 

}
