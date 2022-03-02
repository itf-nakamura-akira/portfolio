# portfolio
Webアプリのポートフォリオを作成。

## 開発環境

### データベース接続先

| Server Host | Port | Database | User | Password |
----|----|----|----|----
| localhost | 52420 | portfolio | root | 4cvTREHMY9kjh6xIPAaer25Ow7iSDFGglbnmq3WtfLU1ozVBNyuZXC0p8QJKds |

### データベースの初期化

docker containerのビルド時に開発用データベースが作成されるが、databaseディレクトリーで下記コマンドを実行することで、開発環境のデータベースを初期化することも出来る。

```
sh re-create.sh
```
