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

/*This code defines a class called "HybridApplication" which is annotated with Spring's @SpringBootApplication annotation,
indicating that it is a Spring Boot application. It contains a main method which runs the Spring application
and a CommandLineRunner bean named "getCommandLiner" which is executed when the application runs.

The CommandLineRunner retrieves the default project ID using the ServiceOptions class, sets a constant for the subscription ID to
"s-invoice", creates a ProjectSubscriptionName object using the project ID and subscription ID, initializes a Subscriber object using
the subscriptionName, GMessageReceiver and logs the project ID. Then starts the subscriber, awaits for the subscriber to be running and
terminates it.

This application is a Spring-based application that uses the Google Cloud Pub/Sub library to subscribe to messages on a
specific subscription in a specific project, and use the GMessageReceiver class to handle the received messages.*/

@SpringBootApplication
public class HybridApplication {

	public static void main(String[] args) {
		SpringApplication.run(HybridApplication.class, args);
	}

	@Bean
	public CommandLineRunner getCommandLiner() {
		return (args) -> {
			String PROJECT_ID = ServiceOptions.getDefaultProjectId();
			String SUBSCRIPTION_ID1 = "s-invoice";
			String SUBSCRIPTION_ID2 = "s-invoice1_1";
			ProjectSubscriptionName subscriptionName1 = ProjectSubscriptionName.of(PROJECT_ID, SUBSCRIPTION_ID1);
			ProjectSubscriptionName subscriptionName2 = ProjectSubscriptionName.of(PROJECT_ID, SUBSCRIPTION_ID2);
			Log log = LogFactory.getLog(HybridApplication.class);
			log.info(String.format("Project, %s", PROJECT_ID));
			
			Thread thread1 = new Thread(() -> {
				try {
					Subscriber subscriber = Subscriber.newBuilder(subscriptionName1, new GMessageReceiver()).build();
					subscriber.startAsync().awaitRunning();
					//se puede imprimir hasta aqui
					subscriber.awaitTerminated();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			});
			thread1.start();

			Thread thread2 = new Thread(() -> {
				try {
					Subscriber subscriber = Subscriber.newBuilder(subscriptionName2, new GMessageReceiver()).build();
					subscriber.startAsync().awaitRunning();
					subscriber.awaitTerminated();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			});
			thread2.start();
		} ;
	}
}