# portfolio
Webアプリのポートフォリオを作成。

## 開発環境

### Visual Studio Code Remote - Containers

本プロジェクトの開発環境はVSCode x Dockerで構築されています。参考記事でVSCodeとWSL2の環境を構築しコンテナーを開くことで、開発環境が整います。  
参考: [Developing inside a Container](https://code.visualstudio.com/docs/remote/containers)

### データベース接続先

| Server Host | Port | Database | User | Password |
----|----|----|----|----
| localhost | 52420 | portfolio | root | 4cvTREHMY9kjh6xIPAaer25Ow7iSDFGglbnmq3WtfLU1ozVBNyuZXC0p8QJKds |

### データベースの初期化

コンテナーのビルド時に開発用データベースが作成されます。また、databaseディレクトリーで下記コマンドを実行することで、開発環境のデータベースを初期化することも出来ます。

```
sh re-create.sh
```
### Backend

#### 構成

Spring Boot x My Batis x Maria DB

#### 起動について

`backend/portfolio/pom.xml` ファイルを開くとSPRING BOOT DASHBOARDが表示され、Mavenの依存ファイルのダウンロードが開始されます。ダウンロードが完了するとプロジェクト名(portfolio)が表示され、そこから起動・デバッグ起動が可能となります。

### Frontend

### 構成

Angular13 x Angular Material x Jest(Unit Test) x Cypress(E2E Test)

#### 起動について

`frontend/package.json` ファイルwを開くとNPM スクリプトが表示されます。 `frontend/package.json` を右クリックしてインストールを実行すると、npmの依存ファイルのダウンロードが開始されます。ダウンロードが完了すると、NPMスクリプトのstartスクリプトから起動・デバッグ起動が可能となります。

#### Cypress(E2Eテスト)について

E2Eテストを実行するにはCypressの依存ファイルをダウンロードする必要があります。NPMスクリプトに `cypress: install` と `cypress dependency: install` を用意しているので、それぞれ実行してください。後者は環境によってはかなり時間がかかる可能性があります。  
依存ファイルのダウンロードが完了すると、NPMスクリプトの `e2e` からE2Eテストを実行できます。Dockerコンテナー内での実行のため、現状はheadlessモードでしか動きません。  
