SET CHARACTER_SET_CLIENT = utf8mb4;
SET CHARACTER_SET_CONNECTION = utf8mb4;

/*********** テストデータの作成 ***********/

-- ユーザーテーブル(パスワードはaccountと同じ)
INSERT INTO users
    (account, password_hash, name, permission, is_enabled)
VALUES
    ('admin', '$2a$10$NHwSXeZd7ieiIKKGsOqcteNdZoND9VfQqkB9yIdUnNh4Dq48DTv7q', '管理者ユーザー', 'Admin', true),
    ('saito', '$2a$10$ZDcm9KoNyZske8wAFnsyu.VbaeoaNgWwuFmff3mMe2Yrf39aT0IJu', '齋藤 綾香', 'User', true),
    ('shirahama', '$2a$10$6wSX9vpPzUorr.TSGaO4CObyvt6aZHicTEULdk.gJCtSK6SdfbJCC', '白浜 隆二', 'User', true),
    ('yamaoka', '$2a$10$91BFfXg80xm1KvVeSzmcMeTjq8kdIJWo0sF9wFGbCP6S1v5oL5dMK', '山岡 友治', 'User', true),
    ('sakata', '$2a$10$bUlnL/CJw/1nkS.o7mzANO0Zx7d5DvxWa83fSGHOJVSRNfqac1G72', '阪田 智恵', 'User', true),
    ('imadu', '$2a$10$QBmA5RGycbyQZOUUzzf9POEkWatY0qoQxfubZnag0cPVzSDCn9Ebq', '今津 重光', 'User', true),
    ('kawakami', '$2a$10$XwxrNn0B2dUB30F/BM61EuhGCWSnHKch8gP7XgQNR5P/1Rx1WlwpK', '川上 伸夫', 'User', true),
    ('shikata', '$2a$10$qFveWYis.1ZGtzeDhx5Id.tjJxkY2pIm1GVMwKZ0nvnTqz/ZKLrou', '四方 謙三', 'User', true),
    ('nojiri', '$2a$10$5YRzeddgE6s7cZUqcPNBw.Nqqy24/pFCOC0Bpu6hbakGuASJtyyMO', '野尻 欧子', 'User', true),
    ('yahata', '$2a$10$ZlLj/QQ9ta0mnIFg3gFe4O8fZ3j2TgviSB/YW2IJKyBk24sfMHMZ6', '八幡 雅也', 'User', true),
    ('ichinohe', '$2a$10$VcUwVqjSMnJxF.ColcRQEe4E1iUAwlSc8tmlcU2mV/50nt6xF2eUC', '一戸 敏雄', 'User', true),
    ('referencer', '$2a$10$Nwig050LweltGBInbomL/ePMZ1Ofspepa.n7dnBJpQTBWDFtl/THO', '参照ユーザー', 'Referencer', true)
;
-- BCrypt ハッシュ値 計算: http://www.tekboy.net/bcrypt-calculator
-- テストデータ生成: https://tm-webtools.com/Tools/TestData
