# CS0
ソフトウェア工学Ⅱのグループ課題のために作成。昨年、発展プログラミング演習Ⅱの授業の最終課題で作成した『ChatServer』をグループで作成する。


'<プロジェクトについて>'
-------------------------
　今回作成するChatServerでは、クライアントはtelnetを用いて18080ポートに接続する。
クライアントはコマンドによって、様々な処理を実行できる。
実装するコマンドは以下のとおりである。
• HELP…コマンドの説明を表示します。コマンドを指定するとその説明を、指定しないと一覧を表示します。
• NAME…名前の変更を行います(ex.NAME 変更したい名前)。
• WHOAMI…あなたの名前と参加グループを返します。
• BYE…チャットを終了します。
• POST…全員にメッセージを送信します。

'＜チケットについて＞'
-------------------------
### ・チケット内容 ###
　　チケット１　　：ChatServerクラスの作成（サーバをたてる、クライアントの接続を受け取る）
　　チケット２　　：クライアントにデータを送り返す処理の追加。
　　チケット３　　：ChatClientHandlerクラスの作成（クライアントの情報）
　　チケット４　　：クライアントの情報を保存する処理の追加（ChatServer）
　　チケット５　　:ChatClientServerクラスに「HELPコマンド」の処理を追加。
　　チケット６　　:ChatClientServerクラスに「NAMEコマンド」の処理を追加。
　　チケット７　　:ChatClientServerクラスに「WHOAMIコマンド」の処理を追加。
　　チケット８　　:ChatClientServerクラスに「POSTコマンド」の処理を追加。
　　チケット９　　:ChatClientServerクラスに「BYEコマンド」の処理を追加。

### ・チケット役割分担表 ###

|            | チケット発行 | チケット担当  | レビュー　| マージ  |
|------------|------------|-------------|---------|--------|
| チケット１   |  岡山愛     |  谷谷芽生    | 西中希   | 岡山愛　 |
| チケット２   |  谷谷芽生   |  西中希      | 岡山愛　 | 谷谷芽生 |
| チケット３   |  西中希     |  岡山愛　    | 谷谷芽生 | 西中希   |  
| チケット４   |  岡山愛     |  谷谷芽生    | 西中希   | 岡山愛　 |
| チケット５   |  谷谷芽生   |  西中希      | 岡山愛　 | 谷谷芽生 |
| チケット６   |  西中希     |  岡山愛　    | 谷谷芽生 | 西中希   | 
| チケット７   |  岡山愛     |  谷谷芽生    | 西中希   | 岡山愛　 |
| チケット８   |  谷谷芽生   |  西中希      | 岡山愛　 | 谷谷芽生 |
| チケット９   |  西中希     |  岡山愛　    | 谷谷芽生 | 西中希   | 
