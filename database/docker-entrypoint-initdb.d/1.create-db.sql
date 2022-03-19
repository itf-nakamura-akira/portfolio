-- 開発用DB
DROP DATABASE IF EXISTS portfolio;

CREATE DATABASE portfolio;

-- テスト実行用DB
DROP DATABASE IF EXISTS portfolio_test;

CREATE DATABASE portfolio_test;

GRANT ALL ON portfolio.* TO 'itf-user';
GRANT ALL ON portfolio_test.* TO 'itf-user';
