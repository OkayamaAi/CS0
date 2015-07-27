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
                
                handler.start();//
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
