### 1.	Дана схема базы данных в виде следующих отношений.  С помощью операторов SQL создать логическую структуру соответствующих таблиц для хранения в СУБД, используя известные средства поддержания целостности (NOT NULL, UNIQUE, и т.д.). Обосновать выбор типов данных и используемые средства поддержания целостности. При выборе подходящих типов данных использовать информацию о конкретных значениях полей БД (см. прил.1) ###

``` SQL
CREATE TABLE medpersonal (
	id INT UNSIGNED PRIMARY KEY,
	-- INT UNSIGNED: представляет целые числа от 0 до 4294967295, занимает 4 байта.
	-- Определение первичного ключа эквивалентно  определению уникального ключа,
	-- дополненного ограничением NOT NULL.

	last_name VARCHAR(100),
	-- VARCHAR: представляет стоку переменной длины.
	adress VARCHAR(100),
	-- VARCHAR: представляет стоку переменной длины.
	tax TINYINT CHECK (tax >= 0
		AND tax <= 100)
	-- TINYINT: представляет целые числа от -127 до 128, занимает 1 байт.
	-- Проценты от 0 до 100.

);

CREATE TABLE place_of_work (
	id INT UNSIGNED PRIMARY KEY,
	-- INT UNSIGNED: представляет целые числа от 0 до 4294967295, занимает 4 байта.
	-- Определение первичного ключа эквивалентно  определению уникального ключа,
	-- дополненного ограничением NOT  NULL.

	facility VARCHAR(100),
	-- VARCHAR: представляет стоку переменной длины.
	adress VARCHAR(100),
	-- VARCHAR: представляет стоку переменной длины.
	local_budget_allocations TINYINT CHECK (local_budget_allocations >= 0
		AND local_budget_allocations <= 100)
	-- TINYINT: представляет целые числа от -127 до 128, занимает 1 байт.
	-- Проценты от 0 до 100.

);

CREATE TABLE types_of_operations (
	id INT UNSIGNED PRIMARY KEY,
	-- INT UNSIGNED: представляет целые числа от 0 до 4294967295, занимает 4 байта.
	-- Определение первичного ключа эквивалентно  определению уникального ключа,
	-- дополненного ограничением NOT  NULL.

	name VARCHAR(100),
	-- VARCHAR: представляет стоку переменной длины.
	stronghold VARCHAR(100),
	-- VARCHAR: представляет стоку переменной длины.
	stocks INT UNSIGNED,
	-- INT UNSIGNED: представляет целые числа от 0 до 4294967295, занимает 4 байта.
	cost INT UNSIGNED
	-- INT UNSIGNED: представляет целые числа от 0 до 4294967295, занимает 4 байта.
);

CREATE TABLE work_activities
(
    contract       INT UNSIGNED PRIMARY KEY,
    -- INT UNSIGNED: представляет целые числа от 0 до 4294967295, занимает 4 байта.
    -- Определение первичного ключа эквивалентно  определению уникального ключа,
    -- дополненного ограничением NOT  NULL.
    date          VARCHAR(15),
    -- VARCHAR: представляет стоку переменной длины.
    -- Хранятся дни недели
    medical_staff INT UNSIGNED,
    -- INT UNSIGNED: представляет целые числа от 0 до 4294967295, занимает 4 байта.
    workplace     INT UNSIGNED,
    -- INT UNSIGNED: представляет целые числа от 0 до 4294967295, занимает 4 байта.
    operation     INT UNSIGNED,
    -- INT UNSIGNED: представляет целые числа от 0 до 4294967295, занимает 4 байта.
    quantity      INT UNSIGNED,
    -- INT UNSIGNED: представляет целые числа от 0 до 4294967295, занимает 4 байта.
    payment       INT UNSIGNED,
    -- INT UNSIGNED: представляет целые числа от 0 до 4294967295, занимает 4 байта.
    FOREIGN KEY (medical_staff) REFERENCES medpersonal (id),
    FOREIGN KEY (workplace) REFERENCES place_of_work (id),
    FOREIGN KEY (operation) REFERENCES types_of_operations (id)
);
```

### 2.	Ввести в ранее созданные таблицы конкретные данные (см. прил. 1). Использовать скрипт-файл из операторов INSERT или вспомогательную утилиту. ###
``` SQL
INSERT INTO medpersonal VALUES
 (001, 'Медина', 	'Вознесенское',	14),
 (002,	'Севастьянов',	'Навашино',	14),
 (003,	'Бессонов',	'Выкса',	10),
 (004,	'Губанов',	'Выкса',	10),
 (005,	'Боева',	'Починки',	5);

INSERT INTO place_of_work VALUES
 (001,	'Районная больница',	'Вознесенское',	10),
 (002,	'Травм. пункт',		'Выкса',	3),
 (003,	'Больница',		'Навашино',	4),
 (004,	'Род. дом',		'Вознесенское',	12),
 (005,	'Больница',		'Починки',	4),
 (006,	'Травм.пункт',		'Лукояново',	3);

INSERT INTO types_of_operations VALUES
 (001,	'Наложение гипса',		'Выкса',	2000,	18000),
 (002,	'Блокада',			'Навашино',	10000,	14000),
 (003,	'Инъекция поливитаминов',	'Навашино',	20000,	11000),
 (004,	'Инъекция алоэ',		'Навашино',	12000,	11000),
 (005,	'ЭКГ',				'Вознесенское',	115,	10000),
 (006,	'УЗИ',				'Вознесенское',	20,	30000),
 (007,	'Флюорография',			'Выкса',	1000,	5000);
 
INSERT INTO work_activities
VALUES (51040, 'Понедельник', 001, 001, 007, 4, 20000),
       (51041, 'Понедельник', 003, 003, 006, 1, 30000),
       (51042, 'Понедельник', 004, 003, 004, 3, 33000),
       (51043, 'Понедельник', 004, 005, 001, 2, 36000),
       (51044, 'Понедельник', 004, 004, 006, 1, 30000),
       (51045, 'Среда', 002, 002, 005, 3, 30000),
       (51046, 'Четверг', 003, 006, 004, 4, 44000),
       (51047, 'Четверг', 004, 006, 002, 1, 28000),
       (51048, 'Четверг', 005, 003, 003, 4, 44000),
       (51049, 'Пятница', 002, 004, 005, 1, 10000),
       (51050, 'Пятница', 003, 006, 004, 2, 22000),
       (51051, 'Пятница', 003, 003, 001, 2, 36000),
       (51052, 'Пятница', 005, 003, 002, 1, 14000),
       (51053, 'Суббота', 003, 002, 007, 2, 10000),
       (51054, 'Суббота', 004, 006, 004, 1, 11000),
       (51055, 'Суббота', 005, 005, 004, 2, 22000),
       (51056, 'Суббота', 003, 006, 003, 2, 22000);
```

### 3.	Используя оператор SELECT создать запрос для вывода всех строк каждой таблицы. Проверить правильность ввода. При необходимости произвести коррекцию значений операторами INSERT, UPDATE, DELETE.  ###
``` SQL
SELECT * FROM medpersonal;
```

| id | last\_name | adress | tax |
| :--- | :--- | :--- | :--- |
| 1 | Медина | Вознесенское | 14 |
| 2 | Севастьянов | Навашино | 14 |
| 3 | Бессонов | Выкса | 10 |
| 4 | Губанов | Выкса | 10 |
| 5 | Боева | Починки | 5 |

``` SQL
SELECT * FROM place_of_work;
```

| id | facility | adress | local\_budget\_allocations |
| :--- | :--- | :--- | :--- |
| 1 | Районная больница | Вознесенское | 10 |
| 2 | Травм. пункт | Выкса | 3 |
| 3 | Больница | Навашино | 4 |
| 4 | Род. дом | Вознесенское | 12 |
| 5 | Больница | Починки | 4 |
| 6 | Травм.пункт | Лукояново | 3 |

``` SQL
SELECT * FROM types_of_operations;
```

| id | name | stronghold | stocks | cost |
| :--- | :--- | :--- | :--- | :--- |
| 1 | Наложение гипса | Выкса | 2000 | 18000 |
| 2 | Блокада | Навашино | 10000 | 14000 |
| 3 | Инъекция поливитаминов | Навашино | 20000 | 11000 |
| 4 | Инъекция алоэ | Навашино | 12000 | 11000 |
| 5 | ЭКГ | Вознесенское | 115 | 10000 |
| 6 | УЗИ | Вознесенское | 20 | 30000 |
| 7 | Флюорография | Выкса | 1000 | 5000 |

``` SQL
SELECT * FROM work_activities;
```

| contract | date | medical\_staff | workplace | operation | quantity | payment |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| 51040 | Понедельник | 1 | 1 | 7 | 4 | 20000 |
| 51041 | Понедельник | 3 | 3 | 6 | 1 | 30000 |
| 51042 | Понедельник | 4 | 3 | 4 | 3 | 33000 |
| 51043 | Понедельник | 4 | 5 | 1 | 2 | 36000 |
| 51044 | Понедельник | 4 | 4 | 6 | 1 | 30000 |
| 51045 | Среда | 2 | 2 | 5 | 3 | 30000 |
| 51046 | Четверг | 3 | 6 | 4 | 4 | 44000 |
| 51047 | Четверг | 4 | 6 | 2 | 1 | 28000 |
| 51048 | Четверг | 5 | 3 | 3 | 4 | 44000 |
| 51049 | Пятница | 2 | 4 | 5 | 1 | 10000 |
| 51050 | Пятница | 3 | 6 | 4 | 2 | 22000 |
| 51051 | Пятница | 3 | 3 | 1 | 2 | 36000 |
| 51052 | Пятница | 5 | 3 | 2 | 1 | 14000 |
| 51053 | Суббота | 3 | 2 | 7 | 2 | 10000 |
| 51054 | Суббота | 4 | 6 | 4 | 1 | 11000 |
| 51055 | Суббота | 5 | 5 | 4 | 2 | 22000 |
| 51056 | Суббота | 3 | 6 | 3 | 2 | 22000 |

### 4.	Вывести с помощью запросов: ###
#### a)	различные адреса всех медработников; ####

``` SQL
SELECT DISTINCT adress FROM medpersonal;
```
| adress |
| :--- |
| Вознесенское |
| Навашино |
| Выкса |
| Починки |

#### b)	список всех различных медучреждений; ####

``` SQL
SELECT DISTINCT facility FROM place_of_work;
```
| facility |
| :--- |
| Районная больница |
| Травм. пункт |
| Больница |
| Род. дом |
| Травм.пункт |

#### c)	различные дни, для которых хранится информация о трудовой деятельности. ####

```SQL
SELECT DISTINCT date FROM work_activities;
```

| date |
| :--- |
| Понедельник |
| Среда |
| Четверг |
| Пятница |
| Суббота |


### 5.	Найти: ###
#### a)	даты и номера договоров, когда производились операции на сумму не менее 14000руб. ####

```SQL
SELECT date, contract
FROM work_activities
WHERE payment >= 14000;
```

| date | contract |
| :--- | :--- |
| Понедельник | 51040 |
| Понедельник | 51041 |
| Понедельник | 51042 |
| Понедельник | 51043 |
| Понедельник | 51044 |
| Среда | 51045 |
| Четверг | 51046 |
| Четверг | 51047 |
| Четверг | 51048 |
| Пятница | 51050 |
| Пятница | 51051 |
| Пятница | 51052 |
| Суббота | 51055 |
| Суббота | 51056 |


#### b)	размер налога для медперсонала из Выксы или Навашино; ####

``` SQL
SELECT tax
FROM medpersonal
WHERE (adress = 'Выкса'
    OR adress = 'Навашино');
```

| tax |
| :--- |
| 14 |
| 10 |
| 10 |

#### c)	название, стоимость и адрес опорного пункта для операций, в названии которых есть слово “Инъекция”, и стоящих более 10000руб. Результат отсортировать по адресу и стоимости. ####
``` SQl
SELECT name, cost, stronghold
from types_of_operations
where cost > 10000
  AND locate('Инъекция', name) > 0
ORDER BY stronghold, cost;
```
| name | cost | stronghold |
| :--- | :--- | :--- |
| Инъекция поливитаминов | 11000 | Навашино |
| Инъекция алоэ | 11000 | Навашино |

### 6.	На основании данных о проведенных операциях вывести в следующем формате все записи: ###
#### a)	дата, фамилия медперсонала, название места работы, название операции; ####
``` SQl
SELECT w.date, m.last_name, pow.facility, too.name
FROM work_activities AS w
         JOIN medpersonal m on m.id = w.medical_staff
         JOIN place_of_work pow on pow.id = w.workplace
         JOIN types_of_operations too on too.id = w.operation;
```

| date | last\_name | facility | name |
| :--- | :--- | :--- | :--- |
| Понедельник | Медина | Районная больница | Флюорография |
| Понедельник | Бессонов | Больница | УЗИ |
| Понедельник | Губанов | Больница | Инъекция алоэ |
| Понедельник | Губанов | Больница | Наложение гипса |
| Понедельник | Губанов | Род. дом | УЗИ |
| Среда | Севастьянов | Травм. пункт | ЭКГ |
| Четверг | Бессонов | Травм.пункт | Инъекция алоэ |
| Четверг | Губанов | Травм.пункт | Блокада |
| Четверг | Боева | Больница | Инъекция поливитаминов |
| Пятница | Севастьянов | Род. дом | ЭКГ |
| Пятница | Бессонов | Травм.пункт | Инъекция алоэ |
| Пятница | Бессонов | Больница | Наложение гипса |
| Пятница | Боева | Больница | Блокада |
| Суббота | Бессонов | Травм. пункт | Флюорография |
| Суббота | Губанов | Травм.пункт | Инъекция алоэ |
| Суббота | Боева | Больница | Инъекция алоэ |
| Суббота | Бессонов | Травм.пункт | Инъекция поливитаминов |


#### b)	номер договора, название места работы, количество операций, оплата. Отсортировать по возрастанию оплаты. ####
``` SQl
SELECT w.contract, pow.facility, w.quantity, w.payment
FROM work_activities w
JOIN place_of_work pow on pow.id = w.workplace
ORDER BY w.payment;
```

| contract | facility | quantity | payment |
| :--- | :--- | :--- | :--- |
| 51053 | Травм. пункт | 2 | 10000 |
| 51049 | Род. дом | 1 | 10000 |
| 51054 | Травм.пункт | 1 | 11000 |
| 51052 | Больница | 1 | 14000 |
| 51040 | Районная больница | 4 | 20000 |
| 51055 | Больница | 2 | 22000 |
| 51050 | Травм.пункт | 2 | 22000 |
| 51056 | Травм.пункт | 2 | 22000 |
| 51047 | Травм.пункт | 1 | 28000 |
| 51045 | Травм. пункт | 3 | 30000 |
| 51041 | Больница | 1 | 30000 |
| 51044 | Род. дом | 1 | 30000 |
| 51042 | Больница | 3 | 33000 |
| 51051 | Больница | 2 | 36000 |
| 51043 | Больница | 2 | 36000 |
| 51048 | Больница | 4 | 44000 |
| 51046 | Травм.пункт | 4 | 44000 |


### 7.	Определить: ###
#### a)	фамилии и места проживания медперсонала, проведших более одного наложения гипса в день; ####
``` SQl
SELECT m.last_name, m.adress
FROM medpersonal m
         JOIN work_activities wa on m.id = wa.medical_staff
         JOIN types_of_operations too on too.id = wa.operation
WHERE wa.quantity > 1
  AND too.name = 'Наложение гипса';
```
| last\_name | adress |
| :--- | :--- |
| Губанов | Выкса |
| Бессонов | Выкса |


#### b)	название операций, которые проводили врачи из Вознесенского или Выксы в больницах; ####
``` SQl
SELECT too.name
FROM types_of_operations too
         JOIN work_activities wa on too.id = wa.operation
         JOIN medpersonal m on m.id = wa.medical_staff
         JOIN place_of_work pow on pow.id = wa.workplace
WHERE (m.adress = 'Выкса' OR m.adress = 'Вознесенское')
  AND pow.facility = 'Больница'
GROUP BY too.name;
```
| name |
| :--- |
| УЗИ |
| Наложение гипса |
| Инъекция алоэ |


#### c)	названия и размер отчислений в местный бюджет для тех учреждений, где проводили операции те, у кого налог не менее 7%, но не более 16%. Включить в вывод фамилии таких людей и отсортировать по размеру отчислений и налогу; ####
``` SQl
SELECT DISTINCT pow.facility, pow.local_budget_allocations, m.last_name, m.tax
FROM types_of_operations too
         JOIN work_activities wa on too.id = wa.operation
         JOIN medpersonal m on m.id = wa.medical_staff
         JOIN place_of_work pow on pow.id = wa.workplace
WHERE m.tax >= 7
  AND m.tax <= 16
ORDER BY pow.local_budget_allocations, m.tax;
```

| facility | local\_budget\_allocations | last\_name | tax |
| :--- | :--- | :--- | :--- |
| Травм.пункт | 3 | Бессонов | 10 |
| Травм. пункт | 3 | Бессонов | 10 |
| Травм.пункт | 3 | Губанов | 10 |
| Травм. пункт | 3 | Севастьянов | 14 |
| Больница | 4 | Бессонов | 10 |
| Больница | 4 | Губанов | 10 |
| Районная больница | 10 | Медина | 14 |
| Род. дом | 12 | Губанов | 10 |
| Род. дом | 12 | Севастьянов | 14 |

#### d)	даты, идентификаторы операций и фамилии тех, кто проводил операции стоимостью не менее 7000руб больше одного раза. ####
``` SQl
SELECT wa.date, too.id, m.last_name
FROM work_activities wa
         JOIN medpersonal m on m.id = wa.medical_staff
         JOIN types_of_operations too on too.id = wa.operation
WHERE too.cost >= 7000
  AND wa.quantity > 1
```

| date | id | last\_name |
| :--- | :--- | :--- |
| Понедельник | 1 | Губанов |
| Пятница | 1 | Бессонов |
| Четверг | 3 | Боева |
| Суббота | 3 | Бессонов |
| Понедельник | 4 | Губанов |
| Четверг | 4 | Бессонов |
| Пятница | 4 | Бессонов |
| Суббота | 4 | Боева |
| Среда | 5 | Севастьянов |

### 8.	Создать запрос для модификации всех значений столбца с суммарной величиной оплаты, чтобы он содержал истинную сумму, получаемую медперсоналом ( за вычетом налога). Вывести новые значения. ###
``` SQl
UPDATE work_activities wa
    JOIN medpersonal m on m.id = wa.medical_staff
SET wa.payment = wa.payment * (1 - (m.tax / 100));

SELECT wa.contract, wa.payment
FROM work_activities wa;
```

| contract | payment |
| :--- | :--- |
| 51040 | 17200 |
| 51041 | 27000 |
| 51042 | 29700 |
| 51043 | 32400 |
| 51044 | 27000 |
| 51045 | 25800 |
| 51046 | 39600 |
| 51047 | 25200 |
| 51048 | 41800 |
| 51049 | 8600 |
| 51050 | 19800 |
| 51051 | 32400 |
| 51052 | 13300 |
| 51053 | 9000 |
| 51054 | 9900 |
| 51055 | 20900 |
| 51056 | 19800 |

### 9.	Расширить таблицу с данными об операциях столбцом, содержащим величину отчислений в местный бюджет для мед.учреждения, где проводилась операция. Создать запрос для ввода конкретных значений во все строки таблицы операций. ###
``` SQl
UPDATE types_of_operations too
    JOIN work_activities wa on too.id = wa.operation
    JOIN place_of_work pow on pow.id = wa.workplace
SET too.allocations_amount = too.cost * (pow.local_budget_allocations / 100)
WHERE too.id = wa.operation -- Без этого бросало ошибку Unsafe query ¯\_(ツ)_/¯
  AND pow.id = wa.workplace;
  
SELECT name, cost, types_of_operations.allocations_amount
FROM types_of_operations;
```
| name | cost | allocations\_amount |
| :--- | :--- | :--- |
| Наложение гипса | 18000 | 720 |
| Блокада | 14000 | 560 |
| Инъекция поливитаминов | 11000 | 440 |
| Инъекция алоэ | 11000 | 440 |
| ЭКГ | 10000 | 300 |
| УЗИ | 30000 | 1200 |
| Флюорография | 5000 | 500 |

### 10.	Используя операцию IN (NOT IN)  реализовать следующие запросы: ###
#### a)	найти фамилии медперсонала из Навашино, проводивших инъекции в Выксе; ####
``` SQl
SELECT m.last_name
FROM medpersonal m
WHERE m.id IN (
    SELECT wa.medical_staff
    FROM work_activities wa
             JOIN types_of_operations too on too.id = wa.operation
    WHERE too.name LIKE '%Инъекция%'
      AND too.stronghold = 'Выкса'
)
  AND m.adress = 'Навашино';
```
Инъекции в Выксе не делают =)

#### b)	найти те операции, которые не проводились до среды; ####
``` SQl
SELECT too.name
FROM types_of_operations too
WHERE too.id NOT IN (
    SELECT wa.operation
    FROM work_activities wa
    WHERE wa.date IN ('Понедельник', 'Вторник')
);
```
| name |
| :--- |
| Блокада |
| Инъекция поливитаминов |
| ЭКГ |

#### c)	запросы задания 7.с и 7.d. ####
``` SQl
SELECT DISTINCT pow.facility, pow.adress, pow.local_budget_allocations, m.last_name, m.tax
FROM types_of_operations too
         JOIN work_activities wa on too.id = wa.operation
         JOIN medpersonal m on m.id = wa.medical_staff
         JOIN place_of_work pow on pow.id = wa.workplace
WHERE m.tax IN (7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
ORDER BY pow.local_budget_allocations, m.tax;
```
| facility | adress | local\_budget\_allocations | last\_name | tax |
| :--- | :--- | :--- | :--- | :--- |
| Травм.пункт | Лукояново | 3 | Бессонов | 10 |
| Травм. пункт | Выкса | 3 | Бессонов | 10 |
| Травм.пункт | Лукояново | 3 | Губанов | 10 |
| Травм. пункт | Выкса | 3 | Севастьянов | 14 |
| Больница | Навашино | 4 | Бессонов | 10 |
| Больница | Навашино | 4 | Губанов | 10 |
| Больница | Починки | 4 | Губанов | 10 |
| Районная больница | Вознесенское | 10 | Медина | 14 |
| Род. дом | Вознесенское | 12 | Губанов | 10 |
| Род. дом | Вознесенское | 12 | Севастьянов | 14 |


```SQL
SELECT wa.date, too.id, m.last_name
FROM work_activities wa
         JOIN medpersonal m on m.id = wa.medical_staff
         JOIN types_of_operations too on too.id = wa.operation
WHERE too.cost >= 7000
  AND wa.quantity NOT IN (0, 1);
```

| date | id | last\_name |
| :--- | :--- | :--- |
| Понедельник | 1 | Губанов |
| Пятница | 1 | Бессонов |
| Четверг | 3 | Боева |
| Суббота | 3 | Бессонов |
| Понедельник | 4 | Губанов |
| Четверг | 4 | Бессонов |
| Пятница | 4 | Бессонов |
| Суббота | 4 | Боева |
| Среда | 5 | Севастьянов |

### 11.	Используя операции ALL-ANY реализовать следующие запросы: ###
#### a)	найти среди больниц ту, которая имеет наименьший процент отчислений; ####
``` SQl
SELECT pow.facility, pow.adress
FROM place_of_work pow
WHERE local_budget_allocations <= ALL (
    SELECT pow.local_budget_allocations
    FROM place_of_work pow
    WHERE pow.facility LIKE 'Больница'
)
  AND pow.facility LIKE 'Больница';
```
| facility | adress |
| :--- | :--- |
| Больница | Навашино |
| Больница | Починки |

У больниц одинаковый процент.

#### b)	найти педперсонал, проводивший операции с самой малой суммой оплаты; ####
``` SQl
SELECT m.last_name, wa.payment
FROM medpersonal m
JOIN work_activities wa on m.id = wa.medical_staff
WHERE wa.payment <= ALL (
    SELECT wa.payment
    FROM work_activities wa
);
```

| last\_name | payment |
| :--- | :--- |
| Севастьянов | 10000 |
| Бессонов | 10000 |

#### c)	найти цену самой дорогой операции, проведенной в четверг или пятницу; ####
``` SQl
SELECT too.name, too.cost
FROM types_of_operations too
         JOIN work_activities wa on too.id = wa.operation
WHERE wa.date IN ('Четверг', 'Пятница')
  AND too.cost >= ALL (
    SELECT too.cost
    FROM types_of_operations too
             JOIN work_activities wa on too.id = wa.operation
    WHERE wa.date IN ('Четверг', 'Пятница')
);
```
| name | cost |
| :--- | :--- |
| Наложение гипса | 18000 |

#### d)	запрос задания 7.а. ####
``` SQl
SELECT m.last_name, m.adress
FROM medpersonal m
WHERE m.id = ANY (
    SELECT DISTINCT wa.medical_staff
    FROM work_activities wa
             JOIN types_of_operations too on too.id = wa.operation
    WHERE wa.quantity > 1
      AND too.name = 'Наложение гипса'
);
```

| last\_name | adress |
| :--- | :--- |
| Бессонов | Выкса |
| Губанов | Выкса |

### 12.	Используя операцию UNION получить места проживания медпероснала и опероные пункты для операций. ###
``` SQl
SELECT adress
FROM medpersonal
UNION
SELECT stronghold
FROM types_of_operations;
```

| adress |
| :--- |
| Вознесенское |
| Навашино |
| Выкса |
| Починки |

### 13.	Используя операцию EXISTS ( NOT EXISTS ) реализовать нижеследующие запросы. В случае, если для текущего состояния БД запрос будет выдавать пустое множество строк, требуется указать какие добавления в БД необходимо провести. ###
#### a)	определить тот медперсонал, который не работал в субботу; ####
``` SQl
SELECT m.last_name
FROM medpersonal m
WHERE NOT EXISTS(
        SELECT *
        FROM work_activities wa
                 join medpersonal m2 on m2.id = wa.medical_staff
        WHERE wa.date = 'Суббота'
          AND m.id = m2.id
    );
```

| last\_name |
| :--- |
| Медина |
| Севастьянов |

#### b)	найти такие операции, которые проводились всеми врачами в Выксе; ####
``` SQl
SELECT too.name, m.last_name, pow.adress
FROM types_of_operations too
         JOIN work_activities wa on too.id = wa.operation
         JOIN place_of_work pow on pow.id = wa.workplace
         JOIN medpersonal m on m.id = wa.medical_staff
WHERE EXISTS(
              SELECT *
              FROM work_activities wa
                       JOIN types_of_operations too on too.id = wa.operation
                       JOIN place_of_work pow2 on pow2.id = wa.workplace
                       JOIN medpersonal m2 on m2.id = wa.medical_staff
              WHERE pow.adress = 'Выкса'
                AND m2.last_name = m.last_name
          )
```

| name | last\_name | adress |
| :--- | :--- | :--- |
| ЭКГ | Севастьянов | Выкса |
| Флюорография | Бессонов | Выкса |

#### c)	определить те места работы, где не делали УЗИ более раза; ####
``` SQl
SELECT pow.facility, pow.adress
FROM place_of_work pow
WHERE NOT EXISTS
    (
        SELECT *
        FROM work_activities wa
                 JOIN place_of_work pow2 on wa.workplace = pow2.id
                 JOIN types_of_operations too on wa.operation = too.id
        WHERE wa.quantity > 1
          AND too.name = 'УЗИ'
          AND pow2.adress = pow.adress
    );
```

| facility | adress |
| :--- | :--- |
| Районная больница | Вознесенское |
| Травм. пункт | Выкса |
| Больница | Навашино |
| Род. дом | Вознесенское |
| Больница | Починки |
| Травм.пункт | Лукояново |

#### d)	определить места работы, где работали все врачи из чужих населенных пунктов. ####
``` SQl
SELECT pow.facility, pow.adress as facility_adress, m.adress as person_adress
FROM place_of_work pow
         JOIN work_activities wa on pow.id = wa.workplace
         JOIN medpersonal m on m.id = wa.medical_staff
WHERE EXISTS
          (
              SELECT pow.facility, m.adress
              FROM place_of_work pow2
                       JOIN work_activities wa on pow2.id = wa.workplace
                       JOIN medpersonal m on m.id = wa.medical_staff
              WHERE pow.facility != m.adress
                AND pow.facility = pow2.facility
          )
GROUP BY pow.facility, facility_adress, person_adress;
```

| facility | facility\_adress | person\_adress |
| :--- | :--- | :--- |
| Районная больница | Вознесенское | Вознесенское |
| Травм. пункт | Выкса | Навашино |
| Травм. пункт | Выкса | Выкса |
| Больница | Навашино | Выкса |
| Больница | Навашино | Починки |
| Род. дом | Вознесенское | Выкса |
| Род. дом | Вознесенское | Навашино |
| Больница | Починки | Выкса |
| Больница | Починки | Починки |
| Травм.пункт | Лукояново | Выкса |

### 14.	Реализовать запросы с использованием аггрегатных функций: ###
#### a)	найти число различных мест работы для медперсонала, работавшего в мед.учреждениях Выксы; ####
``` SQl
SELECT COUNT(*)
FROM (
         SELECT DISTINCT pow.facility, pow.adress
         FROM work_activities wa
                  JOIN place_of_work pow on pow.id = wa.workplace
         WHERE wa.medical_staff IN
               (
                   SELECT medical_staff
                   FROM work_activities wa2
                            JOIN place_of_work pow2 on pow2.id = wa2.workplace
                   WHERE pow2.adress = 'Выкса'
               )
     ) as fa;
```
| COUNT\(\*\) |
| :--- |
| 4 |

#### b)	определить средний размер налога для медперсонала, производившего иньекции; ####
``` SQl
SELECT AVG(lnt.tax) as average_tax
FROM (
         SELECT DISTINCT m.last_name, m.tax
         FROM medpersonal m
                  JOIN work_activities wa on m.id = wa.medical_staff
                  JOIN types_of_operations too on too.id = wa.operation
         WHERE too.name LIKE '%Инъекция%'
     ) as lnt;
```
| average\_tax |
| :--- |
| 8.3333 |

#### c)	кто из медперсонала делал операцию с минимальной стоимостью; ####
``` SQl
SELECT m.last_name
FROM work_activities wa
         JOIN types_of_operations too on too.id = wa.operation
         JOIN medpersonal m on m.id = wa.medical_staff
WHERE too.cost = (
    SELECT MIN(too2.cost)
    FROM work_activities wa
             JOIN types_of_operations too2 on too2.id = wa.operation
);
```
| last\_name |
| :--- |
| Медина |
| Бессонов |


#### d)	определить количество операций стоимостью не более 15000, проведенных в понедельник Губановым . ####
``` SQl
SELECT COUNT(*)
FROM work_activities wa
         JOIN medpersonal m on m.id = wa.medical_staff
         JOIN types_of_operations too on too.id = wa.operation
WHERE too.cost <= 15000
  AND m.last_name = 'Губанов'
  AND wa.date = 'Понедельник';
```
| COUNT\(\*\) |
| :--- |
| 1 |

### 15.	Используя средства группировки реализовать следующие запросы: ###
#### a)	определить для каждого дня недели и каждой операции сколько раз ее проводили; ####
``` SQl
SELECT wa.date, too.name, SUM(wa.quantity)
FROM work_activities wa
         JOIN types_of_operations too on too.id = wa.operation
GROUP BY wa.date, too.name
ORDER BY wa.date;
```
| date | name | SUM\(wa.quantity\) |
| :--- | :--- | :--- |
| Понедельник | Инъекция алоэ | 3 |
| Понедельник | Наложение гипса | 2 |
| Понедельник | УЗИ | 2 |
| Понедельник | Флюорография | 4 |
| Пятница | Блокада | 1 |
| Пятница | Инъекция алоэ | 2 |
| Пятница | Наложение гипса | 2 |
| Пятница | ЭКГ | 1 |
| Среда | ЭКГ | 3 |
| Суббота | Инъекция алоэ | 3 |
| Суббота | Инъекция поливитаминов | 2 |
| Суббота | Флюорография | 2 |
| Четверг | Блокада | 1 |
| Четверг | Инъекция алоэ | 4 |
| Четверг | Инъекция поливитаминов | 4 |

#### b)	найти для каждого медработника среднюю стоимость всех проведенных им операций; ####
``` SQl
SELECT m.last_name, SUM(too.cost) / SUM(wa.quantity) as average
FROM work_activities wa
         JOIN medpersonal m on m.id = wa.medical_staff
         JOIN types_of_operations too on too.id = wa.operation
GROUP BY m.last_name;
```
| last\_name | average |
| :--- | :--- |
| Медина | 1250.0000 |
| Севастьянов | 5000.0000 |
| Бессонов | 6615.3846 |
| Губанов | 10500.0000 |
| Боева | 5142.8571 |

#### c)	определить те мед.учреждения, где суммарная величина стоимости всех проведенных в них операций была более 30000; ####
``` SQl
SELECT pow.facility, pow.adress, wSS.sum
FROM (
         SELECT wa.workplace, SUM(too.cost) * SUM(wa.quantity) as sum
         FROM work_activities wa
                  JOIN types_of_operations too on too.id = wa.operation
         GROUP BY wa.workplace
     ) as wSS
         JOIN place_of_work pow on pow.id = wSS.workplace
WHERE wSS.sum > 30000
ORDER BY wSS.sum;
```
| facility | adress | sum |
| :--- | :--- | :--- |
| Травм. пункт | Выкса | 75000 |
| Род. дом | Вознесенское | 80000 |
| Больница | Починки | 116000 |
| Травм.пункт | Лукояново | 580000 |
| Больница | Навашино | 924000 |

#### d)	для каждого дня недели найти число проведенных в этот день операций. ####
``` SQl
SELECT date, SUM(quantity)
FROM work_activities
GROUP BY date;
```

| date | SUM\(quantity\) |
| :--- | :--- |
| Понедельник | 11 |
| Среда | 3 |
| Четверг | 9 |
| Пятница | 6 |
| Суббота | 7 |
