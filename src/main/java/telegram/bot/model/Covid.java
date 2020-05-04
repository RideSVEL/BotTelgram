package telegram.bot.model;

import org.telegram.telegrambots.meta.api.methods.ActionType;
import org.telegram.telegrambots.meta.api.methods.send.SendChatAction;
import org.telegram.telegrambots.meta.api.methods.send.SendSticker;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
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

import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

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
        sendMessage.setText(text).setParseMode("html");
        System.out.println(message.getText());
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendCustomKeyboard(Long chatId, List<String> countries) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("⌨ Выбери страну на клавиатуре ниже:");
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        for (int i = 0; i < countries.size(); i++) {
            if (i % 3 == 2) {
                keyboard.add(row);
                row = new KeyboardRow();
            }
            row.add(countries.get(i));
        }
        keyboard.add(row);
        keyboardMarkup.setKeyboard(keyboard);
        keyboardMarkup.setResizeKeyboard(true);
        message.setReplyMarkup(keyboardMarkup);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void chatActionUpdate(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            SendChatAction sendChatAction = new SendChatAction();
            sendChatAction.setChatId(update.getMessage().getChatId());
            sendChatAction.setAction(ActionType.TYPING);
            try {
                execute(sendChatAction);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendSticker(String filePath, Long chatId) {
        SendSticker sendSticker = new SendSticker();
        sendSticker.setChatId(chatId);
        sendSticker.setSticker(new File(filePath));
        try {
            execute(sendSticker);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void countryShow(Message message, Country country) {
        sendSticker("src\\main\\resources\\img\\stickers\\" +
                        BotConfig.PATH_STICKERS_CORONA[new SecureRandom().nextInt(BotConfig.PATH_STICKERS_CORONA.length)],
                message.getChatId());
        sendMsg(message, "\uD83C\uDF0D <b>Данные по стране:</b> " + "<em>" + country.getName() + "</em>" +
                "\nℹ Страна №" + country.getRank() + " в мире по заболеваемости"
                + "\n\n \uD83E\uDD22Заболевших за сутки: " + country.getNewConfirmed()
                + "\n\uD83D\uDC80 Смертей за сутки: " + country.getNewDeath()
                + "\n\uD83E\uDD12 <b>Всего заболевших:</b> " + country.getTotalConfirmed()
                + "\n\uD83C\uDFE5 <b>Всего выздоровевших:</b> " + country.getTotalRecovered()
                + "\n⚰ <b>Всего смертей:</b> " + country.getTotalDeath()
                + "\n\uD83D\uDD1B <em>Болеющие на данный момент:</em> " + country.getActive()
                + "\n❎ <em>В тяжелом состоянии:</em> " + country.getHardState());
    }

    private void worldShow(Message message, World world) {
        sendSticker(BotConfig.STICKER_COVID, message.getChatId());
        sendMsg(message, "\uD83D\uDDFA <b>Держи данные по всему миру:</b>" +
                "\n\n\uD83E\uDD12 <em>Общее число заболевших:</em> " + world.getTotalConfirmed()
                + "\n\uD83C\uDFE5 <em>Общее число выздоровевших:</em> " + world.getTotalRecovered()
                + "\n⚰ <em>Общее число смертей:</em> " + world.getTotalDeath()
                + "\n\uD83C\uDD95 <em>Заболевших за сутки</em> " + world.getNewConfirmed()
                + "\n❌ <em>Смертей за сутки:</em> " + world.getNewDeath()
                + "\n\uD83D\uDC51 <em>Болеют сейчас:</em> " + world.getActive()
                + "\n\uD83C\uDF0C <em>Количество зараженных стран:</em> " + world.getNumberCountries());
    }

    private void commandStart(Message message) {
        sendSticker(BotConfig.STICKER_HI, message.getChatId());
        sendMsg(message, "<b>Привет, " + message.getChat().getFirstName() + "!</b>✋");
        sendMsg(message, "\uD83E\uDDA0 С помощью этого бота у тебя есть возможность получить " +
                "статистику по заболеваемости, выздоровлению и др. данных." +
                "\nЧтобы узнать все возможности и способ управления ботом - отправь команду: /help " +
                "\n\n⛑ Здоровья тебе!");
    }

    private void commandHelp(Message message) {
        sendSticker(BotConfig.STICKER_HELP, message.getChatId());
        sendMsg(message, "Для того, чтобы получить статистику по нужной стране, воспользуйся одним из способов:" +
                "\n1️⃣ Введи первую букву необходимой страны и выбери нужную из списка на клавиатуре" +
                "\n<em>Пример: \"у\", \"U\"</em>" +
                "\n2️⃣ Введи полное название страны" +
                "\n<em>Пример: \"Украина\", \"Ukraine\"</em>" +
                "\n3️⃣ Введи ISO-код нужной страны" +
                "\n<em>Пример: \"UA\"</em>" +
                "\n\uD83C\uDF0D Для получение статистики по всему миру воспользуйся командой: /world" +
                "\nПоддерживается ввод на языках: Русский, Английский." +
                "\n\uD83E\uDD1E <b>Удачи!</b>");
    }

    /**
     * @param update get updates messages from telegram server
     */
    @Override
    public void onUpdateReceived(Update update) {
        chatActionUpdate(update);
        Message message = update.getMessage();
        if (message != null && message.hasText()) {
            switch (message.getText()) {
                case "/start":
                    commandStart(message);
                    break;
                case "/help":
                    commandHelp(message);
                    break;
                case "/world":
                    World world = FormStats.getWorldTotal();
                    if (world != null) {
                        worldShow(message, world);
                    }
                    break;
                default:
                    if (message.getText().length() == 1) {
                        List<String> countries = FormStats.getCountries(message.getText().toUpperCase().charAt(0));
                        assert countries != null;
                        if (countries.size() == 0) {
                            sendMsg(message, "К сожалению, я не нашел стран начинающихся" +
                                    " на данную букву, попробуй еще \uD83D\uDE09\uD83D\uDE0A");
                            return;
                        }
                        sendSticker("src\\main\\resources\\img\\keyboard.tgs", message.getChatId());
                        sendCustomKeyboard(message.getChatId(), countries);
                        return;
                    }
                    System.out.println(message.getText());
                    System.out.println(message.getChatId());
                    System.out.println(message.getDate());
                    sendMsg(message, "⏳ Секунду, сейчас произведу расчеты..");
                    chatActionUpdate(update);
                    Country country = FormStats.getCountry(message.getText()
                            .replaceAll("[.,@$()012456789]", ""));
                    if (country != null) {
                        countryShow(message, country);
                    } else {
                        List<String> countries = FormStats.getCountriesByRegex(message.getText().toLowerCase().replaceAll("[.,@$()012456789]", ""));
                        if (countries != null && countries.size() > 0) {
                            sendMsg(message, "\uD83E\uDD37\u200D♂ К сожалению не знаю такой страны, но нашел похожие");
                            sendCustomKeyboard(message.getChatId(), countries);
                            return;
                        }
                        try {
                            country = FormStats.spellChecking(message.getText().replaceAll("[.,@$()012456789]", ""));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (country != null) {
                            countryShow(message, country);
                            return;
                        }
                        world = FormStats.getWorldTotal();
                        if (world != null) {
                            sendMsg(message, "К сожалению, мой железный мозг не знает такой страны\uD83D\uDE48" +
                                    "\nЛибо отсутствуют заболевшие\uD83D\uDE0A");
                            worldShow(message, world);
                        } else {
                            sendMsg(message, "Произошла ошибка");
                        }
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