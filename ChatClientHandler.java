/*

プログラム名：チャットサーバ
ファイル名：ChatClientHandler.java

 */
import java.net.*;
import java.io.*;

public class ChatClientHandler extends Thread{//Threadクラス←既存
    /**　フィールド　　*/
    private Socket socket;//クライアントを表すソケット
    private String name;//クライアントの名前

    /**　コンストラクタ　　*/
    ChatClientHandler (Socket sock){
        this.socket = sock;
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

    }
    
    /*  並列実行を行うときに実行されるメソッド */
    public void run(){
    }
    
}
    