/*

プログラム名：応答サーバ　
ファイル名：ChatServer.java

 */
import java.net.*;
import java.io.*;
import java.util.*;

public class ChatServer{
    /** フィールド　*/
    private ServerSocket server;
    private List clients = new ArrayList();//クライアント一覧を作成
    /** メソッド　　*/
    private void listen(){
        try{
            server = new ServerSocket(18080);//ポート番号を引数にサーバをたてる
            
            System.out.println("Echoサーバをポート18080で起動しました。");
                
            while(true){
                Socket socket = server.accept();//クライアントの接続を待つ
                ChatClientHandler handler = new ChatClientHandler(socket,clients);
                clients.add(handler);//クライアントのListに追加
                System.out.println(handler.getClientName() + " connected.");
                
                for(int i = 0;i < clients.size();i++){//自分以外のユーザに接続を知らせる
                    ChatClientHandler otherHandler = (ChatClientHandler)clients.get(i);
                    if(otherHandler != handler){//自分以外なら
                        otherHandler.send(handler.getClientName() + " connected.");
                    }
                }

                handler.start();//handler.receive(),send()の代わりに呼び出す
            }
            
            
        } catch (IOException e){
            e.printStackTrace();
        }
	
    }

    public static void main(String[] args){
	ChatServer echo = new ChatServer();
	echo.listen();    
    }
}
