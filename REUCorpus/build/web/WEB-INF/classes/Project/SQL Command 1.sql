CREATE TABLE Counselor (
    id SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT,
    firstName VARCHAR (50),
    nickName VARCHAR (50),
    lastName VARCHAR (50),
    telephone VARCHAR (25),
    email VARCHAR (50),
    memberSince DATE DEFAULT '0000-00-00',
    PRIMARY KEY (id)
            );