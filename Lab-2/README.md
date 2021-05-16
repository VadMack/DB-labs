### Задача:
1.	Придумать свою предметную область и продумать схему БД для неё.
2.	Реализовать реляционную БД для своей предметной области(все таблицы должны быть не менее чем в третьей нормальной форме)
3.	Критерии к БД:  
[1] 	БД должна быть в третьей нормальной форме или выше;  
[2] 	Минимальное количество таблиц – 2;
[3] 	Все подключения из GUI должны осуществляться выделенным, не root, пользователем;  
[4] 	Должен существовать как минимум один индекс, созданный вами по выбранному текстовому не ключевому полю;  
[5] 	В одной из таблиц должно присутствовать поле, заполняемое/изменяемое только триггером (например, «общая стоимость бронирования» в таблице «бронирования», которое автоматически высчитывается при добавлении/изменении/удалении билетов, входящих в это бронирование). 
4.	Реализовать программу GUI со следующим функционалом:   
[1] 	Создание базы данных (важно(!) именно create database, а не только create table).  
[2] 	Удаление базы данных.  
[3] 	Вывод содержимого таблиц.  
[4] 	Очистка(частичная - одной, и полная - всех) таблиц.  
[5] 	Добавление новых данных.  
[6] 	Поиск по заранее выбранному (вами) текстовому не ключевому полю.  
[7] 	Обновление кортежа.  
[8] 	Удаление по заранее выбранному текстовому не ключевому полю.  
[9] 	Удаление конкретной записи, выбранной пользователем.  
[10] 	Все функции должны быть реализованы как хранимые процедуры.   
Из GUI вызывать только хранимые процедуры/функции. Выполнение произвольного SQL кода запрещено.  
В качестве отчёта от вас ожидается: описание вашей предметной области, схема бд(с пояснением в какой НФ она находится и почему), исходный код(лучше всего выкладывайте на любой source control server, например github, bitbucket и мне просто ссылку) и демонстрация работы вашего приложения на практике в виде видео с комментариями

### Выполнение:

Создание пользователя:

``` SQL
CREATE USER 'admin'@'localhost' IDENTIFIED BY 'admin';
GRANT ALL PRIVILEGES ON * . * TO 'admin'@'localhost';
```

Создание таблиц:

``` SQL
CREATE TABLE clubs
(
    id           INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name         VARCHAR(255),
    city_name    VARCHAR(255),
    salary_costs INT UNSIGNED
);

CREATE TABLE players
(
    id         INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(255),
    last_name  VARCHAR(255),
    club_id    INT UNSIGNED,
    salary     INT UNSIGNED NOT NULL DEFAULT 0,
    FOREIGN KEY (club_id) REFERENCES clubs (id)
);
```

1НФ:
- в любом допустимом значении отношения каждый кортеж содержит только одно значение для каждого из атрибутов.  

2НФ:  
- каждый неключевой атрибут неприводимо (функционально полно) зависит от потенциального ключа.  

3НФ:  
- отсутствуют транзитивные функциональные зависимости неключевых атрибутов от ключевых


Триггеры:

``` SQL
CREATE TRIGGER TRG_insert
    AFTER INSERT
    ON players
    FOR EACH ROW
BEGIN
    UPDATE clubs
    SET salary_costs=(SELECT SUM(salary)
                      FROM players
                      GROUP BY club_id)
    where clubs.id = NEW.club_id;
END;

CREATE TRIGGER TRG_update
    AFTER UPDATE
    ON players
    FOR EACH ROW
BEGIN
    UPDATE clubs
    SET salary_costs=(SELECT SUM(salary)
                      FROM players
                      GROUP BY club_id)
    where clubs.id = NEW.club_id;
END;

CREATE TRIGGER TRG_delete
    AFTER DELETE
    ON players
    FOR EACH ROW
BEGIN
    UPDATE clubs
    SET salary_costs=(SELECT SUM(salary)
                      FROM players
                      GROUP BY club_id)
    where clubs.id = OLD.club_id;
END;
```

Создание индекса: 

```SQL
CREATE INDEX IND_last_name ON players (last_name);
```



