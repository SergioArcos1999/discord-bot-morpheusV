package listeners.twitter;

import commons.utils.discord.producer.DiscordProducer;
import config.BotConfig;
import listeners.discord.ChatEventHandler;
import listeners.twitter.config.TwitterContext;
import listeners.twitter.config.TwitterSourceConstants;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter4j.*;
import twitter4j.api.*;
import twitter4j.auth.AccessToken;
import twitter4j.auth.Authorization;
import twitter4j.auth.OAuth2Token;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.util.function.Consumer;

import javax.naming.*;
import java.awt.*;
import java.io.File;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.Map;
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
        DiscordProducer webhook = new DiscordProducer("https://discord.com/api/webhooks/843227367522828299/5H_sxFtEF98rzrAuuWruwIazvYiwVljaofFg2otEpTZHROIMjuYbdPmxc4BLOhAR_I_e");


        StatusListener listener = new StatusListener() {

            @Override
            public void onStatus(Status status) {
                final String username = status.getUser().getScreenName();
                final String content = status.getText();
                System.out.println(username + ": " + content);
                logger.info(status.getUser().getScreenName() + ": " + status.getText());
                if(BotConfig.listenTwitter) {
                    try {
                        webhook.setContent("Se ha encontrado el siguiente tweet en la intrané: "
                                + content);
                        webhook.execute();
                    } catch (Exception ex) {
                        logger.info(ex.getMessage());
                    }
                }
            }

            @Override
            public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
                System.out.println("Got a status deletion notice id:" + statusDeletionNotice.getStatusId());
            }

            @Override
            public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
                System.out.println("Got track limitation notice:" + numberOfLimitedStatuses);
            }

            @Override
            public void onScrubGeo(long userId, long upToStatusId) {
                System.out.println("Got scrub_geo event userId:" + userId + " upToStatusId:" + upToStatusId);
            }

            @Override
            public void onException(Exception ex) {
                System.err.println(ex.getMessage());
            }

            @Override
            public void onStallWarning(StallWarning warning) {
                System.out.println("Got stall warning:" + warning);
            }
        };

        FilterQuery filterQuery = new FilterQuery();

        String[] keywords = {"viral", "españa" , "barcelona"}; /* "zaragoza", "madrid", "barcelona" */
        String[] languages = {"es"};



        long users[] = new long[] {2382599791L, 861678265L}; //_danilorenzo_

        //filterQuery.language(languages);
        filterQuery.follow(users); //track(keywords); //language(language).follow(users);

        twitterStream.addListener(listener)
                .filter(filterQuery);
    }
}
