import commons.utils.CustomFileReader;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

public class Main extends ListenerAdapter {

    public static void main(String[] args) throws LoginException {

        CustomFileReader reader = new CustomFileReader();
        String botToken = reader.read("botToken");

        JDABuilder builder = JDABuilder.createDefault(botToken);
        builder.addEventListeners(new Main());
        builder.build();
        System.out.println("jaja");

    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String autor = event.getAuthor().getName();
        String content = event.getMessage().getContentDisplay();
        String rawContent = event.getMessage().getContentRaw();

        System.out.println("We received a message from " +
                autor +
                ": " +
                content);

        if (rawContent.equals("!pussy")) {
            event.getChannel().sendMessage("Pussy tu puta madre, zorra").queue();
        }

        if (event.getAuthor().isBot()) {
            return;
        }
    }

}
