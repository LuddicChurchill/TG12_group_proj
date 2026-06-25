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

/*TESTDATEN highscore*/
INSERT INTO SnakeSpiel (name, schwierigkeit, spieleNr) VALUES
('Snake - das Original', 2, 1),
('Snake II', 2, 2),
('TG-Snake', 4, 3),
('Snake Extreme', 5, 4),
('Classic Snake', 1, 5),
('Speed Snake', 3, 6);

INSERT INTO Spieler (name, passwort, spielerNr) VALUES
('max_mustermann', 'test123', 1),
('lisa_schmidt', 'test123', 2),
('tom_weber', 'test123', 3),
('anna_klein', 'test123', 4),
('admin', 'admin123', 0);

INSERT INTO Highscore (spielerID, spielID, highscore) VALUES
(1, 1, 82),
(1, 2, 45),
(1, 3, 4242),
(2, 1, 120),
(2, 2, 67),
(2, 4, 300),
(3, 1, 55),
(3, 5, 200),
(4, 3, 1500),
(4, 6, 80);