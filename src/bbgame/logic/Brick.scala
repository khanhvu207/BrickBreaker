package bbgame.logic

case class Brick(x: Int,
                 y: Int,
                 BrickWidth: Int,
                 BrickHeight: Int,
                 Red: Int,
                 Green: Int,
                 Blue: Int,
                 isAlive: Boolean) {
  def gotHit(): Brick = Brick(x, y, BrickWidth, BrickHeight, Red, Green, Blue, isAlive = false)
}
