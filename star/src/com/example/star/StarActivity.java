package com.example.star;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class StarActivity extends Activity {
	
    private static final String LOG_TAG = "BallGame";
    
    private Bitmap mBitmap;
    private float mXOrigin;
    private float mYOrigin;
    private long mLastT;
    private float mLastDeltaT;
    private static final float sBallDiameter = 0.004f;
    private static final float sBallDiameter2 = sBallDiameter * sBallDiameter;
    private float mXDpi;
    private float mYDpi;
    private float mMetersToPixelsX;
    private float mMetersToPixelsY;
    private float mHorizontalBound;
    private float mVerticalBound;
    private float mLeft = 0.0f;
    private float mRight = 0.0f;
    private float mUp = 0.0f;
    private float mDown = 0.0f;
    
	private int imagenumber = 0;
	private int mNum = 0;
	
	private int[] mImageId = new int[] {
			R.drawable.bg_sunrise, R.drawable.bg_sunset,
			R.drawable.earthrise };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		showbackground(imagenumber);
		
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        mXDpi = metrics.xdpi;
        mYDpi = metrics.ydpi;
        mMetersToPixelsX = mXDpi / 0.0254f;
        mMetersToPixelsY = mYDpi / 0.0254f;
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		mNum = mNum + 1;
		showbackground(mNum);
		
	    float mLeft = 0.0f;
	    float mRight = 0.0f;
	    float mUp = 0.0f;
	    float mDown = 0.0f;	
	}
	
	@Override
	protected void onPause() {		
		super.onPause();
		mNum = 2;
	}	

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mNum = 0;
	}
	
	private void showbackground(int mNum) {
		ImageView imageView = (ImageView) findViewById(R.id.image_view);
		imageView.setBackgroundResource(mImageId[mNum]);
	}
	
    class Particle {
        private float mPosX;
        private float mPosY;
        private float mAccelX;
        private float mAccelY;
        private float mLastPosX;
        private float mLastPosY;
        private float mOneMinusFriction;

        public void computePhysics(float sx, float sy, float dT, float dTC) {
            final float m = 1000.0f; // mass of our virtual object
            final float gx = -sx * m;
            final float gy = -sy * m;

            final float invm = 1.0f / m;
            final float ax = gx * invm;
            final float ay = gy * invm;

            final float dTdT = dT * dT;
            final float x = mPosX + dTC * (mPosX - mLastPosX) + mAccelX * dTdT;
            final float y = mPosY + dTC * (mPosY - mLastPosY) + mAccelY * dTdT;
            mLastPosX = mPosX;
            mLastPosY = mPosY;
            mPosX = x;
            mPosY = y;
            mAccelX = ax;
            mAccelY = ay;
        }

        public void resolveCollisionWithBounds() {
            final float xmax = mHorizontalBound;
            final float ymax = mVerticalBound;
            final float x = mPosX;
            final float y = mPosY;
            if (x > xmax) {
                mPosX = xmax;
            } else if (x < -xmax) {
                mPosX = -xmax;
            }
            if (y > ymax) {
                mPosY = ymax;
            } else if (y < -ymax) {
                mPosY = -ymax;
            }
        }
    }

    class ParticleSystem {
        static final int NUM_PARTICLES = 3;
        private Particle mStars[] = new Particle[NUM_PARTICLES];

        ParticleSystem() {
            for (int i = 0; i < mStars.length; i++) {
                mStars[i] = new Particle();
            }
        }

        private void updatePositions(float sx, float sy, long timestamp) {
            final long t = timestamp;
            if (mLastT != 0) {
                final float dT = (float) (t - mLastT) * (1.0f / 1000000000.0f);
                if (mLastDeltaT != 0) {
                    final float dTC = dT / mLastDeltaT;
                    final int count = mStars.length;
                    for (int i = 0; i < count; i++) {
                        Particle star = mStars[i];
                        star.computePhysics(sx, sy, dT, dTC);
                    }
                }
                mLastDeltaT = dT;
            }
            mLastT = t;
        }

        public void update(float sx, float sy, long now) {
            // update the system's positions
            updatePositions(sx, sy, now);

            // We do no more than a limited number of iterations
            final int NUM_MAX_ITERATIONS = 10;

            boolean more = true;
            final int count = mStars.length;
            for (int k = 0; k < NUM_MAX_ITERATIONS && more; k++) {
                more = false;
                for (int i = 0; i < count; i++) {
                    Particle curr = mStars[i];
                    for (int j = i + 1; j < count; j++) {
                        Particle ball = mStars[j];
                        float dx = ball.mPosX - curr.mPosX;
                        float dy = ball.mPosY - curr.mPosY;
                        float dd = dx * dx + dy * dy;
                        // Check for collisions
                        if (dd <= sBallDiameter2) {
                            /*
                             * add a little bit of entropy, after nothing is
                             * perfect in the universe.
                             */
                            dx += ((float) Math.random() - 0.5f) * 0.0001f;
                            dy += ((float) Math.random() - 0.5f) * 0.0001f;
                            dd = dx * dx + dy * dy;
                            // simulate the spring
                            final float d = (float) Math.sqrt(dd);
                            final float c = (0.5f * (sBallDiameter - d)) / d;
                            curr.mPosX -= dx * c;
                            curr.mPosY -= dy * c;
                            ball.mPosX += dx * c;
                            ball.mPosY += dy * c;
                            more = true;
                        }
                    }
                    curr.resolveCollisionWithBounds();
                }
            }
        }

        public int getParticleCount() {
            return mStars.length;
        }

        public float getPosX(int i) {
            return mStars[i].mPosX;
        }

        public float getPosY(int i) {
            return mStars[i].mPosY;
        }
    }
    
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        // compute the origin of the screen relative to the origin of
        // the bitmap
        mXOrigin = (w - mBitmap.getWidth()) * 0.5f;
        mYOrigin = (h - mBitmap.getHeight()) * 0.5f;
        mHorizontalBound = ((w / mMetersToPixelsX - sBallDiameter) * 0.5f);
        mVerticalBound = ((h / mMetersToPixelsY - sBallDiameter) * 0.5f);
    }
    
    private void onToutch() {
        Button UpButton = (Button) findViewById(R.id.button1);
        UpButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mUp = mUp++;
            }
        });
        
        Button DownButton = (Button) findViewById(R.id.button2);
        DownButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mDown = mDown++;
            }
        });
        
        Button LeftButton = (Button) findViewById(R.id.button3);
        LeftButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mLeft = mLeft++;
            }
        });
        
        Button RightButton = (Button) findViewById(R.id.button4);
        RightButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mRight = mRight++;
            }
        });
    }
    

    protected void onDraw(Canvas canvas) {

        /*
         * draw the background
         */
    	Paint paint = new Paint();
    	
    	showbackground(imagenumber);
    	
    	Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.greenstar);
        canvas.drawBitmap(bitmap1, 100, 600, paint); 
        
    	Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.redstar);
        canvas.drawBitmap(bitmap2, 1000, 400, paint); 
        
    	Bitmap bitmap3 = BitmapFactory.decodeResource(getResources(), R.drawable.yellowstar);
        canvas.drawBitmap(bitmap3, 500, 100, paint); 

        final ParticleSystem mParticleSystem = new ParticleSystem();

        final ParticleSystem particleSystem = mParticleSystem;
        final long now = 1 / 1000000000;
        final float sx = mRight - mLeft;
        final float sy = mUp - mDown;

        particleSystem.update(sx, sy, now);

        final float xc = mXOrigin;
        final float yc = mYOrigin;
        final float xs = mMetersToPixelsX;
        final float ys = mMetersToPixelsY;
        final Bitmap bitmap = mBitmap;
        final int count = particleSystem.getParticleCount();
        for (int i = 0; i < count; i++) {
            /*
             * We transform the canvas so that the coordinate system matches
             * the sensors coordinate system with the origin in the center
             * of the screen and the unit is the meter.
             */

            final float x = xc + particleSystem.getPosX(i) * xs;
            final float y = yc - particleSystem.getPosY(i) * ys;
            canvas.drawBitmap(bitmap, x, y, null);
        }
//        invalidate();
    }

}


