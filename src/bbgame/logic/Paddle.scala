package bbgame.logic

import java.awt.event.KeyEvent._

case class Paddle(x: Int,
                  y: Int,
                  PaddleWidth: Int,
                  PaddleHeight: Int,
                  GridWidth: Int,
                  GridHeight: Int) {
  private val PaddleSpeed = 10

  def update(keyCode: Int): Paddle =
    keyCode match {
      case VK_LEFT => Paddle(Math.max(0, x - PaddleSpeed), y, PaddleWidth, PaddleHeight, GridWidth, GridHeight)
      case VK_RIGHT => Paddle(Math.min(GridWidth - PaddleWidth, x + PaddleSpeed), y, PaddleWidth, PaddleHeight, GridWidth, GridHeight)
      case _ => Paddle(x, y, PaddleWidth, PaddleHeight, GridWidth, GridHeight)
    }
}
