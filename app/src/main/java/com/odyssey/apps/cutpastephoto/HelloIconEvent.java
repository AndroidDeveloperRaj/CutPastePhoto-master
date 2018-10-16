package com.odyssey.apps.cutpastephoto;

import android.view.MotionEvent;
import android.widget.Toast;

/**
 * @author wupanjie
 * @see StickerIconEvent
 */

public class HelloIconEvent implements StickerIconEvent  {



  @Override
  public void onActionDown(StickerView stickerView, MotionEvent event) {

  }

  @Override
  public void onActionMove(StickerView stickerView, MotionEvent event) {

  }

  @Override
  public void onActionUp(StickerView stickerView, MotionEvent event) {
    //It Will not Work rather EditSticker will work
    Toast.makeText(stickerView.getContext(), "Hello World!", Toast.LENGTH_SHORT).show();




  }
}
