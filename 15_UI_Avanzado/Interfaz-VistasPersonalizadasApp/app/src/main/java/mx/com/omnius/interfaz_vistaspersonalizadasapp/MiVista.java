package mx.com.omnius.interfaz_vistaspersonalizadasapp;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;


public class MiVista extends View {

    private int colorCirculo, colorEtiqueta;
    private String textoCirculo;
    private Paint paint;
    private int radio;

    public MiVista(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.estilos, 0, 0);

        try {
            textoCirculo = typedArray.getString(R.styleable.estilos_etiquetaCirculo);
            colorCirculo = typedArray.getInteger(R.styleable.estilos_colorCirculo, 0);
            colorEtiqueta = typedArray.getInteger(R.styleable.estilos_colorEtiqueta, 0);
        } finally {
            typedArray.recycle();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int viewWidth = this.getMeasuredWidth() / 2;
        int viewHeight = this.getMeasuredHeight() / 2;
        radio = 0;
        if (viewWidth > viewHeight) {
            radio = viewHeight - 10;
        } else {
            radio = viewWidth - 10;
        }


        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        paint.setColor(colorCirculo);

        canvas.drawCircle(viewWidth, viewHeight, radio, paint);

        paint.setColor(colorEtiqueta);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(50);

        canvas.drawText(textoCirculo, viewWidth, viewHeight, paint);
    }

    public void setColorCirculo(int nuevoColor) {
        colorCirculo = nuevoColor;
        invalidate();
        requestLayout();
    }

    public void setColorEtiqueta(int nuevoColor) {
        colorEtiqueta = nuevoColor;
        invalidate();
        requestLayout();
    }

    public void setTextoEtiqueta(String nuevaEtiqueta) {
        textoCirculo = nuevaEtiqueta;
        invalidate();
        requestLayout();
    }

}
