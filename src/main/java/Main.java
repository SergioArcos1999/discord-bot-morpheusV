import commons.utils.CustomFileReader;
import listeners.discord.ChatEventHandler;
import listeners.twitter.TwitterConsumer;
import listeners.twitter.config.TwitterContext;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.LoginException;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws LoginException {

        BasicConfigurator.configure();

        CustomFileReader reader = new CustomFileReader();
        String botToken = reader.read("src/main/resources/botToken");

        JDA builder = JDABuilder.createDefault(botToken).build();
        builder.addEventListener(new ChatEventHandler());
        builder.getPresence().setStatus(OnlineStatus.ONLINE);
        builder.getPresence().setActivity(Activity.watching("a tu puta madre"));

        try {
            TwitterContext context = new TwitterContext();
            TwitterConsumer tp = new TwitterConsumer();
            tp.start(context);

        } catch (Exception ex) {
            logger.info(ex.getMessage());
        }

    }
}
