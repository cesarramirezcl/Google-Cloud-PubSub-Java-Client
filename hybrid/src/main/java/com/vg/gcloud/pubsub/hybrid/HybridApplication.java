package com.vg.gcloud.pubsub.hybrid;

import com.google.cloud.ServiceOptions;
import com.google.cloud.pubsub.v1.Subscriber;
import com.google.pubsub.v1.ProjectSubscriptionName;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class HybridApplication {

	public static void main(String[] args) {
		SpringApplication.run(HybridApplication.class, args);
	}

	@Bean
	public CommandLineRunner getCommandLiner() {
		return (args) -> {
			String PROJECT_ID = ServiceOptions.getDefaultProjectId() ;
			String SUBSCRIPTION_ID = "s-invoice" ;
			ProjectSubscriptionName subscriptionName = ProjectSubscriptionName.of(PROJECT_ID, SUBSCRIPTION_ID) ;
			Subscriber subscriber = null ;
			Log log = LogFactory.getLog(HybridApplication.class) ;
			log.info(String.format("Project, %s", PROJECT_ID) );
			try {
				subscriber = Subscriber.newBuilder(subscriptionName, new GMessageReceiver()).build() ;
				subscriber.startAsync().awaitRunning();
				subscriber.awaitTerminated();

			} catch (Exception ex) {
				ex.printStackTrace();
			}
			
		} ;
	}

}