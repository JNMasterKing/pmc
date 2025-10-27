CREATE DATABASE ExpenseDB;
GO
USE ExpenseDB;
GO

CREATE TABLE expenses (
   id INT IDENTITY(1,1) PRIMARY KEY,
   title VARCHAR(255) NOT NULL,
   amount DECIMAL(12,2) NOT NULL CHECK (amount >= 0),
   category VARCHAR(100) NOT NULL,
   expense_date DATE NOT NULL,
   created_at DATETIME2 DEFAULT SYSUTCDATETIME()
);
GO

INSERT INTO expenses (title, amount, category, expense_date)
VALUES
  ('Lunch', 80.0, 'Food', '2025-10-01'),
  ('Bus pass', 45.00, 'Transport', '2025-10-02'),
  ('Books', 250.00, 'Education', '2025-09-15');
GO
