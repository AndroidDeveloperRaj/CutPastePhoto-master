package com.odyssey.apps.cutpastephoto;

/**
 * @author wupanjie
 */

public class FlipHorizontallyEvent extends AbstractFlipEvent {

  @Override
  @StickerView.Flip protected int getFlipDirection() {
    return StickerView.FLIP_HORIZONTALLY;
  }
}
