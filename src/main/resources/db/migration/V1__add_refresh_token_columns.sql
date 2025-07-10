-- Add `refresh_token` column if not exists
SELECT COUNT(*)
INTO @columnCount
FROM INFORMATION_SCHEMA.COLUMNS
WHERE TABLE_NAME = 'tokens'
  AND TABLE_SCHEMA = 'zshop'
  AND COLUMN_NAME = 'refresh_token';

SET @alterStatement = IF(@columnCount = 0,
    'ALTER TABLE tokens ADD COLUMN refresh_token VARCHAR(255) DEFAULT '''';',
    '');
PREPARE stmt FROM @alterStatement;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- Add `refresh_expiration_date` column if not exists
SELECT COUNT(*)
INTO @columnCount
FROM INFORMATION_SCHEMA.COLUMNS
WHERE TABLE_NAME = 'tokens'
  AND TABLE_SCHEMA = 'zshop'
  AND COLUMN_NAME = 'refresh_expiration_date';

SET @alterStatement = IF(@columnCount = 0,
    'ALTER TABLE tokens ADD COLUMN refresh_expiration_date DATETIME;',
    '');
PREPARE stmt FROM @alterStatement;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;
