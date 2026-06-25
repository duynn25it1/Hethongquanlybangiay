Use QuanLyBanGiay
GO 
INSERT INTO Users(username,passwordHash, role)
VALUES('admin','123','ADMIN');
Use QuanLyBanGiay
GO 
INSERT INTO Customer(CustomerName,Phone,Email)
VALUES
('Nguyen Van A','0901234567','a@gmail.com'),
('Tran Van B','0912345678','b@gmail.com');

SELECT * FROM Users;