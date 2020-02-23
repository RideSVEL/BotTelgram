package model;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

import java.util.logging.Level;

import static org.telegram.telegrambots.logging.BotLogger.log;


public class Bot extends TelegramLongPollingBot {

    boolean starting = false;
    String name = "";

    private synchronized void sendMsg(Message message, String text, boolean reply) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId());
        if (reply) {
            sendMessage.setReplyToMessageId(message.getMessageId());
        }
        sendMessage.setText(text);
        //System.out.println(message.getText());
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();

        if (message != null && message.hasText()) {

            if (starting) {
                name = message.getText();
                sendMsg(message, "Привет, " + name + "!\nКак у тебя дела?", false);
                starting = false;
            } else {
                switch (message.getText()) {
                    case "/start":
                        sendMsg(message, "Привет, как твое имя?", false);
                        starting = true;
                        break;
                    case "/help":
                        sendMsg(message, "How are you today?", false);
                        break;
                    case "/settings":
                        sendMsg(message, "It is settings mod", false);
                        break;
                    default:
                        sendMsg(message, "Mistake input", true);
                        break;
                }
            }
        }
    }

    public String getBotUsername() {
        return "AbituraKHPI";
    }

    public String getBotToken() {
        return "909718704:AAF1O6VxafVioSkxD6bM1UU_gza0uH6MQlI";
    }

    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new Bot());
        } catch (TelegramApiRequestException e) {
            log(Level.SEVERE, "Exception: ", e.toString());
        }
    }
}
