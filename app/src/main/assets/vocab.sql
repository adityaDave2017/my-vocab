CREATE TABLE "tblWordType" (
	`strTypeName`	TEXT,
	PRIMARY KEY(`strTypeName`)
);

INSERT INTO `tblWordType` (strTypeName) VALUES ('Noun'),
 ('Pronoun'),
 ('Verb'),
 ('Adjective'),
 ('Adverb'),
 ('Preposition'),
 ('Conjunction'),
 ('Interjection');

CREATE TABLE IF NOT EXISTS `tblWord` (
	`intWordId`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	`strWord`	TEXT NOT NULL,
	`strType`	TEXT NOT NULL,
	`strMeaning`	TEXT NOT NULL,
	`intFrequency`	INTEGER DEFAULT 0,
	`tsCreateTime`	DATETIME DEFAULT CURRENT_TIMESTAMP,
	`tsLastAccess`	DATETIME DEFAULT CURRENT_TIMESTAMP,
	FOREIGN KEY(`strType`) REFERENCES `tblWordType`(`strTypeName`)
);

CREATE TABLE IF NOT EXISTS `tblSentence` (
	`intSentenceId`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	`intWordId`	INTEGER,
	`strSentence`	TEXT,
	`tsCreateTime`	DATETIME DEFAULT CURRENT_TIMESTAMP,
	FOREIGN KEY(`intWordId`) REFERENCES `tblWord`(`intWordId`)
);

CREATE TABLE IF NOT EXISTS `tblSynonym` (
    `intSynonymId` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    `intMainWordId` INTEGER NOT NULL,
    `intSynonymWordId` INTEGER NOT NULL,
    FOREIGN KEY (intMainWordId) REFERENCES tblWord(intWordId),
    FOREIGN KEY (intSynonymWordId) REFERENCES tblWord(intWordId)
);

CREATE TABLE IF NOT EXISTS `tblAntonym` (
    `intAnotnymId` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    `intMainWordId` INTEGER NOT NULL,
    `intAntonymWordId` INTEGER NOT NULL,
    FOREIGN KEY (intMainWordId) REFERENCES tblWord(intWordId),
    FOREIGN KEY (intAntonymWordId) REFERENCES tblWord(intWordId)
);