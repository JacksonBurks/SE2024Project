DROP TABLE wofFamousLandmarks;
DROP TABLE wofHistoricalFigures;
DROP TABLE wofLiteraryClassics;
DROP TABLE wofHistoricalEvents;
DROP TABLE wofFamousInventions;
DROP TABLE wofFamousPaintings;
DROP TABLE wofLiteraryCharacters;
DROP TABLE wofFamousExplorers;
DROP TABLE wofFamousDuos;
DROP TABLE wofMovies;
DROP TABLE IF EXISTS User;

CREATE TABLE User (
    Username VARCHAR(50) PRIMARY KEY,
    Password VARBINARY(16)
);

-- Famous Landmarks
CREATE TABLE wofFamousLandmarks (
    landmark_id INT AUTO_INCREMENT PRIMARY KEY,
    words VARCHAR(100)
);

INSERT INTO wofFamousLandmarks (words) VALUES
('Eiffel Tower'),
('Statue of Liberty'),
('Great Wall of China'),
('Taj Mahal'),
('Sydney Opera House'),
('Machu Picchu'),
('Colosseum'),
('Petra'),
('Christ the Redeemer'),
('Pyramids of Giza');

-- Movies
CREATE TABLE wofMovies (
    movie_id INT AUTO_INCREMENT PRIMARY KEY,
    words VARCHAR(100)
);

INSERT INTO wofMovies (words) VALUES
('Titanic'),
('The Godfather'),
('Avatar'),
('Star Wars'),
('Jurassic Park'),
('The Matrix'),
('Forrest Gump'),
('Inception'),
('The Shawshank Redemption'),
('Pulp Fiction');

-- Historical Figures
CREATE TABLE wofHistoricalFigures (
    figure_id INT AUTO_INCREMENT PRIMARY KEY,
    words VARCHAR(100)
);

INSERT INTO wofHistoricalFigures (words) VALUES
('Leonardo da Vinci'),
('Cleopatra'),
('Alexander the Great'),
('Joan of Arc'),
('Nelson Mandela'),
('Marie Curie'),
('Genghis Khan'),
('Queen Elizabeth I'),
('Abraham Lincoln'),
('Mahatma Gandhi');

-- Literary Classics
CREATE TABLE wofLiteraryClassics (
    classic_id INT AUTO_INCREMENT PRIMARY KEY,
    words VARCHAR(100)
);

INSERT INTO wofLiteraryClassics (words) VALUES
('War and Peace'),
('To Kill a Mockingbird'),
('One Hundred Years of Solitude'),
('Moby-Dick'),
('Ulysses'),
('Crime and Punishment'),
('The Odyssey'),
('The Divine Comedy'),
('Pride and Prejudice'),
('The Catcher in the Rye');

-- Historical Events
CREATE TABLE wofHistoricalEvents (
    event_id INT AUTO_INCREMENT PRIMARY KEY,
    words VARCHAR(100)
);

INSERT INTO wofHistoricalEvents (words) VALUES
('The Renaissance in Europe'),
('The French Revolution'),
('The American Civil War'),
('The Industrial Revolution'),
('The Apollo 11 Moon Landing'),
('The Fall of the Berlin Wall'),
('The Great Depression'),
('The Cuban Missile Crisis'),
('The World War II Holocaust'),
('The Age of Exploration');

-- Famous Inventions
CREATE TABLE wofFamousInventions (
    invention_id INT AUTO_INCREMENT PRIMARY KEY,
    words VARCHAR(100)
);

INSERT INTO wofFamousInventions (words) VALUES
('Electricity'),
('Printing Press'),
('Telephone'),
('Light Bulb'),
('Airplane'),
('Computer'),
('Internet'),
('Antibiotics'),
('Television'),
('Automobile');

-- Famous Paintings
CREATE TABLE wofFamousPaintings (
    painting_id INT AUTO_INCREMENT PRIMARY KEY,
    words VARCHAR(100)
);

INSERT INTO wofFamousPaintings (words) VALUES
('Mona Lisa'),
('The Starry Night'),
('The Last Supper'),
('Guernica'),
('The Scream'),
('The Persistence of Memory'),
('Girl with a Pearl Earring'),
('The Creation of Adam'),
('The Birth of Venus'),
('American Gothic');

-- Literary Characters
CREATE TABLE wofLiteraryCharacters (
    character_id INT AUTO_INCREMENT PRIMARY KEY,
    words VARCHAR(100)
);

INSERT INTO wofLiteraryCharacters (words) VALUES
('Sherlock Holmes'),
('Harry Potter'),
('Elizabeth Bennet'),
('Huckleberry Finn'),
('Ebenezer Scrooge'),
('Don Quixote'),
('Holden Caulfield'),
('Jay Gatsby'),
('Captain Ahab'),
('Atticus Finch');

-- Famous Explorers
CREATE TABLE wofFamousExplorers (
    explorer_id INT AUTO_INCREMENT PRIMARY KEY,
    words VARCHAR(100)
);

INSERT INTO wofFamousExplorers (words) VALUES
('Christopher Columbus'),
('Marco Polo'),
('Ferdinand Magellan'),
('Vasco da Gama'),
('Lewis and Clark'),
('James Cook'),
('Roald Amundsen'),
('Sir Ernest Shackleton'),
('David Livingstone'),
('Neil Armstrong');

-- Famous Duos
CREATE TABLE wofFamousDuos (
    duo_id INT AUTO_INCREMENT PRIMARY KEY,
    words VARCHAR(100)
);

INSERT INTO wofFamousDuos (words) VALUES
('Bonnie and Clyde'),
('Romeo and Juliet'),
('Batman and Robin'),
('Tom and Jerry'),
('Simon and Garfunkel'),
('Abbott and Costello'),
('Thelma and Louise'),
('Butch Cassidy and The Sundance Kid'),
('Laurel and Hardy'),
('Lennon and McCartney');


--SQL statement to get word
--SELECT * FROM category ORDER BY RAND() LIMIT 1;
--Category should be randomly selected from our array of categories