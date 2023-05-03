USE sp3;
DROP TABLE IF EXISTS movie_genre;
DROP TABLE IF EXISTS series_genre;
DROP TABLE IF EXISTS series_genre;
DROP TABLE IF EXISTS series_seasons;
DROP TABLE IF EXISTS user_watched_series;
DROP TABLE IF EXISTS user_watched_movies;
DROP TABLE IF EXISTS user_to_watch_series;
DROP TABLE IF EXISTS user_to_watch_movies;
DROP TABLE IF EXISTS movies;
DROP TABLE IF EXISTS genres;
DROP TABLE IF EXISTS series;
DROP TABLE IF EXISTS users;

CREATE TABLE genres(
    ID INT NOT NULL AUTO_INCREMENT,
    genre VARCHAR(45) NOT NULL,
    PRIMARY KEY (ID)
);
CREATE TABLE movies(
    ID INT NOT NULL AUTO_INCREMENT,
    title VARCHAR(45) NOT NULL,
    year INT NOT NULL,
    rating FLOAT NOT NULL,
    PRIMARY KEY (ID)
);
CREATE TABLE series(
    ID INT NOT NULL AUTO_INCREMENT,
    title VARCHAR(45) NOT NULL,
    startyear INT NOT NULL,
    endyear INT,
    rating FLOAT NOT NULL,
    PRIMARY KEY (ID)
);

CREATE TABLE users(
    ID INT NOT NULL AUTO_INCREMENT,
    username VARCHAR(45) NOT NULL,
    password VARCHAR(45) NOT NULL,
    PRIMARY KEY (ID)
);

CREATE TABLE movie_genre(
    movieID INT NOT NULL,
    genreID INT NOT NULL,
    FOREIGN KEY (movieID) REFERENCES movies(ID),
    FOREIGN KEY (genreID) REFERENCES genres(ID)
);
CREATE TABLE series_genre(
    seriesID INT NOT NULL,
    genreID INT NOT NULL,
    FOREIGN KEY (seriesID) REFERENCES series(ID),
    FOREIGN KEY (genreID) REFERENCES genres(ID)
);
CREATE TABLE series_seasons(
    seriesID INT NOT NULL,
    seasonNumber INT NOT NULL,
    seasonNumberOfEpisodes INT NOT NULL,
    FOREIGN KEY (seriesID) REFERENCES series(ID)
);
CREATE TABLE user_watched_movies(
    movieID INT NOT NULL,
    userID INT NOT NULL,
    FOREIGN KEY (movieID) REFERENCES movies(ID),
    FOREIGN KEY (userID) REFERENCES users(ID)
);
CREATE TABLE user_watched_series(
    seriesID INT NOT NULL,
    userID INT NOT NULL,
    FOREIGN KEY (seriesID) REFERENCES series(ID),
    FOREIGN KEY (userID) REFERENCES users(ID)
);
CREATE TABLE user_to_watch_movies(
    movieID INT NOT NULL,
    userID INT NOT NULL,
    FOREIGN KEY (movieID) REFERENCES movies(ID),
    FOREIGN KEY (userID) REFERENCES users(ID)
);
CREATE TABLE user_to_watch_series(
    seriesID INT NOT NULL,
    userID INT NOT NULL,
    FOREIGN KEY (seriesID) REFERENCES series(ID),
    FOREIGN KEY (userID) REFERENCES users(ID)
);
BEGIN;
INSERT INTO genres(genre) 
VALUES("Action");
INSERT INTO genres( genre)
VALUES("Adventure");
INSERT INTO genres(genre) 
VALUES("Biography");
INSERT INTO genres(genre) 
VALUES("Comedy");
INSERT INTO genres(genre) 
VALUES("Crime");
INSERT INTO genres(genre) 
VALUES("Drama");
INSERT INTO genres(genre) 
VALUES("Family");
INSERT INTO genres(genre) 
VALUES("Fantasy");
INSERT INTO genres(genre) 
VALUES("Film-Noir");
INSERT INTO genres(genre) 
VALUES("History");
INSERT INTO genres(genre) 
VALUES("Horror");
INSERT INTO genres(genre) 
VALUES("Musical");
INSERT INTO genres(genre) 
VALUES("Music");
INSERT INTO genres(genre) 
VALUES("Mystery");
INSERT INTO genres(genre) 
VALUES("Romance");
INSERT INTO genres(genre) 
VALUES("Sci-fi");
INSERT INTO genres(genre) 
VALUES("Sport");
INSERT INTO genres(genre) 
VALUES("Thriller");
INSERT INTO genres(genre) 
VALUES("War");
INSERT INTO genres(genre) 
VALUES("Western");
COMMIT;

