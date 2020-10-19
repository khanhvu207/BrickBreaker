package bbgame.game

import processing.core._

class GameEngine extends PApplet{
  val defaultWidth = 500
  val defaultHeight = 700

  override def settings(): Unit = {
    pixelDensity(displayDensity())
    size(defaultWidth, defaultHeight, PConstants.P2D)
  }
}
