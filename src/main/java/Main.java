import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;

public class Main extends ListenerAdapter {

    public static void main(String[] args) throws LoginException {
        final String botToken = "ODI0NzQ0NzIzODEzMjM2Nzky.YFz1YQ.G-D9HVtyW6ztqwCecZnZkq78hP0";
        JDABuilder JDA = JDABuilder.createDefault(botToken);
        JDA.addEventListeners(new Main());
        JDA.build();
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

        if(rawContent.equals("!pussy")) {
            event.getChannel().sendMessage("Pussy tu puta madre, zorra").queue();
        }

        if(event.getAuthor().isBot()) {
            return;
        }
    }

}
