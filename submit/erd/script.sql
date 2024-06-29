CREATE TABLE `Users` (
                         `UniqueUserID`	varchar(50)	PRIMARY KEY NOT NULL,
                         `UserID`	varchar(50)	NOT NULL	COMMENT 'Unique UUID',
                         `UserName`	varchar(50)	NOT NULL,
                         `UserPassword`	varchar(100)	NOT NULL	COMMENT 'mysql password 사용',
                         `UserBirth`	varchar(8)	NOT NULL,
                         `UserTelephone`	varchar(15)	NOT NULL,
                         `UserAuth`	varchar(10)	NOT NULL	COMMENT 'ROLE_ADMIN, ROLE_USER',
                         `CreatedAt`	datetime	NOT NULL,
                         `LatestLoginAt`	datetime	NULL	DEFAULT NULL,
                         `State`	varchar(10)	NOT NULL	COMMENT 'ACTIVE, DEACTIVE, DELETED',
                         `DeletedAt`	datetime	NULL
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `Products` (
                            `ProductID`	int	NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT 'auto_increment',
                            `ModelNumber`	varchar(10)	NOT NULL,
                            `ModelName`	varchar(120)	NOT NULL,
                            `Banner`	varchar(50)	NULL,
                            `Thumbnail`	varchar(100)	DEFAULT '/resources/thumbnails/no-image.png',
                            `Price`	int	NOT NULL,
                            `DiscountRate` int NOT NULL,
                            `Sale`	int	NOT NULL,
                            `Description`	varchar(300)	NULL,
                            `Stock`	int	NOT NULL	DEFAULT 0,
                            `ViewCount`	int	NOT NULL	DEFAULT 0,
                            `OrderCount`	int	NOT NULL	DEFAULT 0,
                            `Brand`	varchar(50)	NULL,
                            `State`	varchar(10)	NULL,
                            `CreatedAt`	datetime	NULL,
                            `DeletedAt`	datetime	NULL
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `Categories` (
                              `CategoryID`	int	NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT 'auto_increment',
                              `CategoryName`	varchar(50)	NOT NULL
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `Reviews` (
                           `ReviewID`	int	NOT NULL PRIMARY KEY AUTO_INCREMENT,
                           `Rating`	int	NOT NULL	COMMENT '1 ~ 5',
                           `Comments`	text	NULL,
                           `Recommend`	int	NOT NULL	DEFAULT 0,
                           `ProductID`	int	NOT NULL	COMMENT 'auto_increment',
                           `UniqueUserID`	varchar(50)	NOT NULL,

                           CONSTRAINT `FK_Reviews_Products` FOREIGN KEY(`ProductID`) REFERENCES Products(ProductID) ON DELETE CASCADE ON UPDATE CASCADE,
                           CONSTRAINT `FK_Reviews_Users` FOREIGN KEY(UniqueUserID) REFERENCES Users(UniqueUserID) ON DELETE CASCADE ON UPDATE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `Addresses` (
                             `AddressID`	int	NOT NULL PRIMARY KEY AUTO_INCREMENT,
                             `Address`	text	NOT NULL,
                             `AddressDetail`	text	NULL,
                             `zipcode`	varchar(6)	NOT NULL,
                             `Name`	varchar(20)	NOT NULL,
                             `Telephone`	varchar(15)	NOT NULL,
                             `Request`	varchar(100)	NULL,
                             `isDefault`	boolean	NOT NULL,
                             `UniqueUserID`	varchar(50)	NOT NULL,

                             CONSTRAINT `FK_Addresses_Users` FOREIGN KEY(UniqueUserID) REFERENCES Users(UniqueUserID) ON DELETE CASCADE ON UPDATE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `Orders` (
                          `OrderID`	int	NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT 'auto_increment',
                          `OrderDate`	datetime	NOT NULL,
                          `ShipDate`	datetime	NULL,
                          `Price`	int	NOT NULL,
                          `UniqueUserID`	varchar(50)	NOT NULL,
                          `AddressID`	int	NOT NULL,

                          CONSTRAINT `FK_Orders_Users` FOREIGN KEY(UniqueUserID) REFERENCES Users(UniqueUserID) ON DELETE CASCADE ON UPDATE CASCADE,
                          CONSTRAINT `FK_Orders_Addresses` FOREIGN KEY(AddressID) REFERENCES Addresses(AddressID) ON DELETE CASCADE ON UPDATE CASCADE,
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `OrderDetails` (
                                `OrderID`	int	NOT NULL	COMMENT 'auto_increment',
                                `ProductID`	int	NOT NULL	COMMENT 'auto_increment',
                                `Quantity`	int	NOT NULL,
                                `UnitPrice`	int	NOT NULL	COMMENT '예전 가격 기록 로그용',

                                PRIMARY KEY (`OrderID`, `ProductID`),
                                CONSTRAINT `FK_OrderDetails_Orders` FOREIGN KEY(OrderID) REFERENCES Orders(OrderID) ON DELETE CASCADE ON UPDATE CASCADE,
                                CONSTRAINT `FK_OrderDetails_Products` FOREIGN KEY(ProductID) REFERENCES Products(ProductID) ON DELETE CASCADE ON UPDATE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `ShoppingCart` (
                                `UniqueUserID`	varchar(50)	NOT NULL,
                                `ProductID`	int	NOT NULL	COMMENT 'auto_increment',
                                `Quantity`	int	NOT NULL,
                                `CreatedAt`	datetime	NOT NULL,

                                PRIMARY KEY (`UniqueUserID`, `ProductID`),
                                CONSTRAINT `FK_ShoppingCart_Users` FOREIGN KEY(UniqueUserID) REFERENCES Users(UniqueUserID) ON DELETE CASCADE ON UPDATE CASCADE,
                                CONSTRAINT `FK_ShoppingCart_Products` FOREIGN KEY(ProductID) REFERENCES Products(ProductID) ON DELETE CASCADE ON UPDATE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `ProductCategories` (
                                     `ProductID`	int	NOT NULL	COMMENT 'auto_increment',
                                     `CategoryID`	int	NOT NULL	COMMENT 'auto_increment',

                                     PRIMARY KEY (`ProductID`, `CategoryID`),
                                     CONSTRAINT `FK_ProductCategories_Products` FOREIGN KEY(ProductID) REFERENCES Products(ProductID) ON DELETE CASCADE ON UPDATE CASCADE,
                                     CONSTRAINT `FK_ProductCategories_Categories` FOREIGN KEY(CategoryID) REFERENCES Categories(CategoryID) ON DELETE CASCADE ON UPDATE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `PointDetails` (
                                `PointDetailID`	int	NOT NULL PRIMARY KEY AUTO_INCREMENT,
                                `Volume`	int	NOT NULL,
                                `Type`	varchar(10)	NOT NULL	COMMENT 'CHARGE, USE, SAVE',
                                `CreatedAt`	datetime	NOT NULL,
                                `UniqueUserID`	varchar(50)	NOT NULL,

                                CONSTRAINT `FK_PointDetails_Users` FOREIGN KEY(UniqueUserID) REFERENCES Users(UniqueUserID) ON DELETE CASCADE ON UPDATE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `ProductImages` (
                                 `ProductImageID`	int	NOT NULL PRIMARY KEY AUTO_INCREMENT,
                                 `ProductImage`	varchar(200)	NOT NULL,
                                 `ImageSize`	int	NOT NULL,
                                 `ProductID`	int	NOT NULL	COMMENT 'auto_increment',

                                 CONSTRAINT `FK_ProductImages_Products` FOREIGN KEY(ProductID) REFERENCES Products(ProductID) ON DELETE CASCADE ON UPDATE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;