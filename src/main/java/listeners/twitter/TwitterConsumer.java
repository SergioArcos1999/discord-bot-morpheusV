package listeners.twitter;

import listeners.discord.ChatEventHandler;
import listeners.twitter.config.TwitterContext;
import listeners.twitter.config.TwitterSourceConstants;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import javax.naming.*;
import java.util.Hashtable;
import java.util.Properties;

public class TwitterConsumer {
    private static final Logger logger = LoggerFactory.getLogger(TwitterConsumer.class);

    private String consumerKey;
    private String consumerSecret;
    private String accessToken;
    private String accessTokenSecret;

    private TwitterStream twitterStream;

    public void start(TwitterContext context) {

        consumerKey = context.getString(TwitterSourceConstants.CONSUMER_KEY);
        consumerSecret = context.getString(TwitterSourceConstants.CONSUMER_SECRET_KEY);
        accessToken = context.getString(TwitterSourceConstants.ACCESS_TOKEN_KEY);
        accessTokenSecret = context.getString(TwitterSourceConstants.ACCESS_TOKEN_SECRET_KEY);

        ConfigurationBuilder cb = new ConfigurationBuilder()
                .setOAuthConsumerKey(consumerKey)
                .setOAuthConsumerSecret(consumerSecret)
                .setOAuthAccessToken(accessToken)
                .setOAuthAccessTokenSecret(accessTokenSecret);


        twitterStream = new TwitterStreamFactory(cb.build()).getInstance();

        StatusListener listener = new StatusListener() {

            public void onStatus(Status status) {
//                ChatEventHandler chat =  new ChatEventHandler();
//                chat.onMessageReceived(MessageReceivedEvent) {
//                };
                System.out.println(status.getUser().getScreenName() + ": " + status.getText());
                logger.info(status.getUser().getScreenName() + ": " + status.getText());
            }

            public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {

            }

            public void onTrackLimitationNotice(int numberOfLimitationNotice) {

            }

            public void onScrubGeo(long userId, long upToStatusId) {

            }

            public void onException(Exception ex) {
                System.err.println(ex.getMessage());
            }

            public void onStallWarning(StallWarning warning) {

            }
        };

        FilterQuery filterQuery = new FilterQuery();

        String keywords[] = {"zaragoza", "madrid", "barcelona"}; /* "zaragoza", "madrid", "barcelona" */
        String language[] = {"es"};
        long users = Long.parseLong("_danilorenzo_");

        filterQuery.language(language).follow(users);

        twitterStream.addListener(listener)
                .filter(filterQuery)
                .sample();
    }

    public static void main(String[] args) {
        try {
            TwitterContext context = new TwitterContext();
            TwitterConsumer tp = new TwitterConsumer();
            tp.start(context);

        } catch (Exception ex) {
            logger.info(ex.getMessage());
        }
    }
}
