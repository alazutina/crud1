Консольное CRUD приложение, которое взаимодействует с БД и позволяет выполнять все CRUD операции над сущностями:

Writer(id, name, List<Post> posts)

Post(id, content, List<Tag> tags, PostStatus status)

Tag(id, name)

PostStatus (enum ACTIVE, DELETED)

Описание:

    Шаблона MVC (пакеты model, repository, service, controller, view)
    Для миграции БД использовалось https://www.liquibase.org/
    Сервисный слой приложения покрыт юнит тестами (junit + mockito).
    Для импорта библиотек использовался Maven

Технологии: Java, MySQL, JDBC, Maven, Liquibase, JUnit, Mockito
