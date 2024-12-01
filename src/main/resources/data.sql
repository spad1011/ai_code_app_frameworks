-- Household Entries
INSERT INTO household (eircode, number_of_occupants, max_number_of_occupants, owner_occupied)
VALUES ('D01F123', 3, 5, TRUE),
       ('A02G456', 2, 4, FALSE),
       ('B03H789', 1, 2, TRUE),
       ('C04I012', 4, 4, TRUE),
       ('E05J345', 2, 6, FALSE),
       ('F06K678', 5, 5, TRUE),
       ('G07L901', 1, 3, FALSE),
       ('H08M234', 3, 4, TRUE),
       ('I09N567', 2, 2, TRUE),
       ('J10O890', 6, 6, TRUE);

-- Pet Entries
INSERT INTO pet (name, animal_type, breed, age, household_eircode)
VALUES ('Nibbles', 'Hamster', 'Syrian Hamster', 5, 'D01F123'),
       ('Mittens', 'Cat', 'Siamese', 5, 'D01F123'),
       ('Charlie', 'Dog', 'Labrador', 2, 'A02G456'),
       ('Whiskers', 'Cat', 'Persian', 4, 'B03H789'),
       ('Bubbles', 'Fish', 'Goldfish', 1, 'C04I012'),
       ('Daisy', 'Dog', 'Beagle', 6, 'E05J345'),
       ('Max', 'Rabbit', 'Lionhead', 3, 'F06K678'),
       ('Coco', 'Bird', 'Parrot', 7, 'G07L901'),
       ('Rocky', 'Dog', 'Bulldog', 4, 'H08M234'),
       ('Luna', 'Cat', 'Maine Coon', 2, 'I09N567');

INSERT INTO users (username, password, role, unlocked)
VALUES ('admin', '$2a$10$9c09amlCRG7VT4hWb70HhebHLabWqU5OEMs6GDN0ba8VTPj7G7pWi', 'ROLE_ADMIN',
        TRUE), -- password: admin123
       ('user1', '$2a$10$yCS7h1SxkBZeXIzncXLIiuFz8ge0mjlb6fKlmJ3ipcjH0ZyCNyqD2', 'ROLE_USER',
        TRUE), -- password: user123
       ('lockedUser', '$2a$10$FlVSugea7PZPawaItchCwuHlaJFy8o/diYRDHw80bw6yFAZESvsAS', 'ROLE_USER',
        FALSE); -- password: locked123