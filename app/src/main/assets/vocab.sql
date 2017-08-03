BEGIN TRANSACTION;
CREATE TABLE "tblWordType" (
	`intTypeId`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	`strTypeName`	TEXT NOT NULL UNIQUE,
	`strAbbr`	TEXT NOT NULL UNIQUE
);
INSERT INTO `tblWordType` (intTypeId,strTypeName,strAbbr) VALUES (1,'NOUN','n.'),
 (2,'PRONOUN','pron.'),
 (3,'VERB','v.'),
 (4,'ADJECTIVE','adj.'),
 (5,'ADVERB','adv.'),
 (6,'PREPOSITION','prep.'),
 (7,'CONJUNCTION','conj.'),
 (8,'INTERJECTION','interj.');
CREATE TABLE "tblWord" (
	`intWordId`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	`strWord`	TEXT NOT NULL,
	`intTypeId`	INTEGER NOT NULL,
	`strMeaning`	TEXT NOT NULL,
	`intFrequency`	INTEGER DEFAULT 0,
	`tsCreateTime`	TEXT DEFAULT 'strftime("%s", "now")',
	`tsLastAccess`	TEXT DEFAULT 'strftime("%s", "now")',
	FOREIGN KEY(`intTypeId`) REFERENCES `tblWordType`(`intTypeId    `)
);
CREATE TABLE `tblSynonym` ( `intSynonymId` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, `intMainWordId` INTEGER NOT NULL, `intSynonymWordId` INTEGER NOT NULL ,
FOREIGN KEY (intMainWordId) REFERENCES tblWord(intWordId),FOREIGN KEY (intSynonymWordId) REFERENCES tblWord(intWordId));
CREATE TABLE "tblSentence" (
	`intSentenceId`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	`intWordId`	INTEGER,
	`strSentence`	TEXT,
	`tsCreateTime`	TEXT DEFAULT 'strftime("%s", "now")',
	FOREIGN KEY(`intWordId`) REFERENCES `tblWord`(`intWordId`)
);
CREATE TABLE `tblAntonym` ( `intAntonymId` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, `intMainWordId` INTEGER NOT NULL, `intAntonymWordId` INTEGER NOT NULL ,
FOREIGN KEY (intMainWordId) REFERENCES tblWord(intWordId),FOREIGN KEY (intAntonymWordId) REFERENCES tblWord(intWordId));
COMMIT;
