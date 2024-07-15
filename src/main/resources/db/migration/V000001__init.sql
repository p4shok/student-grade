-- Создание таблицы students
CREATE TABLE students (
                          id BIGINT NOT NULL PRIMARY KEY,
                          group_id BIGINT NOT NULL,
                          fullname VARCHAR(255) NOT NULL,
                          age INTEGER,
                          email VARCHAR(255) NOT NULL,
                          phone VARCHAR(20)
);

-- Создание таблицы objects
CREATE TABLE objects (
                         id BIGINT NOT NULL PRIMARY KEY,
                         teacher_id BIGINT NOT NULL,
                         name VARCHAR(255) NOT NULL
);

-- Создание таблицы marks
CREATE TABLE marks (
                       id BIGINT NOT NULL PRIMARY KEY,
                       student_id BIGINT NOT NULL,
                       object_id BIGINT NOT NULL,
                       mark INTEGER NOT NULL,
                       FOREIGN KEY (student_id) REFERENCES students (id) ON DELETE CASCADE,
                       FOREIGN KEY (object_id) REFERENCES objects (id) ON DELETE CASCADE
);