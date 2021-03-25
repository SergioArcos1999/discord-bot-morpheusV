import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;

public class Main {

    public static void main(String[] args) {
        JDABuilder builder = new JDABuilder(AccountType.BOT);
        String botToken = "";
        builder.setToken(BOT_TOKEN);
    }
}
