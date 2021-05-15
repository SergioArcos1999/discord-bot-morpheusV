package listeners.discord;

import config.BotConfig;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ChatEventHandler extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String autor = event.getAuthor().getName();
        String content = event.getMessage().getContentDisplay();
        String rawContent = event.getMessage().getContentRaw();
        String[] textVector = rawContent.split("\\s+");
        String firstWord = textVector[0];

        System.out.println("We received a message from " +
                autor +
                ": " +
                content);

        if(textVector[0].equalsIgnoreCase(BotConfig.prefix)) {
            if(textVector.length > 1) {
                if(textVector[1].equalsIgnoreCase("maricon")) event.getChannel().sendMessage("Maricon tu puta madre").queue();
                if(textVector[1].equalsIgnoreCase("help")) {
                    EmbedBuilder info = new EmbedBuilder();
                    event.getChannel().sendMessage(info
                            .setTitle("Morpheus, tu esclavo personal")
                            .setDescription("No hay limites, solo asegurate de que sufre. Calidad asegurada.")
                            .addField("Creator","Dios supremo Sergio Arcos", false)
                            .setColor(0xffffff)
                            .setFooter(("Me ha llamado el amo " + autor), event.getMember().getUser().getAvatarUrl())
                            .build()
                    ).queue();
                    info.clear();
                }

            }
            else {
                event.getChannel().sendMessage("Mi amo, usted esta escribiendo mal el comando, use 'esclavo help' para mas informacion").queue();
            }
        }

        else if(textVector[0].equalsIgnoreCase("xD"))
            event.getChannel().sendMessage("Rianse todos, " + autor + " ha puesto 'XD'").queue();


        if(event.getAuthor().isBot()) {
            return;
        }
    }


}
