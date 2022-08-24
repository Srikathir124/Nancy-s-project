create table Users(
	`UserId` varchar(50) primary key,
    `Password` varchar(5),
    `RoleId` tinyint
);
create table Tracker(
	`ID` int primary key auto_increment,
    `DownloadLogs` varchar(100)
);