package com.wickedbotz.cab.common;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

public class JoyStickClass extends Activity {
	public static final int STICK_NONE = 0, STICK_UP_LOW = 11, STICK_UP_MEDIUM = 12, STICK_UP_HIGH = 13,
			STICK_UPRIGHT_LOW = 21, STICK_UPRIGHT_MEDIUM = 22, STICK_UPRIGHT_HIGH = 23, STICK_RIGHT_LOW = 31,
			STICK_RIGHT_MEDIUM = 32, STICK_RIGHT_HIGH = 33, STICK_DOWNRIGHT_LOW = 41, STICK_DOWNRIGHT_MEDIUM = 42,
			STICK_DOWNRIGHT_HIGH = 43, STICK_DOWN_LOW = 51, STICK_DOWN_MEDIUM = 52, STICK_DOWN_HIGH = 53,
			STICK_DOWNLEFT_LOW = 61, STICK_DOWNLEFT_MEDIUM = 62, STICK_DOWNLEFT_HIGH = 63, STICK_LEFT_LOW = 71,
			STICK_LEFT_MEDIUM = 72, STICK_LEFT_HIGH = 73, STICK_UPLEFT_LOW = 81, STICK_UPLEFT_MEDIUM = 82,
			STICK_UPLEFT_HIGH = 83;

	private int STICK_ALPHA = 200, LAYOUT_ALPHA = 200, OFFSET = 0, DistanceMed = 90, DistanceHig = 120;

	public boolean velocidade = false;
	private Context mContext;
	private ViewGroup mLayout;
	private LayoutParams params;
	private int stick_width, stick_height;

	private int position_x = 0, position_y = 0, min_distance = 0;
	private float distance = 0, angle = 0;

	private DrawCanvas draw;
	private Paint paint;
	private Bitmap stick;

	private boolean touch_state = false;

	public static final String PREFS_NAME_VELOCIDADE = "velocidade";

	public JoyStickClass(Context context, ViewGroup layout, int stick_res_id) {
		mContext = context;

		stick = BitmapFactory.decodeResource(mContext.getResources(), stick_res_id);

		stick_width = stick.getWidth();
		stick_height = stick.getHeight();

		draw = new DrawCanvas(mContext);
		paint = new Paint();
		mLayout = layout;
		params = mLayout.getLayoutParams();
	}

	public void drawStick(MotionEvent arg1) {
		position_x = (int) (arg1.getX() - (params.width / 2));
		position_y = (int) (arg1.getY() - (params.height / 2));
		distance = (float) Math.sqrt(Math.pow(position_x, 2) + Math.pow(position_y, 2));
		angle = (float) cal_angle(position_x, position_y);

		if (arg1.getAction() == MotionEvent.ACTION_DOWN) {
			if (distance <= (params.width / 2) - OFFSET) {
				draw.position(arg1.getX(), arg1.getY());
				draw();
				touch_state = true;
			}
		} else if (arg1.getAction() == MotionEvent.ACTION_MOVE && touch_state) {
			if (distance <= (params.width / 2) - OFFSET) {
				draw.position(arg1.getX(), arg1.getY());
				draw();
			} else if (distance > (params.width / 2) - OFFSET) {
				float x = (float) (Math.cos(Math.toRadians(cal_angle(position_x, position_y)))
						* ((params.width / 2) - OFFSET));
				float y = (float) (Math.sin(Math.toRadians(cal_angle(position_x, position_y)))
						* ((params.height / 2) - OFFSET));
				x += (params.width / 2);
				y += (params.height / 2);
				draw.position(x, y);
				draw();
			} else {
				mLayout.removeView(draw);
			}
		} else if (arg1.getAction() == MotionEvent.ACTION_UP) {
			mLayout.removeView(draw);
			touch_state = false;
		}
	}

	public int[] getPosition() {
		if (distance > min_distance && touch_state) {
			return new int[] { position_x, position_y };
		}
		return new int[] { 0, 0 };
	}

	public int getX() {
		if (distance > min_distance && touch_state) {
			return position_x;
		}
		return 0;
	}

	public int getY() {
		if (distance > min_distance && touch_state) {
			return position_y;
		}
		return 0;
	}

	public float getAngle() {
		if (distance > min_distance && touch_state) {
			return angle;
		}
		return 0;
	}

	public float getDistance() {
		if (distance > min_distance && touch_state) {
			return distance;
		}
		return 0;
	}

	public void setMinimumDistance(int minDistance) {
		min_distance = minDistance;
	}

	public int getMinimumDistance() {
		return min_distance;
	}


	private int getVelocidade() {
		int distancia = 3;
		if (velocidade) {

			if (getDistance() < DistanceMed) {
				distancia = 1;
			} else if (getDistance() < DistanceHig) {
				distancia = 2;
			} else {
				distancia = 3;
			}

		}
		return distancia;
	}

	public int get8Direction() {
		int direcao = STICK_NONE;
		if (distance > min_distance && touch_state) {
			if (angle >= 247.5 && angle < 292.5) {
				direcao = 10;
			} else if (angle >= 292.5 && angle < 337.5) {
				direcao = 20;
			} else if (angle >= 337.5 || angle < 22.5) {
				direcao = 30;
			} else if (angle >= 22.5 && angle < 67.5) {
				direcao = 40;
			} else if (angle >= 67.5 && angle < 112.5) {
				direcao = 50;
			} else if (angle >= 112.5 && angle < 157.5) {
				direcao = 60;
			} else if (angle >= 157.5 && angle < 202.5) {
				direcao = 70;
			} else if (angle >= 202.5 && angle < 247.5) {
				direcao = 80;
			}
			// Digito da esquerda determina direção. Digito da direita determina
			// velocidade.
			direcao += getVelocidade();
		}
		return direcao;
	}

	public int get4Direction() {
		int direcao = STICK_NONE;
		if (distance > min_distance && touch_state) {
			if (angle >= 225 && angle < 315) {
				direcao = 10;
			} else if (angle >= 315 || angle < 45) {
				direcao = 30;
			} else if (angle >= 45 && angle < 135) {
				direcao = 50;
			} else if (angle >= 135 && angle < 225) {
				direcao = 70;
			}
			direcao += getVelocidade();
		}
		return direcao;


	}

	public void setOffset(int offset) {
		OFFSET = offset;
	}

	public int getOffset() {
		return OFFSET;
	}

	public void setStickAlpha(int alpha) {
		STICK_ALPHA = alpha;
		paint.setAlpha(alpha);
	}

	public int getStickAlpha() {
		return STICK_ALPHA;
	}

	public void setLayoutAlpha(int alpha) {
		LAYOUT_ALPHA = alpha;
		mLayout.getBackground().setAlpha(alpha);
	}

	public int getLayoutAlpha() {
		return LAYOUT_ALPHA;
	}

	public void setStickSize(int width, int height) {
		stick = Bitmap.createScaledBitmap(stick, width, height, false);
		stick_width = stick.getWidth();
		stick_height = stick.getHeight();
	}

	public void setStickWidth(int width) {
		stick = Bitmap.createScaledBitmap(stick, width, stick_height, false);
		stick_width = stick.getWidth();
	}

	public void setStickHeight(int height) {
		stick = Bitmap.createScaledBitmap(stick, stick_width, height, false);
		stick_height = stick.getHeight();
	}

	public int getStickWidth() {
		return stick_width;
	}

	public int getStickHeight() {
		return stick_height;
	}

	public void setLayoutSize(int width, int height) {
		params.width = width;
		params.height = height;
	}

	public int getLayoutWidth() {
		return params.width;
	}

	public int getLayoutHeight() {
		return params.height;
	}

	private double cal_angle(float x, float y) {
		if (x >= 0 && y >= 0)
			return Math.toDegrees(Math.atan(y / x));
		else if (x < 0 && y >= 0)
			return Math.toDegrees(Math.atan(y / x)) + 180;
		else if (x < 0 && y < 0)
			return Math.toDegrees(Math.atan(y / x)) + 180;
		else if (x >= 0 && y < 0)
			return Math.toDegrees(Math.atan(y / x)) + 360;
		return 0;
	}

	private void draw() {
		try {
			mLayout.removeView(draw);
		} catch (Exception e) {
		}
		mLayout.addView(draw);
	}

	private class DrawCanvas extends View {
		float x, y;

		private DrawCanvas(Context mContext) {
			super(mContext);
		}

		public void onDraw(Canvas canvas) {
			canvas.drawBitmap(stick, x, y, paint);
		}

		private void position(float pos_x, float pos_y) {
			x = pos_x - (stick_width / 2);
			y = pos_y - (stick_height / 2);
		}
	}
}
