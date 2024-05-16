INSERT INTO roles
    (role)
VALUES
    ('ADMIN'),
    ('USER');

INSERT INTO users
    (username, pass, image, forename, surname, email, birth_date, creation_date, last_login, active)
VALUES
    ('tokioschool', '$2a$10$.JGCNuoStUWFceU0S2oh.eoI5c3dIwzm5KiYvzoJW7KGrIOBb41yq', 'tokioschool.jpeg'
        , 'Tokio', 'School', 'tokioschool@gmail.com', '2010-06-06', '2021-12-03', '2022-01-01', 1);

INSERT INTO user_roles
    (user_id, role_id)
VALUES
    ((SELECT id FROM users WHERE username = 'tokioschool'), (SELECT id FROM roles WHERE role = 'ADMIN'));

INSERT INTO people
    (forename, surname, type)
VALUES
    ('Peter', 'Jackson', 'DIRECTOR'),
    ('Orlando', 'Bloom', 'ACTOR'),
    ('Ridley', 'Scott', 'DIRECTOR');

INSERT INTO films
    (title, year, duration, avg_score, poster, migrate, user_id, director_id, uri)
VALUES
    ('The Fellowship of the Ring', 2001, 178, 4,'the-fellowship-of-the-ring.jpg', 0,(SELECT id FROM users WHERE username = 'tokioschool'), (SELECT id FROM people WHERE forename = 'Peter' AND surname ='Jackson'), 'the-fellowship-of-the-ring-2001'),
    ('Kingdom of Heaven', 2005, 190, 3,'kingdom-of-heaven.jpg', 1, (SELECT id FROM users WHERE username = 'tokioschool'), (SELECT id FROM people WHERE forename = 'Ridley' AND surname ='Scott'), 'kingdom-of-heaven-2005');

INSERT INTO film_actors
    (film_id, actor_id)
VALUES
    ((SELECT id FROM films WHERE title = 'The Fellowship of the Ring'), (SELECT id FROM people WHERE forename = 'Orlando' AND surname = 'Bloom')),
    ((SELECT id FROM films WHERE title = 'Kingdom of Heaven'), (SELECT id FROM people WHERE forename = 'Orlando' AND surname = 'Bloom'));

INSERT INTO scores
    (value, film_id, user_id)
VALUES
    (4, (SELECT id FROM films WHERE title = 'The Fellowship of the Ring'), (SELECT id FROM users WHERE username = 'tokioschool')),
    (5, (SELECT id FROM films WHERE title = 'The Fellowship of the Ring'), (SELECT id FROM users WHERE username = 'tokioschool')),
    (4, (SELECT id FROM films WHERE title = 'The Fellowship of the Ring'), (SELECT id FROM users WHERE username = 'tokioschool')),
    (2, (SELECT id FROM films WHERE title = 'Kingdom of Heaven'), (SELECT id FROM users WHERE username = 'tokioschool')),
    (3, (SELECT id FROM films WHERE title = 'Kingdom of Heaven'), (SELECT id FROM users WHERE username = 'tokioschool')),
    (2, (SELECT id FROM films WHERE title = 'Kingdom of Heaven'), (SELECT id FROM users WHERE username = 'tokioschool'));

INSERT INTO reviews
    (title, film_id, user_id, text_review)
VALUES
    ('Test Review', (SELECT id FROM films WHERE title = 'Kingdom of Heaven'), (SELECT id FROM users WHERE username = 'tokioschool'), 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec vulputate facilisis velit eget feugiat. Vivamus nunc felis, interdum accumsan sapien sed, feugiat scelerisque ante. Pellentesque fermentum metus ac venenatis tristique. Ut feugiat ex hendrerit, blandit turpis id, semper magna. Suspendisse velit libero, fermentum at dolor a, rhoncus interdum massa. Ut mattis eget arcu at commodo. Integer rutrum diam est, in ornare ex malesuada eleifend. Phasellus eleifend suscipit enim, ac rhoncus diam tristique a. Sed pharetra quam quis dolor sagittis posuere. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Mauris tincidunt bibendum quam, eu porta mauris tincidunt eu. Nullam a ante magna. Donec lacinia ipsum lacus, sit amet malesuada nulla facilisis ut. Nullam euismod rhoncus erat. Aliquam nec ligula ac magna volutpat ultricies.');
