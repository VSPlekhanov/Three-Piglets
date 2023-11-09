package org.piglets.static_data;

import org.piglets.entity.Piglet;
import org.piglets.entity.User;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

public class Messages {

    private static final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    public static final String FATAL_ERROR = """ 
            Что-то все совсем сломалось :(
            
            Попробуй еще разок, если не помогает - пиши в поддержку.
            """;
    private static final String GREETING_MESSAGE = """
            Привет!
            
            Добро пожаловать к трем поросятам.
            Три поросенка - это набор копилок в которые вы можете сохранять свои мысли и эмоции со своим партнёром.
            """;

    private static final String TO_BEGIN = """
            Чтобы начать введи id своего партнёра, или попроси его ввести свой.
            
            Вот твой id:
            """;

    private static final String WRONG_ID = """
            Что-то не так с id [%s] это должно быть число.
            Попробуем еще раз.
            
            Введи id своего партнёра. Он должен начать диалог со мной, чтобы я смог найти его.
            """;

    private static final String CANT_FIND_BY_ID = """
            Не могу найти пользователя с таким id [%s].
            Он должен начать диалог со мной, чтобы я смог найти его.
            
            Попробуй еще раз.
            """;

    private static final String PARTNERSHIP_REQUEST_MYSELF = """
            Ты не можешь стать партнёром сам с собой! Выбери другой id :)
            """;

    private static final String PARTNERSHIP_REQUEST = """
            Пользователь с id [%s] хочет стать твоим партнёром!
            """;

    private static final String BREAK_REQUEST = """
            Твой партнёр хочет разбить %s копилку!
            """;

    private static final String WAITING_FOR_PARTNERSHIP_REQUEST_APPROVAL = """
            Отлично, я уведомил этого пользователя, как только он ответит я сразу же напишу.
            """;
    private static final String WAITING_FOR_BREAK_REQUEST_APPROVAL = """
            Отлично, я уведомил твоего партнёра, как только он ответит я сразу же напишу.
            """;
    private static final String PARTNERSHIP_REQUEST_APPROVED = """
            Ура, у тебя теперь есть партнёр - пользователь с id [%s]!
            Теперь вы можете начать пользоваться копилками.
            
            Выбери любую копилку, чтобы прочитать про нее подробнее или воспользоваться ей.
            """;
    
    private static final String MAIN_MENU = """
            Выбери любую копилку, чтобы прочитать про нее подробнее или воспользоваться ей.
            """;

    private static final String PARTNERSHIP_REQUEST_REJECTED = """
            Ой, пользователь с id [%s] отклонил заявку :(
            
            Проверь правильность id и попробуй еще раз.
            """;

    private static final String BREAK_REQUEST_REJECTED = """
            Твой партнёр отклонил заявку
            """;

    private static final String REJECTED_SUCCESS = """
            Запрос отклонен.
            """;

    private static final String PARTNER_IS_ALREADY_TAKEN = """
            Упс, кажется у этого пользователя [%s] уже есть партнёр либо он ожидает подтверждения от другого партнёра...
            Если вы хотите стать партнёрами попроси его сначала удалить текущего партнёра:
            Главное меню -> Настройки -> Удалить партнёра. [Этой функции пока нет, обратись в поддержку]
            
            Либо отменть запрос на установление партнёрства с другим пользователем.
            
            После этого попробуй еще раз.
            """;
    private static final String STATE_CHANGED_ERROR = """
            Кажется что-то поменялось с тех пор как мы общались в последний раз, извини, но я не могу обработать этот запрос.
            
            Попробуй, пожалуйста еще раз.
            """;
    private static final String WRITE_SUCCESS = """
            Отлично, я добавил твою запись!
            """;
    private static final String CANCEL_PARTNERSHIP_REQUEST_SUCCESS = """
            Готово, запрос отменен.
            
            Теперь тебе снова нужно выбрать партнёра. Напоминаю твой id:
            """;

    private static final String CANCEL_REQUEST_SUCCESS = """
            Готово, запрос отменен.
            """;
    private static final String PARTNERSHIP_REQUEST_HAVE_BEEN_CANCELLED = """
            Ой, кажется запрос от пользователя [%s] уже отменен :(
            
            Попробуйте отправить его еще раз.
            """;

    private static final String YOU_ALREADY_HAVE_PARTNER = """
            Ой, кажется у тебя уже есть партнёр - [%s]
            
            Если ты хочешь поменять партнёра тебе сначала нужно его удалить в Настройках
            """;

    private static final String FAILED_TO_PROCESS = """
            Не понимаю тебя :(
            
            Пожалуйста, выбери одну из предложенных опций
            """;

    private static final String BLACK_PIGLET_CHOSEN = """
            Это - серая копилка, в ней хранятся сомнения, тревоги, обиды или просто негативные эмоции.
            """;

    private static final String WHITE_PIGLET_CHOSEN = """
            Это - белая копилка, в ней хранятся радости, хорошие воспоминания, благодарности и любые другие позитивные мысли.
            """;

    private static final String PINK_PIGLET_CHOSEN = """
            Это - розовая копилка, в ней хранятся те идеи, которые хочется воплотить в жизнь.
            """;

    private static final String WRITE_NOTE = """
            Окей, отправь сюда то, что хочешь поместить в копилку
            """;

    private static final String MESSAGE_TEMPLATE = """
            %s UTC
            
            %s
            """;

    private static final String BREAK_IT = """
            Ты хочешь разбить копилку? Это можно сделать только с согласия партнёра.
            Я пришлю вам обоим все ваши накопленные сообщения в чат, если вы это сделаете.
            
            Ну что, разбиваем?
            """;

    private static final String YOUR_MESSAGES = """
            Твои записи:
            """;

    private static final String BREAK_ALREADY_CANCELLED = """
            Кажется твой партнёр уже отменил свой запрос.
            """;

    private static final String PARTNER_MESSAGES = """
            Записи партнёра:
            """;

    public static String greetingMessage() {
        return GREETING_MESSAGE;
    }

    public static String yourMessages() {
        return YOUR_MESSAGES;
    }

    public static String breakAlreadyCancelled() {
        return BREAK_ALREADY_CANCELLED;
    }

    public static String partnerMessages() {
        return PARTNER_MESSAGES;
    }

    public static String failedToProcess() {
        return FAILED_TO_PROCESS;
    }

    public static String cancelRequestSuccess() {
        return CANCEL_REQUEST_SUCCESS;
    }

    public static String toBegin() {
        return TO_BEGIN;
    }

    public static String writeNote() {
        return WRITE_NOTE;
    }
    
    public static String mainMenu() {
        return MAIN_MENU;
    }

    public static String breakIt() {
        return BREAK_IT;
    }

    public static String youAlreadyHavePartner(String partnerId) {
        return YOU_ALREADY_HAVE_PARTNER.formatted(partnerId);
    }

    public static String cancelPartnershipRequestSuccess() {
        return CANCEL_PARTNERSHIP_REQUEST_SUCCESS;
    }

    public static String stateChangedError() {
        return STATE_CHANGED_ERROR;
    }

    public static String breakRequestRejected() {
        return BREAK_REQUEST_REJECTED;
    }

    public static String writeSuccess() {
        return WRITE_SUCCESS;
    }

    public static String waitingForPartnershipRequestApproval() {
        return WAITING_FOR_PARTNERSHIP_REQUEST_APPROVAL;
    }

    public static String waitingForBreakRequestApproval() {
        return WAITING_FOR_BREAK_REQUEST_APPROVAL;
    }

    public static String partnershipRequestApproved(String partnerId) {
        return PARTNERSHIP_REQUEST_APPROVED.formatted(partnerId);
    }

    public static String partnershipRequestRejected(String userId) {
        return PARTNERSHIP_REQUEST_REJECTED.formatted(userId);
    }

    public static String partnerIsAlreadyTaken(String partnerId) {
        return PARTNER_IS_ALREADY_TAKEN.formatted(partnerId);
    }

    public static String rejectedSuccess() {
        return REJECTED_SUCCESS;
    }

    public static String partnershipRequestHaveBeenCancelled(String partnerId) {
        return PARTNERSHIP_REQUEST_HAVE_BEEN_CANCELLED.formatted(partnerId);
    }

    public static String wrongIdMessage(String id) {
        return WRONG_ID.formatted(id);
    }

    public static String cantFindByIdMessage(String id) {
        return CANT_FIND_BY_ID.formatted(id);
    }

    public static String partnershipRequestMyself() {
        return PARTNERSHIP_REQUEST_MYSELF;
    }

    public static String blackPigletChosen() {
        return BLACK_PIGLET_CHOSEN;
    }

    public static String whitePigletChosen() {
        return WHITE_PIGLET_CHOSEN;
    }

    public static String pinkPigletChosen() {
        return PINK_PIGLET_CHOSEN;
    }

    public static String partnershipRequestMessage(String id) {
        return PARTNERSHIP_REQUEST.formatted(id);
    }


    public static String breakRequest(Piglet piglet) {
        return BREAK_REQUEST.formatted(pigletToMessage(piglet));
    }

    public static String decorateMessage(User.Message message) {
        var time = message.createdAt().atZone(ZoneId.of("UTC")).toLocalDateTime();
        return MESSAGE_TEMPLATE.formatted(formatter.format(time), message.text());
    }
    
    private static String pigletToMessage(Piglet piglet) {
        return switch (piglet) {
            case BLACK -> "серую";
            case WHITE -> "белую";
            case PINK -> "розовую";
        };
    }
}
