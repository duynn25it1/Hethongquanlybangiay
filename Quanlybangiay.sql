CREATE DATABASE QuanLyBanGiay;
GO

USE QuanLyBanGiay;
GO
-- Bảng sản phẩm
CREATE TABLE Product (
    ProductID INT IDENTITY(1,1) PRIMARY KEY,
    ProductName NVARCHAR(100) NOT NULL,
    Brand NVARCHAR(50),
    Size INT,
    Price DECIMAL(10,2),
    Quantity INT
);

-- Bảng khách hàng
CREATE TABLE Customer (
    CustomerID INT IDENTITY(1,1) PRIMARY KEY,
    CustomerName NVARCHAR(100) NOT NULL,
    Phone NVARCHAR(15),
    Email NVARCHAR(100)
);

-- Bảng đơn hàng
CREATE TABLE Orders (
    OrderID INT IDENTITY(1,1) PRIMARY KEY,
    CustomerID INT NOT NULL,
    OrderDate DATE DEFAULT GETDATE(),
    TotalAmount DECIMAL(10,2),
    FOREIGN KEY (CustomerID) REFERENCES Customer(CustomerID)
);

-- Bảng chi tiết đơn hàng
CREATE TABLE OrderDetail (
    OrderDetailID INT IDENTITY(1,1) PRIMARY KEY,
    OrderID INT NOT NULL,
    ProductID INT NOT NULL,
    Quantity INT,
    FOREIGN KEY (OrderID) REFERENCES Orders(OrderID),
    FOREIGN KEY (ProductID) REFERENCES Product(ProductID)
);
-- Bảng thanh toán
CREATE TABLE Payment (
    PaymentID INT IDENTITY(1,1) PRIMARY KEY,
    OrderID INT NOT NULL,
    PaymentDate DATE DEFAULT GETDATE(),
    PaymentMethod NVARCHAR(50),   -- Ví dụ: 'Cash', 'Credit Card', 'VNPay', 'Momo'
    Amount DECIMAL(10,2),
    Status NVARCHAR(20) DEFAULT 'Pending', -- Trạng thái: Pending, Completed, Failed
    FOREIGN KEY (OrderID) REFERENCES Orders(OrderID)
);


USE QuanLyBanGiay;
GO
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
(1, GETDATE(), 1200000);

INSERT INTO Orders
(
    CustomerID,
    OrderDate,
    TotalAmount
)
VALUES
(2, GETDATE(), 2500000);

INSERT INTO Orders
(
    CustomerID,
    OrderDate,
    TotalAmount
)
VALUES
(3, GETDATE(), 800000);