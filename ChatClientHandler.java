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
    private Map commandList;//コマンド一覧

    /**　コンストラクタ　　*/
    ChatClientHandler(Socket sock,List clients){
        this.socket = sock;
        this.clients = clients;
        setInitialName();
        this.commandList = createCommandList();
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
                //受け取ったメッセージをスペースで区切って配列に変換
                String[] commands = message.split(" ");
                System.out.print(getClientName() + ": ");
                
                //コマンド処理
                try{
                    if(commands[0].equals("help")){//『HELP』
                        //コマンドが指定されたらその説明を、指定されなかったら一覧を表示
                        try{ commandHelp(commands[1]);
                            System.out.print("help :");
                        }catch(ArrayIndexOutOfBoundsException e){
                            help();
                            System.out.println("help :");
                        }
                    }
                    else if(commands[0].equals("name")){//『NAME』
                        System.out.print("name: ");
                        name(commands[1]);
                    }
                    else if(commands[0].equals("whoami")){//『WHOAMI』
                        System.out.print("whoami: ");
                        whoami();
                    }
                    else if(commands[0].equalsIgnoreCase("post")){//『POST』
                        System.out.print("post: ");
                        post(commands[1]);
                    }
                    else{
                        System.out.println("コマンドが存在しません。");
                        this.send("コマンドが存在しません。(helpでコマンド一覧)");
                    }
    
                }catch(ArrayIndexOutOfBoundsException e){//引数の数が間違っているとき
                    System.out.println("コマンドの使い方が間違っています。");
                    this.send("コマンドの使い方が間違っています。(helpでコマンド一覧)");
                }
            }
        }catch(IOException e){
            e.printStackTrace();
            
        }finally{
            close();
        }
    }
    /*++++++++++システムとユーザに関するコマンドで利用したメソッド++++++++++++++++++*/
    /* コマンドのヘルプを表示するメソッド　『HELP』*/
    //（１）コマンド一覧を表示
    public void help() throws IOException{
        printMap(this.commandList);
    }
    //（２）指定したコマンドのヘルプを表示
    public void commandHelp(String command)throws IOException{
        
        command = command.toUpperCase();//commandを大文字に変更してから検索
        System.out.println(command + ": ");
        String detail =  (String)commandList.get(command);//コマンドの説明を探す
        if(detail != null)//コマンドが存在したら
            this.send("…" + detail);//説明をクライアントに送信
        else//存在しなかったら
            this.send(command + "というコマンドは存在しません。");//エラーメッセージを送信
    }
    /* コマンドリストを作成するメソッド  『HELP』*/
    private Map createCommandList(){
        Map<String,String> commandList = new TreeMap<String,String>();//キーと値がString型のマップを作成
        /*+++++++システムとユーザに関するコマンド+++++++++++*/
        commandList.put("HELP","コマンドの説明を表示します。コマンドを指定するとその説明を、指定しないと一覧を表示します。(ex.HELP コマンド)");
        commandList.put("NAME","名前の変更を行います(ex.NAME 変更したい名前)");
        commandList.put("WHOAMI","あなたの名前と参加グループを返します。");
        commandList.put("BYE","チャットを終了します。");
        /*+++++++投稿に関するコマンド+++++++++++++++++++++++*/
        commandList.put("POST","全員にメッセージを送信します。");
        
        return commandList;
    }
    /* マップを表示するためのメソッド  『HELP』*/
    private void printMap(Map map) throws IOException{
        for(Iterator i = map.entrySet().iterator();i.hasNext();){
            Map.Entry entry = (Map.Entry)i.next();
            Object key = entry.getKey();
            Object value = entry.getValue();
            this.send(key + " … " + value);
        }
    }
    /* 名前を変更するためのメソッド     『NAME』*/
    public void name(String name) throws IOException{
        
        String preName = this.getClientName();
        
        for(int i = 0;i < clients.size(); i++){
            ChatClientHandler handler = (ChatClientHandler)clients.get(i);
            if(name.equals(handler.getClientName())){//他に同じ名前の人がいたら
                this.send("同じ名前があります。");//クライアントに送信
                return;//メソッドの処理を終了
            }
        }
        setClientName(name);//名前を設定する
        
        for(int i = 0;i < clients.size();i ++ ){
            ChatClientHandler handler = (ChatClientHandler)clients.get(i);
            if(handler != this){//自分以外のユーザで
                handler.send("name:" + preName + "->" + name);//他のユーザにも名前の変更を知らせる
            }
        }
        System.out.println("name:" + name);//サーバに残す
    }
    
    /* クライアントに名前を送信するメソッド   『WHOAMI』*/
    public void whoami() throws IOException{
    
            System.out.println(getClientName());
            this.send(getClientName());
     
    }

    /*++++++++++++++投稿に関するコマンド+++++++++++++++++++++++++++++++++++++++++*/
    /* 接続している全員にmessageを送信するメソッド  『POST』*/
    public void post(String message) throws IOException{
        int flag1 = 1;//フラグ(送信者表示時に利用)
     
        System.out.println(message);
        List names = new ArrayList();//送信者リストを作る
        
        //チャットに参加しているユーザ全員にメッセージを送信する
        
        for(int i = 0;i < clients.size();i ++ ){
            ChatClientHandler handler = (ChatClientHandler)clients.get(i);
            if(handler != this){//自分以外のユーザで
                    names.add(handler.getClientName());//送信者リストに名前追加
                    handler.send("[" + this.getClientName() + "]" + message);//メッセージを送信
            }
            
        }
        
        Collections.sort(names);//送信者リストをソート
        
        String returnMessage = "";//初期化
        
        for(int i = 0;i < names.size();i ++ ){//
            if(flag1 == 0)//２人目以降、前に「,」つける
                returnMessage = returnMessage +  ",";
            returnMessage = returnMessage + names.get(i);//送信者リストに名前を追加
            flag1 = 0;//２人目以降フラグをおろす
        }
        this.send("<< To. " + returnMessage + ">>");	
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
    