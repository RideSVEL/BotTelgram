package model;

import database.DBExceptions;
import database.DBManager;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


import java.util.ArrayList;
import java.util.List;



public class BotKHPI extends TelegramLongPollingBot {

    DBManager dbManager = DBManager.getInstance();
    boolean nameUser = false;
    //TODO change the methods for set name, that it works with unique users
    String name = "default";

//    /**
//     * @param message get message parameters
//     * @param text    text your message
//     * @param reply   reply on message
//     * @param menu    show menu
//     */
//    private synchronized void sendMsg(Message message, String text, boolean reply, boolean menu) {
//        SendMessage sendMessage = new SendMessage();
//        sendMessage.enableMarkdown(true);
//        sendMessage.setChatId(message.getChatId());
//        if (reply) {
//            sendMessage.setReplyToMessageId(message.getMessageId());
//        }
//        if (menu) {
//            setButtons(sendMessage);
//        }
//
//        sendMessage.setText(text);
//        System.out.println(message.getText());
//        try {
//            execute(sendMessage);
//        } catch (TelegramApiException e) {
//            e.printStackTrace();
//        }
//    }

//    /**
//     * @param update get updates messages from telegram server
//     */
//    @Override
//    public void onUpdateReceived(Update update) {
//        Message message = update.getMessage();
//        SendMessage sendMessage = new SendMessage();
//        if (message != null && message.hasText()) {
//            if (nameUser) {
//                try {
//                    dbManager.updateName(message.getChatId(), message.getText());
//                    sendMsg(message, "Привет, " + dbManager.getName(message.getChatId()) + "!\nЭто ваше новое отображаемое имя.", false, false);
//                } catch (DBExceptions dbExceptions) {
//                    dbExceptions.printStackTrace();
//                }
//                nameUser = false;
//            } else {
//                System.out.println(message.getMessageId());
//                System.out.println(message.getChatId());
//                System.out.println(message.getChat());
//                System.out.println(message.getContact());
//
//                switch (message.getText()) {
//                    case "/start":
//                        //sendMsg(message, "Выбери пункт из меню ниже:", false, true);
//
//                        try {
//                            setButtons(sendMessage);
//                            dbManager.insertChat(message.getChatId(), message.getChat().getUserName(), message.getChat().getFirstName(), message.getChat().getLastName());
//                        } catch (DBExceptions dbExceptions) {
//                            dbExceptions.printStackTrace();
//                        }
//                        sendMsg(message, "Привет! Это телеграм бот для абитуриентов ХПИ!\n" +
//                                "Давай знакомиться=)\n" +
//                                "Используй команду /setname.\n" +
//                                "В дальнейшем ты сможешь изменить свое имя используя данную команду.", false, false);
//                        break;
//                    case "/setname":
////                        String temp = message.getText().replaceAll("[.,:!?\"]", message.getText());
////                        String[] buffer = temp.split(" ");
////                        name = new String(new StringBuilder().append(buffer[1]).append(" ").append(buffer[2]));
//                        sendMsg(message, "Введите ваше новое отображаемое имя и фамилию:", false, false);
//                        nameUser = true;
//                        break;
//                    case "/help":
//                        sendMsg(message, "How are you today?", false, false);
//                        break;
//                    case "Hi":
//                        try {
//                            sendMsg(message, "Привет, " + dbManager.getName(message.getChatId()) + "!\nТы лучший(ая)❤", false, false);
//                        } catch (DBExceptions dbExceptions) {
//                            dbExceptions.printStackTrace();
//                        }
//                        break;
//                    case "/settings":
//                        sendMsg(message, "It is settings mod", false, false);
//                        setInline();
//                        break;
//                    default:
//                        sendMsg(message, "Не ломай мой железный мозг=)\nДавай общаться кнопками", true, false);
//                        break;
//                }
//            }
//        }
//    }

    @Override
    public void onUpdatesReceived(List<Update> updates) {

    }

// TODO make this method workable and next
//    @Override
//    public void onUpdateReceived(Update update) {
//        if (update.hasMessage()) {
//            ThreadClass thread = new ThreadClass(update.getMessage());
//        } else if (update.hasCallbackQuery()) {
//            AnswerCallbackThread answerThread = new AnswerCallbackThread(update.getCallbackQuery());
//        }
//    }

// TODO make this method workable and prev
//    public synchronized void answerCallbackQuery(String callbackId, String message) {
//        AnswerCallbackQuery answer = new AnswerCallbackQuery();
//        answer.setCallbackQueryId(callbackId);
//        answer.setText(message);
//        answer.setShowAlert(true);
//        try {
//            answerCallbackQuery(answer);
//        } catch (TelegramApiException e) {
//            e.printStackTrace();
//        }
//    }

    @Override
    public void onUpdateReceived(Update update) {

    }

    /**
     * @return bot`s name, make by registration
     */
    public String getBotUsername() {
        return "@EnrolleeKhpi_bot";
    }

    /**
     * @return bot token to connect the telegram server
     */
    public String getBotToken() {
        return "909718704:AAF1O6VxafVioSkxD6bM1UU_gza0uH6MQlI";
    }

//    /**
//     * @param sendMessage return message for send
//     *                    set keyboards
//     */
//    public synchronized void setButtons(SendMessage sendMessage) {
//        // Создаем клавиуатуру
//        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
//        sendMessage.setReplyMarkup(replyKeyboardMarkup);
//        replyKeyboardMarkup.setSelective(true);
//        replyKeyboardMarkup.setResizeKeyboard(true);
//        replyKeyboardMarkup.setOneTimeKeyboard(false);
//        // создаем строки
//        List<KeyboardRow> keyboardRows = new ArrayList<>();
//        KeyboardRow firstRow = new KeyboardRow();
//        KeyboardRow secondRow = new KeyboardRow();
//
//        firstRow.add(new KeyboardButton("Давай знакомиться"));
//        secondRow.add(new KeyboardButton("Помощь"));
//
//        keyboardRows.add(firstRow);
//        keyboardRows.add(secondRow);
//        replyKeyboardMarkup.setKeyboard(keyboardRows);
//
//        System.out.println("Create keyboard");
//    }
//
//
//    private void setInline() {
//        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
//        List<InlineKeyboardButton> buttons1 = new ArrayList<>();
//        buttons1.add(new InlineKeyboardButton().setText("Кнопка").setCallbackData(String.valueOf(17)));
//        buttons.add(buttons1);
//
//        InlineKeyboardMarkup markupKeyboard = new InlineKeyboardMarkup();
//        markupKeyboard.setKeyboard(buttons);
//    }

    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new BotKHPI());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
