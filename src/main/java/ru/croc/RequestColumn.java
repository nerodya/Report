package ru.croc;

public enum RequestColumn {

    NUMBER("№ п/п", 0),
    NUMBER_APPEAL("№ обращения", 1),
    TYPE_APPLICANT("Тип заявителя", 2),
    SURNAME("Фамилия", 3),
    NAME("Имя", 4),
    PATRONYMIC("Отчество", 5),
    SERVICE("Услуга", 6),
    DATE_RECEIPT_REQUEST("Дата получения обращения", 7),
    TIME_RECEIPT_REQUEST("Время получения обращения", 8),
    DATE_SENDING_RESPONSE("Дата направления ответа", 9),
    TINE_SENDING_RESPONSE("Время направления ответа", 10),
    NAME_CO("Краткое наименование КО", 11),
    NUMBER_CODE_KGRKO("Номер КО по КГРКО", 12),
    STATUS("Статус", 13);

    private final String name;
    private final int numberColumn;

    RequestColumn(String name, int numberColumn) {
        this.name = name;
        this.numberColumn = numberColumn;
    }

    public int getNumberColumn() {
        return numberColumn;
    }

    public String getName() {
        return name;
    }
}
