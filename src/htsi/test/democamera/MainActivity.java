package htsi.test.democamera;

import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;


public class MainActivity extends Activity {
	
	private Camera camera;
	private LinearLayout scrollImages;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		scrollImages = (LinearLayout)findViewById(R.id.scrollImages);
		
		SurfaceView surfaceView = (SurfaceView)findViewById(R.id.surfaceView);
		SurfaceHolder holder = surfaceView.getHolder();
		holder.addCallback(new Callback() {
			
			@Override
			public void surfaceDestroyed(SurfaceHolder holder) {
				// TODO Auto-generated method stub
				camera.stopPreview();
				camera.release();
				camera = null;
			}
			
			@Override
			public void surfaceCreated(SurfaceHolder holder) {
				// TODO Auto-generated method stub
				camera = Camera.open();
				try {
					camera.setPreviewDisplay(holder);
					camera.startPreview();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			@Override
			public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
				// TODO Auto-generated method stub
				
			}
		});
		
		Button btnTakePicture = (Button)findViewById(R.id.btnTakePicture);
		btnTakePicture.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				camera.takePicture(null, null, new PictureCallback() {
					
					@Override
					public void onPictureTaken(byte[] data, Camera camera) {
						// TODO Auto-generated method stub
						BitmapFactory.Options option = new Options();
						option.inSampleSize = 4;
						Bitmap b = BitmapFactory.decodeByteArray(data, 0, data.length, option);
						
						ImageView imageView = new ImageView(getApplicationContext());
						imageView.setImageBitmap(b);
						scrollImages.addView(imageView);
					}
				});
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
