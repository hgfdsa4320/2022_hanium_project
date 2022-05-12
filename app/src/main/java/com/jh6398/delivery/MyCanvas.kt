package com.jh6398.delivery

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import kotlin.collections.ArrayList

class MyCanvas : View {
    var paint: Paint


    var myPosX = 300f
    var myPosY = 750f
    var bPosX = 0f
    var bPosY = 0f
    var bPosX2 = 0f



    lateinit var my : Bitmap
    lateinit var rokaBit : Bitmap
    lateinit var rokmBit : Bitmap
    lateinit var wallBit : Bitmap
    lateinit var rokafBit : Bitmap
    lateinit var background : Bitmap
    lateinit var background2 : Bitmap
    lateinit var background3 : Bitmap
    lateinit var background4 : Bitmap
    lateinit var gameover : Bitmap
    lateinit var stage2 : Bitmap
    lateinit var hpBit : Bitmap


    constructor(context: Context) : super(context) {
        paint = Paint()

    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        paint = Paint()


    }



    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        setBackgroundColor(Color.WHITE)
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 10f
        paint.setColor(Color.BLACK)





//        paint.setColor(Color.GRAY)
        canvas.drawRect(40f, 900f, 40f +980 , 900f -180,paint)
//
//        paint.setColor(Color.RED)
//        canvas.drawRect(hPosX, 160f, hPosX + myHp  , 160f - 50,paint)

//        canvas.drawBitmap(my,myPosX,myPosY,paint)



//        canvas.drawBitmap(rokafBit,1200f,550f,paint)
//        canvas.drawRect(1200f,550f,1200+90f,550f+100,paint)

//        canvas.drawBitmap(rokmBit,1200f,650f,paint)
//        canvas.drawRect(1200f,560f,1200+90f,560f+120,paint)

//        canvas.drawBitmap(wallBit,1200f,550f,paint)
//        canvas.drawRect(1210f,590f,1210+100f,590f+60,paint)

//        canvas.drawBitmap(wallBit,1200f,700f,paint)
//        canvas.drawBitmap(wallBit,1295f,700f,paint)
////        canvas.drawRect(1210f,590f,1210+100f,590f+60,paint)

        // 주인공 감싸기 확인용
//        canvas.drawRect(myPosX,myPosY,myPosX+95,myPosY+150,paint)
//        canvas.drawRect(1200f,770f,1200+90f,770+120f,paint)
        // hp 확인용
//        canvas.drawBitmap(hpBit,1200f,550f,paint)
//        canvas.drawRect(1200f,550f,1200+110f,550f+60,paint)

//        my.recycle()



    }




}