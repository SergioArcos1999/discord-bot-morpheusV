import commons.utils.CustomFileReader;
import listeners.ChatEventHandler;
import net.dv8tion.jda.api.JDABuilder;

import javax.security.auth.login.LoginException;

public class Main {

    public static void main(String[] args) throws LoginException {

        CustomFileReader reader = new CustomFileReader();
        String botToken = reader.read("botToken");

        JDABuilder builder = JDABuilder.createDefault(botToken);
        builder.addEventListeners(new ChatEventHandler()).build();
    }
}
