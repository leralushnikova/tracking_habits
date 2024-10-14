package com.lushnikova;

import com.lushnikova.controller.HabitController;
import com.lushnikova.controller.PersonController;
import com.lushnikova.dto.resp.PersonResponse;
import com.lushnikova.exception.ModelNotFound;
import com.lushnikova.mapper_mapstruct.PersonMapper;
import com.lushnikova.middleware.DateMiddleware;
import com.lushnikova.middleware.PersonMiddleware;
import com.lushnikova.repository.PersonRepository;
import com.lushnikova.service.PersonService;
import com.lushnikova.service.impl.PersonServiceImpl;

import java.util.UUID;

import static com.lushnikova.controller.PersonController.scannerString;
import static com.lushnikova.controller.PersonController.wrongInput;


public class Main {


    private static final PersonMapper personMapper = PersonMapper.INSTANCE;
    private static final DateMiddleware dateMiddleware = new DateMiddleware();
    private static final PersonMiddleware personMiddleware = new PersonMiddleware();
    private static final PersonRepository personRepository = PersonRepository.getInstance();
    private static final PersonService personService = new PersonServiceImpl(personMapper, personRepository);
    private static final PersonController personController = new PersonController(personService, personMiddleware);
    private static final HabitController habitController = new HabitController(personService, dateMiddleware);

    public static void main(String[] args) throws ModelNotFound {

        PersonResponse personResponse;
        while (true) {
            System.out.println("Вы хотите войти(in), зарегистрироваться(up) или выйти(exit)?[in/up/exit]");
            String input = scannerString();

            switch (input) {
                case ("in") -> {
                    personResponse = personController.getPersonAfterAuthentication();
                    modesForUsers(personResponse.getId());
                }
                case ("up") -> {
                    personResponse = personController.createPerson();
                    modesForUsers(personResponse.getId());
                }
                case ("exit") -> {
                    return;
                }

                default -> wrongInput();
            }
        }
    }

    public static void modesForUsers(UUID idPerson) throws ModelNotFound {
        while (true) {
            PersonResponse personResponse = personController.getPerson(idPerson);
            if (personResponse != null) {
                System.out.println("Выберите режим: ");
                System.out.println("1 - Редактировать данные пользователя");
                System.out.println("2 - Просмотр данных пользователя");
                System.out.println("3 - Управления режимами привычек");
                System.out.println("4 - Просмотр привычек");
                System.out.println("5 - Статистика привычек");
                System.out.println("6 - Процент успешного выполнения привычек");
                System.out.println("7 - Отчет по привычке");
                System.out.println("exit - Выход");

                String answer = scannerString();

                switch (answer) {
                    case "1" -> personController.editPerson(idPerson);

                    case "2" -> personController.readPerson(idPerson);

                    case "3" -> habitController.crudHabits(idPerson);

                    case "4" -> habitController.readHabits(idPerson);

                    case "5" -> habitController.getHabitFulfillmentStatisticsByIdPerson(idPerson);

                    case "6" -> habitController.getPercentSuccessHabitsByIdPerson(idPerson);

                    case "7" -> habitController.reportHabitByIdPerson(idPerson);

                    case "exit" -> {
                        return;
                    }
                    default -> wrongInput();
                }
            } else return;
        }
    }


}
