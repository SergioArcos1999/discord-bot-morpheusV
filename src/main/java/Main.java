import commons.utils.CustomFileReader;
import listeners.ChatEventHandler;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

import javax.security.auth.login.LoginException;

public class Main {

    public static void main(String[] args) throws LoginException {

        CustomFileReader reader = new CustomFileReader();
        String botToken = reader.read("botToken");

        JDA builder = JDABuilder.createDefault(botToken).build();
        builder.addEventListener(new ChatEventHandler());
        builder.getPresence().setStatus(OnlineStatus.ONLINE);
        builder.getPresence().setActivity(Activity.watching("a tu puta madre"));

    }
}
