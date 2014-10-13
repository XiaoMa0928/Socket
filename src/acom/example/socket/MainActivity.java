package acom.example.socket;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
	Button mBgit;
	TextView mTVis;
	private static final int REFRESH=0x000001;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mBgit=(Button) findViewById(R.id.BGet);
		mTVis=(TextView) findViewById(R.id.textView1);
		
		mBgit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new Thread(new Server()).start();
				
			}
		});
		
	}
	Handler handler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case MainActivity.REFRESH:
				Log.i("ma", msg+"");
				mTVis.setText(msg.obj.toString());
				break;

			default:
				break;
			}
			super.handleMessage(msg);
		}
	};
	class  Server implements Runnable{

		@Override
		public void run() {
			try {
				
//				实例化
				Socket socket=new Socket("10.12.75.213",8889);
				
				Log.i("ma", socket.getRemoteSocketAddress().toString());
//				获得输入流
				InputStream  in=socket.getInputStream();

//				缓冲区
				byte[] buffer=new byte[in.available()];
				
//			当在手机上运行程序的时候加上以下代码
//				
//				int count=0;
//				while (count==0) {
//					int s=in.available();
//					
//				}
				Log.i("ma", in.available() + "个字节");
//				读缓冲区
				in.read(buffer);
//				转换字符串
				String msg=new String(buffer);
				Log.i("ma", msg);
				Message message=new Message();
				message.what=MainActivity.REFRESH;
				message.obj=msg;
				MainActivity.this.handler.sendMessage(message);
//				设置文本框的字符串
//				mTVis.setText(msg);
				
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
			
		}

		
		
	}

	

	
}
