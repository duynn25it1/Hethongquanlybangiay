CREATE DATABASE QuanLyBanGiay;
GO

USE QuanLyBanGiay;
GO
CREATE TABLE Product (
    ProductID INT IDENTITY(1,1) PRIMARY KEY,
    ProductName NVARCHAR(100) NOT NULL,
    Brand NVARCHAR(50),
    Size INT,
    Price DECIMAL(10,2),
    Quantity INT
);

CREATE TABLE Customer (
    CustomerID INT IDENTITY(1,1) PRIMARY KEY,
    CustomerName NVARCHAR(100) NOT NULL,
    Phone NVARCHAR(15),
    Email NVARCHAR(100)
);

CREATE TABLE Orders (
    OrderID INT IDENTITY(1,1) PRIMARY KEY,
    CustomerID INT NOT NULL,
    OrderDate DATE DEFAULT GETDATE(),
    TotalAmount DECIMAL(10,2),
    FOREIGN KEY (CustomerID) REFERENCES Customer(CustomerID)
);

CREATE TABLE OrderDetail (
    OrderDetailID INT IDENTITY(1,1) PRIMARY KEY,
    OrderID INT NOT NULL,
    ProductID INT NOT NULL,
    Quantity INT,
    FOREIGN KEY (OrderID) REFERENCES Orders(OrderID),
    FOREIGN KEY (ProductID) REFERENCES Product(ProductID)
);
CREATE TABLE Payment (
    PaymentID INT IDENTITY(1,1) PRIMARY KEY,
    OrderID INT NOT NULL,
    PaymentDate DATE DEFAULT GETDATE(),
    PaymentMethod NVARCHAR(50),   
    Amount DECIMAL(10,2),
    Status NVARCHAR(20) DEFAULT 'Pending', 
    FOREIGN KEY (OrderID) REFERENCES Orders(OrderID)
);

CREATE TABLE Users (
    id INT IDENTITY(1,1) PRIMARY KEY,
    username NVARCHAR(50) NOT NULL UNIQUE,
    passwordHash NVARCHAR(255) NOT NULL,
    role NVARCHAR(20) NOT NULL
);

INSERT INTO Orders
(
    CustomerID,
    OrderDate,
    TotalAmount
)
VALUES
(
    1,
    GETDATE(),
    500000
);

Status DEFAULT 'Completed'