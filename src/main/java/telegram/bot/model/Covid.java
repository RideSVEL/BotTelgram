package telegram.bot.model;

import telegram.bot.config.BotConfig;
import org.telegram.telegrambots.ApiContextInitializer;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import telegram.bot.entity.Country;


import java.util.logging.Level;

//import static sun.util.logging.LoggingSupport.log;

public class Covid extends TelegramLongPollingBot {


    /**
     * @param message get message parameters
     * @param text    text your message
     * @param reply   reply on message
     */
    private synchronized void sendMsg(Message message, String text, boolean reply) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId());
        if (reply) {
            sendMessage.setReplyToMessageId(message.getMessageId());
        }

        sendMessage.setText(text);
        System.out.println(message.getText());
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param update get updates messages from telegram server
     */
    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        if (message != null && message.hasText()) {
            sendMsg(message, FormStats.getBody(message.getText().toLowerCase(), new Country()), false);
            System.out.println(message.getText());
            System.out.println(message.getChatId());
            System.out.println(message.getDate());
            System.out.println(message.getPhoto());
        }
    }

    /**
     * @return bot`s name, make by registration
     */
    public String getBotUsername() {
        return BotConfig.COVID_USER;
    }

    /**
     * @return bot token to connect the telegram server
     */
    public String getBotToken() {
        return BotConfig.COVID_TOKEN;
    }


    /**
     * Register bot on telegram API
     */
    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new Covid());
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
//            log(Level.SEVERE, "Exception: ", e.toString());
        }
    }
}