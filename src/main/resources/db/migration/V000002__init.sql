-- Изменение имени колонки "GROUP" на "group"
ALTER TABLE students RENAME COLUMN "GROUP" TO "group";

-- Установка NOT NULL для колонки id
ALTER TABLE students ALTER COLUMN id SET NOT NULL;

-- Добавление автоинкрементного значения для колонки id
ALTER TABLE students ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY;

-- Вставка данных с правильными значениями для колонки "group_id"
INSERT INTO students(group_id, fullname, age, email, phone) VALUES (1, 'Дюмин Кирилл Олегович', 17, 'dumin@gmail.com', '89323234132');