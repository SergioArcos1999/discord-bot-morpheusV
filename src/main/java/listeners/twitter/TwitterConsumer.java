package listeners.twitter;

import commons.utils.discord.producer.DiscordProducer;
import config.BotConfig;
import listeners.twitter.config.TwitterContext;
import listeners.twitter.config.TwitterSourceConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.util.ArrayList;
import java.util.Arrays;

public class TwitterConsumer {
    private static final Logger logger = LoggerFactory.getLogger(TwitterConsumer.class);

    private String consumerKey;
    private String consumerSecret;
    private String accessToken;
    private String accessTokenSecret;

    private TwitterStream twitterStream;

    String[] keywords = {"viral", "españa", "barcelona"}; /* "zaragoza", "madrid", "barcelona" */
    String[] languages = {"es"};
    long[] usersFilter = new long[]{2382599791L, 861678265L, 44196397L}; //Dani, yo, elonmusk
    ArrayList<Long> users = new ArrayList<>(Arrays.asList(2382599791L, 861678265L, 44196397L));

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
        DiscordProducer webhook = new DiscordProducer(BotConfig.discordChatWebhooks[0]);


        StatusListener listener = new StatusListener() {

            @Override
            public void onStatus(Status status) {
                final Long userId = status.getUser().getId();
                final String userName = status.getUser().getScreenName();
                final String content = status.getText();

                if (BotConfig.listenTwitter && users.contains(userId)) {
                    System.out.println("---TWITTER--- " + userName + ": " + content);
                    logger.info(userName + ": " + content);
                    try {
                        webhook.setContent(userName + " acaba de twittear: "
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

            private boolean from_creator(Status status) {
                if (status.getInReplyToUserId() == TwitterResponse.NONE) return false;
                else if (status.getInReplyToStatusId() == TwitterResponse.NONE) return false;
                    //else if(status.getInReplyToScreenName() == null) return false;
                else return true;
            }
        };

        FilterQuery filterQuery = new FilterQuery();

        //filterQuery.language(languages);
        filterQuery.follow(usersFilter); //track(keywords); //language(language).follow(users);

        twitterStream.addListener(listener)
                .filter(filterQuery);
    }
}
