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
import telegram.bot.entity.World;

public class Covid extends TelegramLongPollingBot {


    /**
     * @param message get message parameters
     * @param text    text your message
     *                //     * @param reply   reply on message
     */
    private synchronized void sendMsg(Message message, String text) {
        SendMessage sendMessage = new SendMessage();

        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId());
//        if (reply) {
//            sendMessage.setReplyToMessageId(message.getMessageId());
//        }

        sendMessage.setText(text).setParseMode("html");
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
            System.out.println(message.getText());
            System.out.println(message.getChatId());
            System.out.println(message.getDate());
            Country country = FormStats.getCountry(message.getText()
                    .replaceAll("[.,@$()012456789]", ""));
            if (country != null) {
                sendMsg(message, "\uD83C\uDF0D <b>Данные по стране:</b> " + "<em>" + country.getName() + "</em>"
                        + "\n\n \uD83E\uDD22Заболевших за сутки: " + country.getNewConfirmed()
                        + "\n\uD83C\uDD92 Выздоровевших за сутки: " + country.getNewRecovered()
                        + "\n\uD83D\uDC80 Смертей за сутки: " + country.getNewDeath()
                        + "\n\uD83E\uDD12 <b>Всего заболевших:</b> " + country.getTotalConfirmed()
                        + "\n\uD83C\uDFE5 <b>Всего выздоровевших:</b> " + country.getTotalRecovered()
                        + "\n⚰ <b>Всего смертей:</b> " + country.getTotalDeath()
                        + "\n\uD83D\uDD1B <em>Болеющие на данный момент:</em> " + country.getActive());
            } else {
                World world = FormStats.getWorldTotal();
                if (world != null) {
                    sendMsg(message, "Извини, мой железный мозг не знает такой страны\uD83D\uDE48" +
                            "\nЛибо отсутствуют заболевшие\uD83D\uDE0A" +
                            "\n\n\uD83D\uDDFA <b>Держи данные по всему миру:</b>" +
                            "\n\n\uD83E\uDD12 <em>Общее число заболевших:</em> " + world.getTotalConfirmed()
                            + "\n\uD83C\uDFE5 <em>Общее число выздоровевших:</em> " + world.getTotalRecovered()
                            + "\n⚰ <em>Общее число смертей:</em> " + world.getTotalDeath());
                } else {
                    sendMsg(message, "Произошла ошибка");
                }
            }
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