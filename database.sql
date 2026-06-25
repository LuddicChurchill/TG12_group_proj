DROP DATABASE IF EXISTS snake_portal;
CREATE DATABASE snake_portal;
USE snake_portal;

/*TABELLEN*/
CREATE TABLE Spieler (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL UNIQUE,
    passwort VARCHAR(255) NOT NULL,
    spielerNr INT NOT NULL
);

CREATE TABLE SnakeSpiel (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL UNIQUE,
    schwierigkeit INT NOT NULL CHECK (schwierigkeit BETWEEN 1 AND 10),
    spieleNr INT NOT NULL
);

CREATE TABLE Highscore (
    id INT PRIMARY KEY AUTO_INCREMENT,
    spielerID INT NOT NULL,
    spielID INT NOT NULL,
    highscore INT NOT NULL,
    erstellungsdatum DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (spielerID) REFERENCES Spieler(id) ON DELETE CASCADE,
    FOREIGN KEY (spielID) REFERENCES SnakeSpiel(id) ON DELETE CASCADE,
    UNIQUE KEY unique_highscore (spielerID, spielID)
);

CREATE INDEX idx_spieler_name ON Spieler(name);
CREATE INDEX idx_highscore_spieler ON Highscore(spielerID);
CREATE INDEX idx_highscore_spiel ON Highscore(spielID);


