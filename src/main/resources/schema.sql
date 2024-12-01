CREATE TABLE household
(
    eircode                 VARCHAR(255) NOT NULL PRIMARY KEY,
    number_of_occupants     INT          NOT NULL CHECK (number_of_occupants >= 1),
    max_number_of_occupants INT          NOT NULL CHECK (max_number_of_occupants >= 1),
    owner_occupied          BOOLEAN      NOT NULL
);

CREATE TABLE pet
(
    id                INT AUTO_INCREMENT PRIMARY KEY,
    name              VARCHAR(255) NOT NULL,
    animal_type       VARCHAR(255) NOT NULL,
    breed             VARCHAR(255) NOT NULL,
    age               INT          NOT NULL CHECK (age >= 0),
    household_eircode VARCHAR(255) NOT NULL,
    CONSTRAINT fk_household FOREIGN KEY (household_eircode) REFERENCES household (eircode) ON DELETE CASCADE
);

CREATE TABLE users
(
    id       SERIAL PRIMARY KEY,
    username VARCHAR(50)  NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role     VARCHAR(20)  NOT NULL,
    unlocked BOOLEAN      NOT NULL DEFAULT TRUE
);