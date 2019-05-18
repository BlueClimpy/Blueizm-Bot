package com.blueclimpy;

import com.blueclimpy.commands.CommandHandler;
import com.blueclimpy.commands.chat.ClearChatCommand;
import com.blueclimpy.commands.essentials.*;
import com.blueclimpy.events.FirstJoin;
import com.blueclimpy.events.MessageEvent;
import com.mashape.unirest.http.Unirest;
import net.dv8tion.jda.client.entities.Application;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.Guild;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BotMain {

    public static JDABuilder jdaBuilder;
    public static JDA discordJDA;

    public static long timeStart = 0;
    public static List<String> BotID = Arrays.asList("356191447348936710", "538675497022783488");

    public static void main(String[] args) throws Exception {
        jdaBuilder = new JDABuilder(AccountType.BOT);
        jdaBuilder.setToken("NTA4Mzg5MjkwMjc2MDI4NDI3.DyaCXg.pBw8lXCqKTRGJNvsxFcSAUkvHoc");
        jdaBuilder.setAutoReconnect(true);
        jdaBuilder.setGame(Game.streaming("Elice Network", "https://www.twitch.tv/climpytv"));
       // jdaBuilder.setGame(Game.playing("Elice geliştiriliyor.."));
        jdaBuilder.setStatus(OnlineStatus.DO_NOT_DISTURB);

        jdaBuilder.addEventListener(new MessageEvent());
        jdaBuilder.addEventListener(new FirstJoin());
        registerCommands();
        timeStart = System.currentTimeMillis();

        try {
            discordJDA = jdaBuilder.build().awaitReady();
        } catch (LoginException lex) {
            System.out.println("[JDA BlueizmBot] Giriş esnasında bağlantı kurulamadı!");
        } catch (InterruptedException iex) {
            System.out.println("[JDA BlueizmBot] Üzgünüz ki InterruptedExecption hatası gerçekleşti!");
        }

        System.out.println("[JDA Blueizm Bot] Botu açma işlemi başarıyla sonuçlandı.");
        System.out.println("[JDA Blueizm Bot] Bot şu anda " +  BotMain.discordJDA.getGuilds().size() + " sunucuya eklenmiş durumda.");
        System.out.println("[JDA Blueizm Bot] Bot şu anda " +  BotMain.discordJDA.getUsers().size() + " kullanıcıya hizmet ediyor.");
    }

    private static void registerCommands() {
        CommandHandler commandHandler = new CommandHandler();

        commandHandler.registerCommand(new ClearChatCommand());
        commandHandler.registerCommand(new SayCommand());
        commandHandler.registerCommand(new StatusCommand());
        commandHandler.registerCommand(new BanCommand());
        commandHandler.registerCommand(new KickCommand());
        commandHandler.registerCommand(new ShutdownCommand());
        commandHandler.registerCommand(new AvatarCommand());
        commandHandler.registerCommand(new MathCommand());
        commandHandler.registerCommand(new ModsCommand());
    }


    public synchronized static void shutdown() throws IOException {
        System.out.println("Shutdown işlemi başarıyla sonuçlandı.");
        discordJDA.shutdown();
        Unirest.shutdown();
        System.exit(0);
    }
}

