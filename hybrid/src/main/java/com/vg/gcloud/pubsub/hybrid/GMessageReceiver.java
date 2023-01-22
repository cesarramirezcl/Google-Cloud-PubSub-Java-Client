package com.vg.gcloud.pubsub.hybrid;

import com.google.cloud.pubsub.v1.AckReplyConsumer;
import com.google.cloud.pubsub.v1.MessageReceiver;
import com.google.pubsub.v1.PubsubMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

/*This code defines a class called "GMessageReceiver" that implements the "MessageReceiver" interface from the Google Cloud Pub/Sub library.
It logs the data of a received PubsubMessage and acknowledges it. It also uses Spring Framework's @Component annotation,
indicating that the class is a Spring-managed component. This class is expected to be used in a Spring-based application that is
interacting with Google Cloud Pub/Sub.*/

@Component
public class GMessageReceiver implements MessageReceiver {
    private static final Log log = LogFactory.getLog(GMessageReceiver.class) ;


    @Override
    public void receiveMessage(PubsubMessage message, AckReplyConsumer consumer) {
        // TODO Auto-generated method stub
        log.info(message.getData());
        consumer.ack() ; // this has to be uncommented to acknowledge the message

    }
}
