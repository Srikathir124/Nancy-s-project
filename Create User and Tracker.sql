create table User(
	`UserId` varchar(50) primary key,
    `Password` varchar(5),
    `RoleId` tinyint
);
create table Tracker(
	`ID` int primary key,
    `DownloadLogs` varchar(100)
);