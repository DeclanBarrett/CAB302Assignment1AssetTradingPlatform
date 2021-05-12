
CREATE DATABASE trading_platform;

CREATE TABLE OrgHasQuantity (
OrganisationalUnitName VARCHAR(60) NOT NULL PRIMARY KEY,
AssetName VARCHAR(60) NOT NULL,
AssetQuantity INT NOT NULL,
FOREIGN KEY(OrganisationalUnitName) REFERENCES OrganisationalUnit(OrganisationalUnitName),
FOREIGN KEY(AssetName) REFERENCES Assets(AssetName)
);

CREATE TABLE OrganisationalUnit(
OrganisationalUnitName VARCHAR(60) NOT NULL PRIMARY KEY,
AmountCredits INT NOT NULL
);

CREATE TABLE Users(
UserName VARCHAR(60) NOT NULL PRIMARY KEY,
OrganisationalUnit VARCHAR(60) NOT NULL,
AccountType ENUM('User', 'UnitLeader', 'SystemAdmin') NOT NULL,
HashedPassword VARCHAR(60) NOT NULL,
Salt VARCHAR(60) NOT NULL,
FOREIGN KEY(OrganisationalUnit) REFERENCES OrganisationalUnit(OrganisationalUnitName)
);

CREATE TABLE Order1(
OrderID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
OrganisationalUnitName VARCHAR(60) NOT NULL,
PlaceDateMilSecs INT NOT NULL,
AssetQuantity INT NOT NULL,
AssetName VARCHAR(60) NOT NULL,
OrderType ENUM('BUY', 'SELL') NOT NULL,
FOREIGN KEY(OrganisationalUnitName) REFERENCES OrganisationalUnit(OrganisationalUnitName),
FOREIGN KEY(AssetName) REFERENCES Assets(AssetName)
);

CREATE TABLE Assets(
AssetName VARCHAR(60) NOT NULL PRIMARY KEY
);

CREATE TABLE Trade(
TradeID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
BuyerOrgName VARCHAR(60) NOT NULL,
SellerOrgName VARCHAR(60) NOT NULL,
TradeDateMilSecs INT NOT NULL,
AssetQuantity INT NOT NULL,
AssetName VARCHAR(60) NOT NULL,
FOREIGN KEY(BuyerOrgName) REFERENCES OrganisationalUnit(OrganisationalUnitName),
FOREIGN KEY(SellerOrgName) REFERENCES OrganisationalUnit(OrganisationalUnitName)
);


INSERT INTO Users VALUES ();


DESCRIBE table;
SHOW TABLES;