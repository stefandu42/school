CREATE TABLE classes(
    id_class SERIAL PRIMARY KEY,
    name varchar(30) NOT NULL CHECK(name <> '') UNIQUE
);

CREATE TABLE students(
    id_student SERIAL PRIMARY KEY,
    firstname varchar(50) NOT NULL CHECK(firstname <> ''),
    surname varchar(50) NOT NULL CHECK(surname <> ''),
    id_class INTEGER REFERENCES classes (id_class) NOT NULL
);