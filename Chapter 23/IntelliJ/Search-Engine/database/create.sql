CREATE DATABASE SearchEngine DEFAULT CHARACTER SET 'utf8'
  DEFAULT COLLATE 'utf8_unicode_ci';

USE SearchEngine;

CREATE TABLE UserPrincipal (
  UserId BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  Username VARCHAR(30) NOT NULL,
  UNIQUE KEY UserPrincipal_Username (Username)
) ENGINE = InnoDB;

CREATE TABLE Post (
  PostId BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  UserId BIGINT UNSIGNED NOT NULL,
  Title VARCHAR(100) NOT NULL,
  Body TEXT,
  Keywords VARCHAR(100) NOT NULL,
  CONSTRAINT Post_User FOREIGN KEY (UserId)
    REFERENCES UserPrincipal (UserId) ON DELETE CASCADE
) ENGINE = InnoDB;

INSERT INTO UserPrincipal (Username) VALUES ('Nicholas');
INSERT INTO UserPrincipal (Username) VALUES ('Sarah');
INSERT INTO UserPrincipal (Username) VALUES ('Mike');
INSERT INTO UserPrincipal (Username) VALUES ('John');
