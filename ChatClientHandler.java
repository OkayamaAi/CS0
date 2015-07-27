/*

プログラム名：チャットサーバ
ファイル名：ChatClientHandler.java

 */
import java.net.*;
import java.io.*;
import java.util.*;

public class ChatClientHandler extends Thread{//Threadクラス←既存
    /**　フィールド　　*/
    private Socket socket;//クライアントを表すソケット
    private String name;//クライアントの名前
    private List clients;//接続クライアント一覧

    /**　コンストラクタ　　*/
    ChatClientHandler(Socket sock,List clients){
        this.socket = sock;
        this.clients = clients;
        setInitialName();
    }
    
    /**  メソッド　　*/
    /* ゲッター （ユーザ名・ユーザ(object)）*/
    public String getClientName(){return name;}//クライアントの名前
    
    public ChatClientHandler getHandler(){
        return this;//自身のハンドラを返す
	}
    /* セッター　（ユーザ名・ユーザ名(自動)・所属グループ）*/
    public void setClientName(String name){ this.name = name; }
    public void setInitialName(){//名前をつける
	String name = "undefined";
        int num = 1;
        
        for(int i = 0;i < clients.size(); i++){
            ChatClientHandler handler = (ChatClientHandler)clients.get(i);
            if(handler.getClientName().equals(name + num)){//同じ名前の人がいたら
                num ++;
            }   
        }
        this.name = name + num ;
    }
    
    /*  並列実行を行うときに実行されるメソッド */
    public void run(){
    }
    
}
    