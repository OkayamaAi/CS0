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
    private BufferedReader in;//
    private BufferedWriter out;//
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
        try{
            open();
            
            while(true){
                String message = receive();//受け取ったメッセージをmassageに格納
                System.out.println(getClientName() + ": "+ message);
                send(getClientName() + ": "+ message);
            }
        }catch(IOException e){
            e.printStackTrace();
            
        }finally{
            close();
        }
    }
    
    /*+++++++++++++サーバの基本機能を実行するためのメソッド++++++++++++++*/
    /* クライアントとのデータのやり取りを行うストリームを開くメソッド */
    public void open() throws IOException {
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    }
    
    /* クライアントからデータを受け取るメソッド */
    String receive() throws IOException{
        String message = in.readLine();
        //System.out.println(message);
        return message;
    }
    /* クライアントにデータを送信するメソッド */
    void send(String message) throws IOException{
        out.write(message);//messageの中身をバッファに書き込む
        out.write("\r\n");//（ネットワークの改行）
        out.flush();//バッファの文字をクライアント側に画面出力
    }
    /* クライアントとの接続を閉じるメソッド */
    void close(){
        ChatClientHandler removeHandler = this;
        ChatClientHandler handler = null;
      
        clients.remove(removeHandler);//クライアントをリストから消去
        if(in != null){try{ in.close(); } catch(IOException e ){}}
        if(out != null){try{ out.close(); } catch(IOException e ){}}
        if(socket != null){try{ socket.close(); } catch(IOException e ){}}
        //closeメソッドが例外を投げるかもしれないので例外処理	
        
        
        
    }

}
    