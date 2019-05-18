package com.blueclimpy.commands.chat;

import com.blueclimpy.commands.Command;
import com.blueclimpy.commands.CommandParser;
import com.blueclimpy.constants.Emoji;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.MessageHistory;
import net.dv8tion.jda.core.entities.TextChannel;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class ClearChatCommand extends Command {
    private List<Message> messageList;
    private String usedCommand;

    public ClearChatCommand() {
        super("clearchat", "Sohbeti siler.", new String[]{"clerchat", "sohbetitemizle", "sohbetisil", "sohbetsil"});
    }
    @Override
    public String getUsage() {
        return "Doğru Kullanım: !" + this.usedCommand + " <Mesaj Sayısı>";
    }

    @Override
    public void onChannelCommand(CommandParser context) {
        MessageChannel messageChannel = context.getChannel();
        Message senderMessage = context.getMessage();

        // Birisini taglama şeysi senderMessage.getAuthor().getAsMention()

        this.usedCommand = context.getLabel();
      //  if (senderMessage.getAuthor().isBot()) return;
        if (!context.getMember().hasPermission(Permission.MESSAGE_MANAGE)) {
            messageChannel.sendMessage(
                     Emoji.HATA + " Bu komutu kullanma izniniz yok! "
            ).complete().delete().queueAfter(5, TimeUnit.SECONDS);
            senderMessage.delete().queueAfter(5, TimeUnit.SECONDS);
            return;
        }

        String[] args = senderMessage.getContentRaw().toLowerCase().split(" ");
        if (args.length < 2) {
            messageChannel.sendMessage(
                    "\uD83D\uDEAB " + this.getUsage()
            ).queue();
            return;
        }

        Integer messageNumber = Integer.parseInt(args[1]);
        Integer finalMessageNumber = messageNumber;
        if (finalMessageNumber > 1 && finalMessageNumber <= 150) {
            try {
                TextChannel textChannel = context.getMessage().getTextChannel();
                MessageHistory messageHistory = new MessageHistory(textChannel);
                messageList = messageHistory.retrievePast(finalMessageNumber).complete();
                textChannel.deleteMessages(messageList).queue();

                senderMessage.getChannel().sendTyping().queue();
                messageChannel.sendMessage(
                        "Bu kanaldan **" + (finalMessageNumber) + "** adet mesaj başarıyla silindi. :recycle:"
                ).queue();
            } catch (Exception ex) {
                senderMessage.getChannel().sendTyping().queue();
                messageChannel.sendMessage(
                        "**Komut Hatası:** Girdiğiniz, " + getName() + " komutu belirtilen sayı kadar kanalda mesaj olmadığı için işleme alınamadı. "
                ).queue();
            }
        } else {
            senderMessage.getChannel().sendTyping().queue();
            messageChannel.sendMessage(
                    "Lütfen **1** veya **150** arasında bir sayı giriniz! " + senderMessage.getAuthor().getAsMention()
            ).queue();
        }
    }
}